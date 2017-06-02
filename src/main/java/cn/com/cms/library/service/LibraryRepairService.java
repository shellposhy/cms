package cn.com.cms.library.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import com.microduo.index.IndexException;
import com.microduo.index.lucene3.IIndexService;
import com.microduo.index.lucene3.IndexService;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.base.data.CmsData;
import cn.com.cms.base.data.FieldCodes;
import cn.com.cms.data.dao.BaseDbDao;
import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.data.service.DataFieldService;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.TaskMessage;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.framework.esb.jms.TaskMessageListener;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.EIndexType;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.system.contant.ETaskStatus;
import cn.com.cms.system.model.Task;
import cn.com.cms.system.model.TaskError;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.FileUtil;
import cn.com.people.data.util.SimpleLock;

/**
 * 数据库修复服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryRepairService<T extends BaseLibrary<T>> extends TaskMessageListener {
	// 区分任务是否是真正停滞
	private final static long TASK_NO_PROGRESS_TIME = 3;
	private static final Logger logger = Logger.getLogger(LibraryRepairService.class.getName());
	@Resource
	private LibraryMapper<T> libraryMapper;
	@Resource
	private DataTableMapper dataTableMapper;
	@Resource
	private DataFieldService dataFieldService;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private BaseDbDao dbDao;
	@Resource
	private AppConfig appConfig;
	private IIndexService indexService;

	public void onMessage(Message message) {
		ObjectMessage om = (ObjectMessage) message;
		TaskMessage taskMessage = null;
		try {
			taskMessage = (TaskMessage) om.getObject();
		} catch (JMSException e) {
			logger.error("消息服务出错", e);
			e.printStackTrace();
		}
		Task task = taskMessage.getTask();
		String baseIdStr = task.getAim();
		task.setTaskStatus(ETaskStatus.Executing);
		task.setProgress(1);
		this.updateTaskStatus(task);
		// Lock the database
		if (!SimpleLock.lock("DataBase." + baseIdStr)) {
			logger.warn("数据库已经被锁定");
		}
		Integer baseId = Integer.parseInt(baseIdStr);
		libraryMapper.updateStatus(Integer.parseInt(baseIdStr), EStatus.Repairing);
		try {
			logger.info("开始执行数据库的索引重建，数据库ID：" + baseIdStr);
			indexService = IndexService.getInstance(SystemConstant.PDS3_DB_INDEX_SERVICE);
			String indexPath = libraryDataService.getIndexPath(baseId);
			indexService.closeIndex(indexPath);
			FileUtil.deleteAllFile(indexPath);
			FileUtil.createFolder(indexPath);
			List<DataTable> dataTables = dataTableMapper.findByBaseId(baseId);
			List<DataField> fieldList = dataFieldService.findFieldsByDBId(baseId);
			List<DataField> indexFieldList = new ArrayList<DataField>();
			indexFieldList.addAll(fieldList);
			indexFieldList.add(new DataField(FieldCodes.DOC_YEAR, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
			indexFieldList.add(new DataField(FieldCodes.DOC_MONTH, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
			indexFieldList.add(new DataField(FieldCodes.DOC_DAY, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
			double currentProgress = 0;
			double perTableProgress = 0;
			int countPerTime = appConfig.getTheCountPerTimeByRepairDataBase();
			if (dataTables.size() > 0) {
				perTableProgress = 100 / dataTables.size();
			}
			for (int i = 0; i < dataTables.size(); i++) {
				int currentRow = 0;
				double perProgress = 0;
				DataTable table = dataTables.get(i);
				int rowCount = dbDao.count(table.getName());
				if (rowCount == 0) {
					continue;
				}
				if (rowCount >= countPerTime) {
					perProgress = perTableProgress / rowCount * countPerTime;
					for (int j = 1; j * countPerTime < rowCount; j++) {
						Result<CmsData> result = libraryDataService.search(table, currentRow, countPerTime);
						Date startTime = DateTimeUtil.getCurrentDateTime();
						indexService.addDocuments(indexPath,
								converter(result, fieldList, indexFieldList, baseId, table.getId()));
						Date endTime = DateTimeUtil.getCurrentDateTime();
						logger.debug("建立索引" + result.getList().size() + "条，共花费时间"
								+ DateTimeUtil.getSecondsOfDate(startTime, endTime) + "秒");
						currentRow = j * countPerTime;
						if (rowCount >= countPerTime) {
							task.setProgress((int) Math.floor(currentProgress + j * perProgress));
							this.updateTaskStatus(task);
						}
					}
				}
				Result<CmsData> result = libraryDataService.search(table, currentRow, rowCount - currentRow);
				indexService.addDocuments(indexPath,
						converter(result, fieldList, indexFieldList, baseId, table.getId()));
				task.setProgress((int) Math.floor((i + 1) * perTableProgress));
				this.updateTaskStatus(task);
				currentProgress = task.getProgress();
			}
		} catch (IndexException e) {
			TaskError error = new TaskError();
			error.setTaskId(task.getId());
			error.setContent(e.getMessage());
			error.setErrTime(DateTimeUtil.getCurrentDateTime());
			task.addErrorList(error);
			logger.warn("修复数据出现索引异常", e);
		} catch (IOException e) {
			TaskError error = new TaskError();
			error.setTaskId(task.getId());
			error.setContent(e.getMessage());
			error.setErrTime(DateTimeUtil.getCurrentDateTime());
			task.addErrorList(error);
			logger.warn("修复数据出现IO异常", e);
		} catch (Exception e) {
			TaskError error = new TaskError();
			error.setTaskId(task.getId());
			error.setContent(e.getMessage());
			error.setErrTime(DateTimeUtil.getCurrentDateTime());
			task.addErrorList(error);
			logger.error("修复数据出现未知异常", e);
		} finally {
			task.setProgress(100);
			task.setTaskStatus(ETaskStatus.Finish);
			this.updateTaskStatus(task);
			libraryMapper.updateTask(baseId, null);
			libraryMapper.updateStatus(Integer.parseInt(baseIdStr), EStatus.Normal);
			SimpleLock.unLock("DataBase." + baseIdStr);
		}
	}

	/**
	 * 转换类
	 * 
	 * @param result
	 * @param fieldList
	 * @return Document
	 * @throws IOException
	 */
	private Collection<Document> converter(Result<CmsData> result, List<DataField> fieldList,
			List<DataField> indexFieldList, Integer baseId, Integer tableId) throws IOException {
		Collection<Document> docs = new ArrayList<Document>();
		if (null != result && null != result.getList()) {
			for (CmsData data : result.getList()) {
				data.setBaseId(baseId);
				data.setTableId(tableId);
				if (null != data.get(FieldCodes.DOC_TIME)) {
					Date docDate = (Date) data.get(FieldCodes.DOC_TIME);
					data.put(FieldCodes.DOC_YEAR, DateTimeUtil.getYear(docDate));
					data.put(FieldCodes.DOC_MONTH, DateTimeUtil.getMonth(docDate));
					data.put(FieldCodes.DOC_DAY, DateTimeUtil.getDay(docDate));
					docs.add(DataUtil.getIndexDoc(data, indexFieldList));
				} else {
					docs.add(DataUtil.getIndexDoc(data, fieldList));
				}
			}
		}
		return docs;
	}

	/**
	 * 系统初始化检查
	 * 
	 * @return
	 */
	@PostConstruct
	public void init() {
		List<T> databases = null;
		try {
			databases = libraryMapper.findByStatus(EStatus.Repairing);
		} catch (BadSqlGrammarException e) {
			return;
		}
		if (null != databases && !databases.isEmpty()) {
			logger.info("尚未修复成功的数据库，数量：" + databases.size());
			for (T dataBase : databases) {
				if (null != dataBase.getTaskId()) {
					Task task = this.findTask(dataBase.getTaskId());
					if (null != task) {
						long minutes = DateTimeUtil.getMinutesOfDate(DateTimeUtil.getCurrentDateTime(),
								task.getUpdateTime());
						if (minutes > TASK_NO_PROGRESS_TIME) {
							logger.info("任务（ID：" + task.getId() + "）已有" + minutes + "分钟没有进度，将其对应的数据库（ID："
									+ dataBase.getId() + "）状态重置");
							dataBase.setTaskId(null);
							dataBase.setStatus(EStatus.Normal);
						} else {
							logger.info("任务（ID：" + task.getId() + "）仅有" + minutes + "分钟没有进度，暂不对其对应的数据库（ID："
									+ dataBase.getId() + "）进行处理");
						}
					}
				} else {
					dataBase.setStatus(EStatus.Normal);
				}
				libraryMapper.update(dataBase);
			}
		}
	}
}

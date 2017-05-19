package cn.com.cms.library.service;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.com.cms.base.data.CmsData;
import cn.com.cms.base.data.FieldCodes;
import cn.com.cms.data.dao.BaseDbDao;
import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.data.service.DataCopyService;
import cn.com.cms.data.service.DataFieldService;
import cn.com.cms.framework.base.TaskMessage;
import cn.com.cms.framework.service.TaskMessageListener;
import cn.com.cms.framework.tree.Node;
import cn.com.cms.library.constant.ELibraryCopyType;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.system.contant.ETaskStatus;
import cn.com.cms.system.model.Task;
import cn.com.people.data.util.SimpleLock;

/**
 * 数据库复制和同步服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryCopyService<T extends BaseLibrary<T>> extends TaskMessageListener {
	private static final Logger logger = Logger.getLogger(LibraryCopyService.class.getName());
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
	private DataCopyService dataCopyService;

	/**
	 * receive
	 * 
	 * @param message
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		logger.debug("====library.data.copy.JMS.onMessage====");
		ObjectMessage om = (ObjectMessage) message;
		TaskMessage taskMessage = null;
		try {
			taskMessage = (TaskMessage) om.getObject();
		} catch (JMSException e) {
			logger.error("消息服务出错", e);
			e.printStackTrace();
		}
		Task task = taskMessage.getTask();
		Integer type = (Integer) taskMessage.get("type");
		Integer source = task.getBaseId();
		Integer target = Integer.valueOf(task.getAim());
		String context = task.getContext();
		task.setTaskStatus(ETaskStatus.Executing);
		task.setProgress(1);
		this.updateTaskStatus(task);
		List<DataField> fieldList = findFieldsByDBId(target);
		List<CmsData> sourceDatas = wrapSourceData(context);
		List<CmsData> datas = dataCopyService.wrapData(source, target, sourceDatas, findFieldsByDBId(source),
				findFieldsByDBId(target));
		switch (ELibraryCopyType.valueof(type)) {
		case Copy:// copy data
			if (!SimpleLock.lock("DataBase." + target)) {
				logger.warn("数据库已经被锁定");
			}
			try {
				libraryDataService.save(datas, fieldList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				task.setProgress(100);
				task.setTaskStatus(ETaskStatus.Finish);
				this.updateTaskStatus(task);
				libraryMapper.updateTask(target, null);
				libraryMapper.updateStatus(target, EStatus.Normal);
				SimpleLock.unLock("DataBase." + target);
			}
			break;
		case Movie:// move data
			if (!SimpleLock.lock("DataBase." + target)) {
				logger.warn("数据库已经被锁定");
			}
			try {
				libraryDataService.save(datas, fieldList);
				// delete data
				if (null != sourceDatas && sourceDatas.size() > 0) {
					for (CmsData data : sourceDatas) {
						libraryDataService.deleteIndex(source, (String) data.get(FieldCodes.UUID));
						DataTable dataTable = libraryDataService.getDataTable(source);
						dbDao.delete(dataTable.getName(), (Integer) data.get(FieldCodes.ID));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				task.setProgress(100);
				task.setTaskStatus(ETaskStatus.Finish);
				this.updateTaskStatus(task);
				libraryMapper.updateTask(target, null);
				libraryMapper.updateStatus(target, EStatus.Normal);
				SimpleLock.unLock("DataBase." + target);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Find the database data field list
	 * 
	 * @param libId
	 * @return
	 */
	public List<DataField> findFieldsByDBId(Integer libId) {
		return dataFieldService.findFieldsByDBId(libId);
	}

	/**
	 * Wrap the source Cms Data
	 * 
	 * @param context
	 *            <code>e.g. 1_3,2_3,3_3</code>
	 * @return
	 */
	public List<CmsData> wrapSourceData(String context) {
		List<CmsData> result = null;
		List<Node<Integer, Integer>> nodes = dataCopyService.copyNode(context);
		if (null != nodes && nodes.size() > 0) {
			result = Lists.newArrayList();
			for (Node<Integer, Integer> node : nodes) {
				CmsData cmsData = libraryDataService.find(node.getName(), node.getId());
				if (null != cmsData)
					result.add(cmsData);
			}
		}
		return result;
	}
}

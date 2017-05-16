package cn.com.cms.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.base.data.DbTable;
import cn.com.cms.data.dao.DataFieldMapper;
import cn.com.cms.data.dao.BaseDbDao;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.library.constant.EDataFieldType;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.dao.DataBaseFieldMapMapper;
import cn.com.cms.library.model.DataBase;
import cn.com.cms.library.model.DataBaseFieldMap;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.system.contant.ETaskStatus;
import cn.com.cms.system.contant.ETaskType;
import cn.com.cms.system.dao.TaskErrorMapper;
import cn.com.cms.system.dao.TaskMapper;
import cn.com.cms.system.model.Task;

/**
 * 系统调度任务服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class SchedulerService {
	private static final Logger logger = Logger.getLogger(SchedulerService.class);
	@Resource
	private AppConfig appConfig;
	@Resource
	private TaskMapper taskMapper;
	@Resource
	private TaskErrorMapper taskErrorMapper;
	@Resource
	private LibraryService<DataBase> libraryService;
	@Resource
	private DataFieldMapper dataFieldMapper;
	@Resource
	private DataBaseFieldMapMapper dataBaseFieldMapMapper;
	@Resource
	private BaseDbDao dbDao;

	/**
	 * 数据库模板修改任务调度处理
	 * 
	 * @author
	 */
	public void modelTask() {
		logger.debug("=======scheduler.model.edit.task========");
		List<Task> taskList = taskMapper.findByTypeAndNotStatus(ETaskType.MODEL_EDIT, ETaskStatus.Finish);
		if (null != taskList && taskList.size() > 0) {
			for (Task task : taskList) {
				if (null != task && null != task.getModelId() && task.getModelId().intValue() != 0) {
					// 先执行准备业务
					if (task.getTaskStatus() == ETaskStatus.Preparing) {
						List<DataBase> dataBaseList = libraryService.findModelIDAndType(task.getModelId(),
								ELibraryNodeType.Lib);
						// 更新任务状态
						task.setTaskStatus(ETaskStatus.Executing);
						task.setUpdateTime(new Date());
						taskMapper.update(task);
						syncDataBaseField(dataBaseList, task);
						// 同步完成后更新状态
						task.setTaskStatus(ETaskStatus.Finish);
						task.setProgress(100);
						task.setUpdateTime(new Date());
						taskMapper.update(task);
					}
				}
			}
		}
	}

	/**
	 * 数据库数据表数据库字段变化时，同步数据库字段
	 * 
	 * @param result
	 * @return
	 */
	public void syncDataBaseField(List<DataBase> result, Task task) {
		if (null != result && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				DataBase base = result.get(i);
				logger.debug("=====数据库:" + base.getName() + " >>data field starting sync=====");
				// 字段初始化
				List<DataField> oldFieldList = dataFieldMapper.findFieldsByDBId(base.getId());
				List<DataField> newFieldList = dataFieldMapper.findByType(EDataFieldType.Required);
				List<DataField> modelFields = dataFieldMapper.findFieldsByModelId(base.getModelId());
				newFieldList.addAll(modelFields);
				// 数据库底层处理初始化
				DbTable dbTable = new DbTable(base.getName(), newFieldList);
				List<DataField> adds = libraryService.compareFieldList(newFieldList, oldFieldList);
				List<DataField> drops = libraryService.compareFieldList(oldFieldList, newFieldList);
				dbTable.setAdds(adds);
				dbTable.setDrops(drops);
				List<String> tablesNameList = new ArrayList<String>();
				List<DataTable> tables = libraryService.findDataTables(base.getId());
				for (DataTable table : tables) {
					tablesNameList.add(table.getName());
				}
				dbTable.setNames(tablesNameList);
				// 业务操作
				if (null != adds && adds.size() > 0) {
					for (DataField addField : adds) {
						DataBaseFieldMap baseFieldMap = new DataBaseFieldMap();
						baseFieldMap.setBaseId(base.getId());
						baseFieldMap.setFieldId(addField.getId());
						baseFieldMap.setType(ELibraryType.SYSTEM_DATA_BASE);
						baseFieldMap.setDisplay(false);
						dataBaseFieldMapMapper.insert(baseFieldMap);
					}
				}
				if (null != drops && drops.size() > 0) {
					for (DataField dropField : drops) {
						dataBaseFieldMapMapper.deleteByDbIdAndFieldId(base.getId(), dropField.getId());
					}
				}
				dbDao.alterTable(dbTable);
				// 更新任务进度
				task.setProgress((i + 1) * 100 / result.size());
				task.setUpdateTime(new Date());
				taskMapper.update(task);
			}
		}
	}
}

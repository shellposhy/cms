package cn.com.cms.library.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import cn.com.cms.data.dao.DataFieldMapper;
import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.dao.BaseDbDao;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.framework.base.CmsData;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.dao.LibraryDataDao;
import cn.com.cms.framework.base.table.DbData;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.EIndexType;
import cn.com.cms.library.dao.DataBaseFieldMapMapper;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataBaseFieldMap;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserSecurityService;
import cn.com.cms.user.service.UserService;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.PkUtil;
import cn.com.pepper.PepperException;
import cn.com.pepper.common.PepperResult;

import com.google.common.collect.Maps;

@Service
public class LibraryDataService extends LibraryDataIndexService implements LibraryDataDao {
	private static final Logger logger = Logger.getLogger(LibraryDataService.class);

	@Resource
	private BaseDbDao dbDao;
	@Resource
	private LibraryMapper<?> libraryMapper;
	@Resource
	private LibraryService<?> libraryService;
	@Resource
	private DataFieldMapper dataFieldMapper;
	@Resource
	private DataTableMapper dataTableMapper;
	@Resource
	private DataBaseFieldMapMapper dataBaseFieldMapMapper;
	@Resource
	private UserSecurityService userSecurityService;
	@Resource
	private UserService userService;

	/**
	 * 根据用户编号组统计数据
	 * 
	 * @param tableName
	 * @param userId
	 * @return
	 */
	public Map<String, Integer> count(String tableName, List<User> users) {
		Map<Integer, Integer> map = dbDao.count(tableName, users);
		Map<String, Integer> result = Maps.newHashMap();
		if (null != map && map.size() > 0) {
			for (Integer key : map.keySet()) {
				result.put(userService.find(key).getName(), map.get(key));
			}
		}
		return result;
	}

	/**
	 * 批量保存数据
	 * 
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public void save(List<CmsData> datas, List<DataField> fields) throws Exception {
		if (null != datas && datas.size() > 0) {
			for (CmsData data : datas) {
				// database id is not null
				if (data.getBaseId() == null) {
					throw new Exception("保存失败，需要提供数据库ID！");
				}
				// data save and create lucene index
				if (null == data.getId() || data.getId() == 0) {
					DataTable dataTable = libraryService.getDataTable(data.getBaseId());
					data.setTableId(dataTable.getId());
					data.put(FieldCodes.DATA_STATUS, EDataStatus.Yes.ordinal());
					Integer dataId = dbDao.insert(dataTable.getName(), getDbData(data, fields));
					data.setId(dataId);
					data.put(FieldCodes.ID, dataId);
					saveIndex(data.getBaseId(), data, fields);
					dataTableMapper.increaseRowCount(dataTable.getId(), 1);
					libraryMapper.updateDataUpdateTime(data.getBaseId());
				} else {
					DataTable dataTable = libraryService.getDataTable(data.getBaseId());
					data.put(FieldCodes.UPDATE_TIME, DateTimeUtil.getCurrentDate());
					dbDao.update(dataTable.getName(), getDbData(data, fields));
					deleteIndex(data.getBaseId(), (String) data.get(FieldCodes.UUID));
					saveIndex(data.getBaseId(), data, fields);
					libraryMapper.updateDataUpdateTime(data.getBaseId());
				}
			}
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public int save(CmsData data, HttpServletRequest request) {
		int result = 0;
		if (data.getBaseId() == null) {
			try {
				throw new Exception("保存失败，需要提供数据库ID！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BaseLibrary<?> library = libraryMapper.find(data.getBaseId());
		List<DataField> dbFieldList = dataFieldMapper.findFieldsByDBId(library.getId());
		User currentUser = userSecurityService.currentUser(request);
		if (data.getId() == null) {
			data.put(FieldCodes.CREATE_TIME, DateTimeUtil.getCurrentDateTime());
			data.put(FieldCodes.UPDATE_TIME, DateTimeUtil.getCurrentDateTime());
			data.put(FieldCodes.CREATOR_ID, currentUser == null ? 0 : currentUser.getId());
			data.put(FieldCodes.UPDATER_ID, currentUser == null ? 0 : currentUser.getId());
			DataTable dataTable = libraryService.getDataTable(library.getId());
			data.setTableId(dataTable.getId());
			data.put(FieldCodes.DATA_STATUS, EDataStatus.Yes.ordinal());
			Integer dataId = dbDao.insert(dataTable.getName(), getDbData(data, dbFieldList));
			data.setId(dataId);
			saveIndex(library.getId(), data, dbFieldList);
			dataTableMapper.increaseRowCount(dataTable.getId(), 1);
			libraryMapper.updateDataUpdateTime(data.getBaseId());
			result = dataId;
		}
		// 更新
		else {
			Integer tableId = data.getTableId();
			if (tableId == null) {
				try {
					throw new Exception("保存失败，需要提供数据库ID！");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			DataTable dataTable = dataTableMapper.find(tableId);
			List<DataField> updateFields = new ArrayList<DataField>();
			for (DataField dataField : dbFieldList) {
				if (data.getLowerFieldSet().contains(dataField.getCode().toLowerCase())) {
					updateFields.add(dataField);
				}
			}
			data.put(FieldCodes.UPDATE_TIME, DateTimeUtil.getCurrentDate());
			data.put(FieldCodes.UPDATER_ID, currentUser == null ? 0 : currentUser.getId());
			dbDao.update(dataTable.getName(), getDbData(data, updateFields));
			deleteIndex(data.getBaseId(), (String) data.get(FieldCodes.UUID));
			saveIndex(library.getId(), data, dbFieldList);
			libraryMapper.updateDataUpdateTime(data.getBaseId());
			result = data.getId();
		}
		return result;
	}

	/**
	 * 获取数据表格
	 * 
	 * @param baseId
	 * @return
	 */
	public DataTable getDataTable(Integer baseId) {
		return libraryService.getDataTable(baseId);
	}

	/**
	 * 删除数据
	 * 
	 * @param tableId
	 * @param dataId
	 * @return
	 */
	public void delete(Integer tableId, Integer dataId) {
		DataTable dataTable = dataTableMapper.find(tableId);
		deleteIndex(tableId, dataId);
		dbDao.delete(dataTable.getName(), dataId);
		this.dataTableMapper.decreaseRowCount(tableId, 1);
	}

	/**
	 * 根据数据库编号和uuid删除
	 * 
	 * @param baseId
	 * @param uuid
	 * @return
	 */
	public void delete(int baseId, String uuid) {
		String indexPath = getIndexPath(baseId);
		try {
			PepperResult result = indexService.search(indexPath, "UUID:" + uuid, 0, 1);
			if (result != null && result.documents != null) {
				for (Document doc : result.documents) {
					Integer tableId = Integer.valueOf(doc.get(FieldCodes.TABLE_ID));
					Integer dataId = Integer.valueOf(doc.get(FieldCodes.ID));
					delete(tableId, dataId);
				}
			}
		} catch (PepperException e) {
			e.printStackTrace();
			logger.error("===>删除数据出错", e);
		}
	}

	/**
	 * 查询数据详情
	 * 
	 * @param tableId
	 * @param dataId
	 * @return
	 */
	public CmsData find(Integer tableId, Integer dataId) {
		DataTable dataTable = dataTableMapper.find(tableId);
		if (null != dataTable) {
			return dbDao.select(dataTable, dataId);
		} else {
			return null;
		}
	}

	/**
	 * 获取文章中的所有图片
	 * 
	 * @param tableId
	 * @param dataId
	 * @return
	 */
	public List<String> findDataImgs(Integer tableId, Integer dataId) {
		CmsData data = find(tableId, dataId);
		if (data == null)
			return null;
		try {
			return DataUtil.getImgs((String) data.get(FieldCodes.CONTENT));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 分页查询数据
	 * 
	 * @param table
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public Result<CmsData> search(DataTable table, Integer firstResult, Integer maxResults) {
		return dbDao.select(table.getId(), "select * from " + table.getName(), firstResult, maxResults);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param table
	 * @param where
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public Result<CmsData> search(DataTable table, String where, Integer firstResult, Integer maxResults) {
		return dbDao.select(table.getId(), "select * from " + table.getName() + " where " + where, firstResult,
				maxResults);
	}

	/**
	 * 分页查询最近的数据
	 * 
	 * @param table
	 * @param firstResult
	 * @param maxResults
	 */
	public Result<CmsData> searchLatestData(DataTable table, Integer firstResult, Integer maxResults) {
		return dbDao.select(table.getId(), "select * from " + table.getName() + "  order by Update_Time desc",
				firstResult, maxResults);
	}

	/**
	 * 查询图片数据
	 * 
	 * @param table
	 * @param where
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public Result<CmsData> searchImgData(DataTable table, String where, Integer firstResult, Integer maxResults) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(table.getName()).append(" where imgs > 0 ");
		if (null != where && !where.isEmpty()) {
			sql.append(" and ").append(where);
		}
		sql.append(" order by Update_Time DESC ");
		return dbDao.select(table.getId(), sql.toString(), firstResult, maxResults);
	}

	/**
	 * 查询数据
	 * 
	 * @param table
	 * @param dataId
	 * @return
	 */
	public CmsData find(DataTable table, Integer dataId) {
		return dbDao.select(table, dataId);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param table
	 * @param sql
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public Result<CmsData> select(DataTable table, String sql, Integer firstResult, Integer maxResults) {
		return dbDao.select(table.getId(), sql, firstResult, maxResults);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param sql
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public Result<CmsData> select(String sql, Integer firstResult, Integer maxResults) {
		return dbDao.select(null, sql, firstResult, maxResults);
	}

	/**
	 * 复制数据
	 * 
	 * @param targetLibraryId
	 * @param srcDataMap
	 * @return
	 */
	public void copy(int targetLibraryId, Map<Integer, List<Integer>> srcDataMap, HttpServletRequest request) {
		List<DataBaseFieldMap> fieldMapList = dataBaseFieldMapMapper.findByDBId(targetLibraryId);
		Map<String, String> dataFieldMap = new HashMap<String, String>();
		for (DataBaseFieldMap fieldMap : fieldMapList) {
			DataField field = dataFieldMapper.find(fieldMap.getFieldId());
			dataFieldMap.put(field.getCode().toLowerCase(), "1");
		}
		Set<Integer> tableIdSet = srcDataMap.keySet();
		for (Integer tableId : tableIdSet) {
			List<Integer> dataIds = srcDataMap.get(tableId);
			for (Integer dataId : dataIds) {
				CmsData data = find(tableId, dataId);
				if (data != null) {
					Set<String> keySet = data.getLowerFieldSet();
					String[] keys = new String[keySet.size()];
					keySet.toArray(keys);
					for (String key : keys) {
						if (!dataFieldMap.containsKey(key))
							data.remove(key);
					}
					data.setBaseId(targetLibraryId);
					data.setId(null);
					data.setTableId(null);
					save(data, request);
				}
			}
		}
	}

	/**
	 * 导入数据
	 * 
	 * @param dataList
	 * @return
	 */
	public List<Integer> importData(List<CmsData> dataList) {
		Date now = new Date();
		Integer baseId = dataList.get(0).getBaseId();
		if (baseId == null) {
			try {
				throw new Exception("保存失败，需要提供数据库ID！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String indexPath = getIndexPath(baseId);
		libraryMapper.updateDataUpdateTime(baseId);
		DataTable dataTable = libraryService.getDataTable(baseId);
		StringBuilder sqlSb = new StringBuilder("SELECT * FROM ").append(dataTable.getName()).append(" WHERE ")
				.append(FieldCodes.UUID).append("='");
		int endIndex = sqlSb.length();
		Result<CmsData> result;
		int delCount = 0;
		for (CmsData data : dataList) {
			if (data.get(FieldCodes.UUID) == null) {
				data.put(FieldCodes.UUID, PkUtil.getShortUUID());
				continue;
			}
			sqlSb.append(data.get(FieldCodes.UUID)).append("'");
			result = dbDao.select(dataTable.getId(), sqlSb.toString());
			if (result.getTotalCount() > 0) {
				dbDao.delete(dataTable.getName(), result.getList().get(0).getId());
				try {
					indexService.deleteDocuments(indexPath, FieldCodes.UUID + ":" + data.get(FieldCodes.UUID));
				} catch (PepperException e) {
					e.printStackTrace();
				}
				delCount++;
			}
			sqlSb.delete(endIndex, sqlSb.length());
		}
		dataTableMapper.decreaseRowCount(dataTable.getId(), delCount);
		List<DataField> dbFieldList = dataFieldMapper.findFieldsByDBId(baseId);
		List<DataField> indexFieldList = new ArrayList<DataField>();
		indexFieldList.addAll(dbFieldList);
		indexFieldList.add(new DataField(FieldCodes.DOC_YEAR, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
		indexFieldList.add(new DataField(FieldCodes.DOC_MONTH, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
		indexFieldList.add(new DataField(FieldCodes.DOC_DAY, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
		List<Document> docs = new ArrayList<Document>();
		List<Integer> errorList = new ArrayList<Integer>();
		int i = 0;
		int savedCountOfDataBase = 0;// 存到数据库里的数据的数目
		for (CmsData data : dataList) {

			data.put(FieldCodes.UPDATE_TIME, now);
			data.put(FieldCodes.FINGER_PRINT, data.get(FieldCodes.UUID));
			data.put(FieldCodes.CREATE_TIME, now);
			data.setTableId(dataTable.getId());

			DbData dbData = getDbData(data, dbFieldList);
			try {// 入库
				int id = dbDao.insert(dataTable.getName(), dbData);
				savedCountOfDataBase++;
				data.put(FieldCodes.ID, id);
			} catch (Exception e) {
				e.printStackTrace();
				errorList.add(i);
				continue;
			}
			try {
				if (null != data.get(FieldCodes.DOC_TIME)) {
					Date docDate = (Date) data.get(FieldCodes.DOC_TIME);
					data.put(FieldCodes.DOC_YEAR, DateTimeUtil.getYear(docDate));
					data.put(FieldCodes.DOC_MONTH, DateTimeUtil.getMonth(docDate) + 1);
					data.put(FieldCodes.DOC_DAY, DateTimeUtil.getDay(docDate));
					docs.add(DataUtil.getIndexDoc(data, indexFieldList));
				} else {
					docs.add(DataUtil.getIndexDoc(data, dbFieldList));
				}
			} catch (Exception e) {
				errorList.add(i);// 把出错的位置记录下来
				e.printStackTrace();
			}
			i++;
		}
		dataTableMapper.increaseRowCount(dataTable.getId(), savedCountOfDataBase);
		// 建索引
		try {
			indexService.addDocuments(indexPath, docs);
		} catch (PepperException e) {
			errorList.clear();
			for (int j = 0; j < dataList.size(); j++)
				errorList.add(j);
			e.printStackTrace();
		}
		return errorList;
	}

	/**
	 * 把PeopleData转换成DbData，共DbDao使用。
	 * 
	 * @param data
	 *            PeopleData数据
	 * @param dbFieldList
	 *            数据库的字段列表
	 * @return
	 */
	private DbData getDbData(CmsData data, List<DataField> dbFieldList) {
		DbData dbData = new DbData();
		for (DataField df : dbFieldList) {
			Object value = data.get(df.getCode());
			if (value == null) {
				dbData.add(df, DataUtil.getDefaultValue(df.getDataType(), df.isMand()));
			} else {
				dbData.add(df, value);
			}
		}
		dbData.setDescription(data.getDescription());
		return dbData;
	}

	/**
	 * 保存
	 * 
	 * @param baseId
	 *            库ID
	 * @param data
	 *            数据
	 * @param fieldList
	 *            字段列表
	 */
	public void saveIndex(int baseId, CmsData data, List<DataField> fieldList) {
		List<DataField> indexFieldList = new ArrayList<DataField>();
		indexFieldList.addAll(fieldList);
		try {
			if (null != data.get(FieldCodes.DOC_TIME)) {
				indexFieldList
						.add(new DataField(FieldCodes.DOC_YEAR, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
				indexFieldList
						.add(new DataField(FieldCodes.DOC_MONTH, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
				indexFieldList
						.add(new DataField(FieldCodes.DOC_DAY, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
				Date docTime = (Date) data.get(FieldCodes.DOC_TIME);
				data.put(FieldCodes.DOC_YEAR, DateTimeUtil.getYear(docTime));
				data.put(FieldCodes.DOC_MONTH, DateTimeUtil.getMonth(docTime) + 1);
				data.put(FieldCodes.DOC_DAY, DateTimeUtil.getDay(docTime));
			}

			if (null == data.getId()) {
				indexService.addDocument(getIndexPath(baseId), DataUtil.getIndexDoc(data, indexFieldList));
			} else {
				indexService.updateDocument(getIndexPath(baseId), FieldCodes.UUID, (String) data.get(FieldCodes.UUID),
						DataUtil.getIndexDoc(data, indexFieldList));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
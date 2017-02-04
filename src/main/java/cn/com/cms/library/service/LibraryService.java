package cn.com.cms.library.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.com.cms.base.AppConfig;
import cn.com.cms.common.DbTable;
import cn.com.cms.common.SystemConstant;
import cn.com.cms.data.dao.DataFieldMapper;
import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.dao.BaseDbDao;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.LibraryTreeNode;
import cn.com.cms.framework.base.dao.LibraryDao;
import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode.PropertySetter;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.dao.DataBaseFieldMapMapper;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataBaseFieldMap;
import cn.com.cms.library.model.DataNavigate;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserDataAuthorityService;
import cn.com.cms.user.service.UserGroupService;

/**
 * 数据库服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryService<T extends BaseLibrary<T>> implements LibraryDao<T> {
	@Resource
	private LibraryMapper<T> libraryMapper;
	@Resource
	private DataFieldMapper dataFieldMapper;
	@Resource
	private DataTableMapper dataTableMapper;
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private UserDataAuthorityService userDataAuthorityService;
	@Resource
	private DataBaseFieldMapMapper dataBaseFieldMapMapper;
	@Resource
	private BaseDbDao dbDao;
	@Resource
	private AppConfig appConfig;

	/**
	 * 属性设置器
	 */
	private PropertySetter<LibraryTreeNode, T> defaultPropertySetter = new PropertySetter<LibraryTreeNode, T>() {
		public void setProperty(LibraryTreeNode node, T entity) {
			if (entity != null && ELibraryNodeType.Lib.equals(entity.getNodeType())) {
				node.checked = true;
			}
		}
	};

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public T find(Integer id) {
		T base = libraryMapper.find(id);
		return base;
	}

	/**
	 * 根据<code>pathCode</code>查询
	 * 
	 * @param pathCode
	 * @return
	 */
	public T findByPathCode(String pathCode) {
		return libraryMapper.findByPathCode(pathCode);
	}

	/**
	 * 根据<code>pathCode</code>模糊查询
	 * 
	 * @param pathCode
	 * @return
	 */
	public List<T> findLikePathCode(String pathCode) {
		return libraryMapper.findLikePathCode(pathCode);
	}

	/**
	 * 根据<code>pathCode</code>和节点类型查询
	 * 
	 * @param pathCode
	 * @param nodeType
	 * @return
	 */
	public List<T> findByNodeTypeAndLikePathCode(String pathCode, ELibraryNodeType nodeType) {
		return libraryMapper.findByNodeTypeAndLikePathCode(pathCode, nodeType);
	}

	/**
	 * 根据数据库编码查询
	 * 
	 * @param code
	 * @return
	 */
	public T findByCode(String code) {
		return libraryMapper.findByCode(code);
	}

	/**
	 * 根据数据库编号查询
	 * 
	 * @param tableId
	 * @return
	 */
	public T findByTableId(Integer tableId) {
		return libraryMapper.findByTableId(tableId);
	}

	/**
	 * 根据数据库名称或编码查询
	 * 
	 * @param word
	 * @return
	 */
	public List<T> findByNameOrCode(String str) {
		return libraryMapper.findByNameOrCode(str);
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param id
	 * @param nodeType
	 */
	public List<T> findByParentId(Integer parentId) {
		return libraryMapper.findByParentId(parentId, null);
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param parentId
	 * @param user
	 * @param type
	 * @return
	 */
	public List<T> findByParentId(Integer parentId, User user, ELibraryType type) {
		List<T> result = new ArrayList<T>();
		T parent = find(parentId);
		List<T> dbs = libraryMapper.findByParentAndType(parentId, type);
		if (user != null && !SystemConstant.SA_USER.equals(user.getName())
				&& !userGroupService.isAllDataAuthority(user.getId())) {
			List<Integer> authorityDbIds = userDataAuthorityService.findDbIdsByUserId(user.getId(), parent.getType());
			for (T db : dbs) {
				if (authorityDbIds.contains(db.getId())) {
					result.add(db);
				}
			}
		} else {
			result = dbs;
		}
		return result;
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param id
	 * @param nodeType
	 */
	public List<T> findByParentId(Integer parentId, ELibraryNodeType nodeType) {
		return libraryMapper.findByParentId(parentId, nodeType);
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param parentId
	 * @return
	 */
	public List<T> findLeafLibByParentId(Integer parentId) {
		List<T> list = new ArrayList<T>();
		list = findLeafLibByParentId(parentId, list);
		return list;
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param parentId
	 * @param list
	 * @return
	 */
	private List<T> findLeafLibByParentId(Integer parentId, List<T> list) {
		List<T> chidren = findByParentId(parentId);
		for (T child : chidren) {
			if (ELibraryNodeType.Lib == child.getNodeType()) {
				list.add(child);
			}
			findLeafLibByParentId(child.getId(), list);
		}
		return list;
	}

	/**
	 * 根据父节点、用户和节点类型查询
	 * 
	 * @param parentId
	 * @param user
	 * @param nodeType
	 * @return
	 */
	public List<T> findByParentId(Integer parentId, User user, ELibraryNodeType nodeType) {
		List<T> result = new ArrayList<T>();
		T parent = find(parentId);
		List<T> dbs = libraryMapper.findByParentId(parentId, nodeType);
		if (!SystemConstant.SA_USER.equals(user.getName()) && !userGroupService.isAllDataAuthority(user.getId())) {
			List<Integer> authorityDbIds = userDataAuthorityService.findDbIdsByUserId(user.getId(), parent.getType());
			for (T db : dbs) {
				if (authorityDbIds.contains(db.getId())) {
					result.add(db);
				}
			}
		} else {
			result = dbs;
		}
		return result;
	}

	/**
	 * 根据名称查询
	 * 
	 * @param name
	 * @param type
	 * @param nodeType
	 * @return
	 */
	public List<T> findLikeName(String name, ELibraryType type, ELibraryNodeType nodeType) {
		return libraryMapper.findLikeName(name, type, nodeType);
	}

	/**
	 * 根据数据库节点类型查询
	 * 
	 * @param types
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(ELibraryType... types) {
		int baseTypeId = -1;
		List<T> dbListAll = new ArrayList<T>();
		for (ELibraryType type : types) {
			List<T> dbTypeList = libraryMapper.findAll(type, ELibraryNodeType.Directory);
			List<T> dbList = libraryMapper.findAll(type, ELibraryNodeType.Lib);
			BaseLibrary<T> parent = new BaseLibrary<T>();
			parent.setId(baseTypeId);
			parent.setParentID(0);
			parent.setName(type.getTitle());
			dbListAll.add((T) parent);
			if (dbTypeList == null)
				continue;
			dbTypeList.addAll(dbList);
			for (T base : dbTypeList) {
				if (base.getParentID() == 0) {
					base.setParentID(baseTypeId);
				}
			}
			dbListAll.addAll(dbTypeList);
			baseTypeId--;
		}
		return dbListAll;
	}

	/**
	 * 获得数据量节点不包含根节点
	 * 
	 * @param types
	 * @return
	 */
	public List<T> findAllNotIncludeRoot(ELibraryType... types) {
		List<T> dbListAll = new ArrayList<T>();
		for (ELibraryType type : types) {
			List<T> dbTypeList = libraryMapper.findAll(type, ELibraryNodeType.Directory);
			List<T> dbList = libraryMapper.findAll(type, ELibraryNodeType.Lib);
			dbTypeList.addAll(dbList);
			dbListAll.addAll(dbTypeList);
		}
		return dbListAll;
	}

	/**
	 * 查询全部数据库
	 * 
	 * @param type
	 * @param nodeType
	 * @return
	 */
	public List<T> findAll(ELibraryType type, ELibraryNodeType nodeType) {
		return libraryMapper.findAll(type, nodeType);
	}

	/**
	 * 获得所有的数据库id
	 * 
	 * @param types
	 * @return
	 */
	public List<Integer> findAllIds(ELibraryType... types) {
		List<Integer> result = new ArrayList<Integer>();
		List<T> libraryList = findAll(types);
		for (T library : libraryList) {
			result.add(library.getId());
		}
		return result;
	}

	/**
	 * 根据状态查询
	 * 
	 * @param status
	 * @param nodeType
	 * @return
	 */
	public List<T> findByStatus(EStatus status, ELibraryNodeType nodeType) {
		List<T> databaseList = libraryMapper.findByStatus(status);
		if (nodeType != null) {
			for (int i = 0; i < databaseList.size(); i++) {
				if (!databaseList.get(i).getNodeType().equals(nodeType)) {
					databaseList.remove(i--);
				}
			}
		}
		return databaseList;
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @return
	 */
	public DefaultTreeNode findTree() {
		return DefaultTreeNode.parseTree(findAll(ELibraryType.values()));
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param pathCode
	 * @return
	 */
	public DefaultTreeNode findTreeLikePathCode(String pathCode) {
		return DefaultTreeNode.parseTree(findLikePathCode(pathCode));
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param types
	 * @return
	 */
	public DefaultTreeNode findTree(ELibraryType... types) {
		return DefaultTreeNode.parseTree(findAll(types));
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param qs
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findTree(String qs, ELibraryType type) {
		LibraryTreeNode treeNode = findTree(qs, type, defaultPropertySetter);
		return treeNode;
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param qs
	 * @param type
	 * @param propertySetter
	 * @return
	 */
	public LibraryTreeNode findTree(String qs, ELibraryType type, PropertySetter<LibraryTreeNode, T> propertySetter) {
		LibraryTreeNode treeNode;
		List<T> dataBaseList = null;
		dataBaseList = libraryMapper.findAll(type, ELibraryNodeType.Directory);
		if (qs != null && !qs.equals("")) {
			dataBaseList.addAll(libraryMapper.findLikeName(qs, type, ELibraryNodeType.Lib));
		} else {
			dataBaseList.addAll(libraryMapper.findAll(type, ELibraryNodeType.Lib));
		}
		treeNode = LibraryTreeNode.parseTree(LibraryTreeNode.class, dataBaseList, propertySetter);
		return treeNode;
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param user
	 * @param types
	 * @return
	 */
	public DefaultTreeNode findTree(User user, ELibraryType... types) {
		List<T> bases = findAll(types);
		if (user.getName().equals(SystemConstant.SA_USER) || userGroupService.isAllDataAuthority(user.getId())) {
			return DefaultTreeNode.parseTree(bases);
		} else {
			Map<Integer, T> dataBaseAllMap = new HashMap<Integer, T>();
			List<T> resultBases = new ArrayList<T>();
			List<Integer> authorityDbIds = new ArrayList<Integer>();
			for (ELibraryType type : types) {
				authorityDbIds.addAll(userDataAuthorityService.findDbIdsByUserId(user.getId(), type));
			}
			for (T db : bases) {
				dataBaseAllMap.put(db.getId(), db);
				if (authorityDbIds.contains(db.getId())) {
					resultBases.add(db);
				}
			}
			return LibraryTreeNode.parseTree(LibraryTreeNode.class, resultBases, dataBaseAllMap, defaultPropertySetter);
		}
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param ids
	 * @return
	 */
	public DefaultTreeNode findPartTree(Integer[] ids) {
		LibraryTreeNode treeNode;
		List<T> dataBaseList = null;
		dataBaseList = libraryMapper.findAll(null, ELibraryNodeType.Directory);
		dataBaseList.addAll(libraryMapper.findAll(null, ELibraryNodeType.Lib));
		for (int i = 0; i < dataBaseList.size(); i++) {
			boolean isInIds = false;
			for (int id : ids) {
				if (dataBaseList.get(i).getId() == id) {
					isInIds = true;
					break;
				}
			}
			if (!isInIds) {
				dataBaseList.remove(i--);
			}
		}
		treeNode = LibraryTreeNode.parseTree(LibraryTreeNode.class, dataBaseList, defaultPropertySetter);
		return treeNode;
	}

	/**
	 * 获得默认数据库树
	 * 
	 * @param type
	 * @return
	 */
	public DefaultTreeNode findEmptyDirectoryTree(ELibraryType type) {
		List<T> dataBaseList = libraryMapper.findEmptyDirectory(type);
		return DefaultTreeNode.parseTree(dataBaseList);
	}

	/**
	 * 保存数据库
	 * 
	 * @param database
	 * @return
	 */
	public void saveDatabase(T database) {
		saveDatabase(database, null);
	}

	/**
	 * 保存数据库
	 * 
	 * @param database
	 * @param user
	 * @return
	 */
	public void saveDatabase(T database, User user) {
		T parentDB = libraryMapper.find(database.getParentID());
		database.setDataUpdateTime(new Date());
		if (null == parentDB) {
			database.setPathCode("/" + database.getCode() + "/");
		} else {
			database.setPathCode(parentDB.getPathCode() + database.getCode() + "/");
		}
		database.setNodeType(ELibraryNodeType.Lib);
		if (null == database.getId()) {
			database.setTables(0);
			database.setStatus(EStatus.Normal);
			if (null != parentDB) {
				parentDB.setTables(parentDB.getTables() + 1);
				libraryMapper.update(parentDB);
			}
			libraryMapper.insert(database);
			saveDataBaseFieldMaps(database);
			this.createTableAndUpdateTables(database);
		} else {
			T _database = libraryMapper.find(database.getId());
			List<DataField> _fieldList = dataFieldMapper.findFieldsByDBId(database.getId());
			database.setTables(_database.getTables());
			database.setStatus(_database.getStatus());
			database.setCreatorId(_database.getCreatorId());
			database.setCreateTime(_database.getCreateTime());
			libraryMapper.update(database);
			DbTable dbTable = new DbTable(database.getName(), database.getDataFields());
			dbTable.setAdds(this.compareFieldList(database.getDataFields(), _fieldList));
			dbTable.setDrops(this.compareFieldList(_fieldList, database.getDataFields()));
			List<DataTable> tables = findDataTables(database.getId());
			List<String> tablesNameList = new ArrayList<String>();
			for (DataTable table : tables) {
				tablesNameList.add(table.getName());
			}
			dbTable.setNames(tablesNameList);
			dataBaseFieldMapMapper.batchDeleteByDBId(database.getId());
			saveDataBaseFieldMaps(database);
			dbDao.alterTable(dbTable);
		}
	}

	/**
	 * 更新数据库类别
	 * 
	 * @param databaseType
	 * @return
	 */
	public void saveDatabaseType(T databaseType) {
		saveDatabaseType(databaseType, null);
	}

	/**
	 * 更新数据库类别
	 * 
	 * @param databaseType
	 * @param user
	 * @return
	 */
	public void saveDatabaseType(T databaseType, User user) {
		T parentDB = libraryMapper.find(databaseType.getParentID());
		if (null == parentDB) {
			databaseType.setPathCode("/" + databaseType.getCode() + "/");
		} else {
			databaseType.setPathCode(parentDB.getPathCode() + databaseType.getCode() + "/");
		}
		databaseType.setNodeType(ELibraryNodeType.Directory);
		if (null == databaseType.getId()) {
			databaseType.setTables(0);
			databaseType.setStatus(EStatus.Normal);
			libraryMapper.insert(databaseType);
		} else {
			T _databaseType = libraryMapper.find(databaseType.getId());
			databaseType.setTables(_databaseType.getTables());
			databaseType.setStatus(_databaseType.getStatus());
			databaseType.setCreatorId(_databaseType.getCreatorId());
			databaseType.setCreateTime(_databaseType.getCreateTime());
			libraryMapper.update(databaseType);
		}
	}

	/**
	 * 删除数据库
	 * 
	 * @param dbId
	 * @return
	 */
	public void deleteDatabase(Integer dbId) {
		T db = libraryMapper.find(dbId);
		T parent = libraryMapper.find(db.getParentID());
		List<DataTable> tables = findDataTables(dbId);
		dataBaseFieldMapMapper.batchDeleteByDBId(dbId);
		for (DataTable dataTable : tables) {
			dbDao.dropTable(dataTable.getName());
			dataTableMapper.delete(dataTable.getId());
		}
		if (parent != null && parent.getTables() > 0) {
			parent.setTables(parent.getTables() - 1);
			libraryMapper.update(parent);
		}
		libraryMapper.delete(dbId);
	}

	/**
	 * 删除数据库
	 * 
	 * @param typeId
	 * @return
	 */
	public void deleteDatabaseType(Integer typeId) {
		libraryMapper.delete(typeId);
	}

	/**
	 * 获取数据表格
	 * 
	 * @param baseId
	 * @return
	 */
	public DataTable getDataTable(Integer baseId) {
		T library = find(baseId);
		String tableName = getTableName(library, false);
		Integer count = dataTableMapper.findRowCountByName(tableName);
		if (null == count)
			return null;
		if (count >= appConfig.getDataTableMaxRows()) {
			try {
				library.setDataFields(dataFieldMapper.findFieldsByDBId(baseId));
				return createTableAndUpdateTables(library);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			return dataTableMapper.findByName(getTableName(library, false));
		}
		return null;
	}

	/**
	 * 更新状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public void updataStatus(Integer id, EStatus status) {
		libraryMapper.updateStatus(id, status);
	}

	/**
	 * 修复数据库
	 * 
	 * @param baseId
	 * @return
	 */
	public Integer repair(Integer baseId) {
		T library = this.find(baseId);
		if (null != library.getTaskId()) {
			return library.getTaskId();
		}
		this.updataStatus(baseId, EStatus.Repairing);
		return library.getId();
	}

	/**
	 * 复制数据
	 * 
	 * @param srcColumnId
	 * @param destColumnId
	 * @param userId
	 * @return
	 */
	public Integer copyData(int srcColumnId, int destColumnId, int userId) {
		return null;
	}

	/**
	 * 是否具有子节点
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasChildren(Integer id) {
		List<T> dbs = libraryMapper.findByParentId(id, null);
		return dbs.size() > 0 ? true : false;
	}

	/**
	 * 获得最新更新数据
	 * 
	 * @param type
	 */
	public Date findLastDataUpdateTime(ELibraryType type) {
		return libraryMapper.findLastDataUpdateTime(type);
	}

	/**
	 * 获取数据库的所有表
	 * 
	 * @param baseId
	 * @return
	 */
	public List<DataTable> findDataTables(Integer baseId) {
		return dataTableMapper.findByBaseId(baseId);
	}

	/**
	 * 保存数据库字段
	 * 
	 * @param database
	 * @return
	 */
	private void saveDataBaseFieldMaps(T database) {
		if (null != database.getDataFields()) {
			List<Integer> showIds = new ArrayList<Integer>();
			if (null != database.getDataFieldsStr()) {
				String[] listShowIds = database.getDataFieldsStr().split(SystemConstant.COMMA_SEPARATOR);
				if (null != listShowIds && listShowIds.length > 0) {
					for (String idStr : listShowIds) {
						if (!idStr.isEmpty()) {
							showIds.add(Integer.parseInt(idStr));
						}
					}
				}
			}
			for (DataField field : database.getDataFields()) {
				DataBaseFieldMap map = new DataBaseFieldMap();
				map.setBaseId(database.getId());
				map.setFieldId(field.getId());
				map.setType(database.getType());
				if (showIds.contains(field.getId())) {
					map.setDisplay(true);
				} else {
					map.setDisplay(false);
				}
				dataBaseFieldMapMapper.insert(map);
			}
		}
	}

	/**
	 * 建表、更新表数量
	 * 
	 * @param library
	 * @return
	 */
	private DataTable createTableAndUpdateTables(T library) {
		String dbTableName = this.getTableName(library, true);
		DbTable dbTable = new DbTable(dbTableName, library.getDataFields());
		dbDao.createTable(dbTable);
		DataTable dataTable = new DataTable();
		dataTable.setBaseId(library.getId());
		dataTable.setName(dbTableName);
		dataTable.setRowCount(0);
		dataTableMapper.insert(dataTable);
		library.setTables(library.getTables() + 1);
		library.setUpdateTime(new Date());
		libraryMapper.update(library);
		return dataTable;
	}

	/**
	 * 获取表名
	 * 
	 * @param library
	 *            数据库对象
	 * @param onCreateTable
	 *            是否是在创建表时
	 * @return
	 */
	private String getTableName(T library, boolean onCreateTable) {
		String result = "";
		int tables = library.getTables();
		if (!onCreateTable) {
			tables--;
		}
		switch (library.getType()) {
		default:
			result = "data_" + library.getId() + "_" + tables;
			break;
		}
		return result;
	}

	/**
	 * 比较两个列表的差异
	 * 
	 * @param srcList
	 * @param desList
	 * @return
	 */
	public List<DataField> compareFieldList(List<DataField> srcList, List<DataField> desList) {
		List<DataField> srcMoreThanDes = new ArrayList<DataField>();
		List<String> desNameList = new ArrayList<String>();
		for (DataField des : desList) {
			desNameList.add(des.getName());
		}
		for (DataField dataField : srcList) {
			if (!desNameList.contains(dataField.getName())) {
				srcMoreThanDes.add(dataField);
			}
		}
		return srcMoreThanDes;
	}

	/**
	 * 根据模板编号和数据库类别查询
	 * 
	 * @param modelId
	 * @param type
	 * @return
	 */
	public List<T> findModelIDAndType(Integer modelId, ELibraryNodeType type) {
		return libraryMapper.findModelIDAndType(modelId, type);
	}

	/**
	 * 获得导航列表数据
	 * 
	 * @return
	 */
	public List<DataNavigate> findNavigateList() {
		return findNavigateList(ELibraryType.values());
	}

	/**
	 * 根据数据类型查询
	 * 
	 * @param types
	 * @return
	 */
	public List<DataNavigate> findNavigateList(ELibraryType... types) {
		List<DataNavigate> result = Lists.newArrayList();
		List<T> dbList = findAll(types);
		for (T base : dbList) {
			result.add(new DataNavigate(base));
		}
		return result;
	}
}

package cn.com.cms.framework.base.dao;

import java.util.Date;
import java.util.List;

import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.LibraryTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode.PropertySetter;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.library.model.DataNavigate;
import cn.com.cms.user.model.User;

/**
 * 数据库服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface LibraryDao<T extends BaseLibrary<T>> {

	public T find(Integer id);

	public T findByPathCode(String pathCode);

	public List<T> findLikePathCode(String pathCode);

	public List<T> findByNodeTypeAndLikePathCode(String pathCode, ELibraryNodeType nodeType);

	public T findByTableId(Integer tableId);

	public T findByCode(String code);

	public List<T> findByNameOrCode(String str);

	public List<T> findByParentId(Integer parentId);

	List<T> findByParentId(Integer parentId, User user, ELibraryType type);

	public List<T> findByParentId(Integer parentId, ELibraryNodeType nodeType);

	public List<T> findLeafLibByParentId(Integer parentId);

	public List<T> findByParentId(Integer parentId, User user, ELibraryNodeType nodeType);

	public List<T> findLikeName(String name, ELibraryType type, ELibraryNodeType nodeType);

	public List<T> findAll(ELibraryType... types);

	public List<T> findAllNotIncludeRoot(ELibraryType... types);

	public List<T> findAll(ELibraryType type, ELibraryNodeType nodeType);

	public List<Integer> findAllIds(ELibraryType... types);

	public List<T> findByStatus(EStatus status, ELibraryNodeType nodeType);

	public DefaultTreeNode findTree();

	public DefaultTreeNode findTreeLikePathCode(String pathCode);

	public DefaultTreeNode findTree(ELibraryType... types);

	public DefaultTreeNode findTree(String qs, ELibraryType type);

	public LibraryTreeNode findTree(String qs, ELibraryType type, PropertySetter<LibraryTreeNode, T> propertySetter);

	public DefaultTreeNode findTree(User user, ELibraryType... types);

	public DefaultTreeNode findPartTree(Integer[] ids);

	public DefaultTreeNode findEmptyDirectoryTree(ELibraryType type);

	public void saveDatabase(T database);

	public void saveDatabase(T database, User user);

	public void saveDatabaseType(T databaseType);

	public void saveDatabaseType(T databaseType, User user);

	public void deleteDatabase(Integer dbId);

	public void deleteDatabaseType(Integer typeId);

	public DataTable getDataTable(Integer baseId);

	public void updataStatus(Integer id, EStatus status);

	public Integer repair(Integer baseId);

	public Integer copyData(int srcColumnId, int destColumnId, int userId);

	public boolean hasChildren(Integer id);

	public Date findLastDataUpdateTime(ELibraryType type);

	public List<DataNavigate> findNavigateList();

	public List<DataNavigate> findNavigateList(ELibraryType... types);
}

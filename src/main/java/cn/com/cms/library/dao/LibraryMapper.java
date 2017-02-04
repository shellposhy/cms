package cn.com.cms.library.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.library.model.BaseLibrary;

public interface LibraryMapper<T extends BaseLibrary<T>> {
	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	T find(Integer id);

	/**
	 * 根据数据库编号查询
	 * 
	 * @param tableId
	 * @return
	 */
	T findByTableId(Integer tableId);

	/**
	 * 根据<code>pathCode</code>查询
	 * 
	 * @param pathCode
	 * @return
	 */
	T findByPathCode(@Param("pathCode") String pathCode);

	/**
	 * 根据<code>pathCode</code>模糊查询
	 * 
	 * @param pathCode
	 * @return
	 */
	List<T> findLikePathCode(@Param("pathCode") String pathCode);

	/**
	 * 根据<code>pathCode</code>和节点类型查询
	 * 
	 * @param pathCode
	 * @param nodeType
	 * @return
	 */
	List<T> findByNodeTypeAndLikePathCode(@Param("pathCode") String pathCode,
			@Param("nodeType") ELibraryNodeType nodeType);

	/**
	 * 根据数据库编码查询
	 * 
	 * @param code
	 * @return
	 */
	T findByCode(@Param("code") String code);

	/**
	 * 根据数据库名称或编码查询
	 * 
	 * @param word
	 * @return
	 */
	List<T> findByNameOrCode(String word);

	/**
	 * 根据数据库状态查询
	 * 
	 * @param status
	 * @return
	 */
	List<T> findByStatus(EStatus status);

	/**
	 * 新增数据库
	 * 
	 * @param database
	 * @return
	 */
	void insert(T database);

	/**
	 * 修改数据库
	 * 
	 * @param database
	 * @return
	 */
	void update(T database);

	/**
	 * 更新数据库任务编号
	 * 
	 * @param id
	 * @param taskId
	 * @return
	 */
	void updateTask(@Param("id") Integer id, @Param("taskId") Integer taskId);

	/**
	 * 更新数据库状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	void updateStatus(@Param("id") Integer id, @Param("status") EStatus status);

	/**
	 * 更新数据库数据更新日期
	 * 
	 * @param id
	 */
	void updateDataUpdateTime(@Param("id") Integer id);

	/**
	 * 获得最新更新过数据的日期
	 * 
	 * @param type
	 * @return
	 */
	Date findLastDataUpdateTime(ELibraryType type);

	/**
	 * 删除数据库
	 * 
	 * @param id
	 * @return
	 */
	void delete(Integer id);

	/**
	 * 统计数据库数量
	 * 
	 * @param type
	 * @param nodeType
	 * @return
	 */
	int count(@Param("type") ELibraryType type, @Param("nodeType") ELibraryNodeType nodeType);

	/**
	 * 查询全部数据库
	 * 
	 * @param type
	 * @param nodeType
	 */
	List<T> findAll(@Param("type") ELibraryType type, @Param("nodeType") ELibraryNodeType nodeType);

	/**
	 * 根据父节点查询
	 * 
	 * @param id
	 * @param nodeType
	 */
	List<T> findByParentId(@Param("id") Integer id, @Param("nodeType") ELibraryNodeType nodeType);

	/**
	 * 根据名称查询
	 * 
	 * @param name
	 * @param type
	 * @param nodeType
	 * @return
	 */
	List<T> findLikeName(@Param("name") String name, @Param("type") ELibraryType type,
			@Param("nodeType") ELibraryNodeType nodeType);

	/**
	 * 根据父节点查询全部
	 * 
	 * @param id
	 * @return
	 */
	List<T> findByParent(@Param("id") Integer id);

	/**
	 * 根据父节点和数据库类别查询
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	List<T> findByParentAndType(@Param("id") Integer id, @Param("type") ELibraryType type);

	/**
	 * 查询空目录
	 * 
	 * @param type
	 */
	List<T> findEmptyDirectory(@Param("type") ELibraryType type);

	/**
	 * 增加tables
	 * 
	 * @param id
	 * @return
	 */
	void addDataTables(@Param("id") Integer id);

	/**
	 * 根据模板编号和数据库类别查询
	 * 
	 * @param modelId
	 * @param type
	 * @return
	 */
	List<T> findModelIDAndType(@Param("modelId") Integer modelId, @Param("type") ELibraryNodeType type);
}

package cn.com.cms.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.library.constant.ELibraryType;

/**
 * 数据表格
 * 
 * @author shishb
 * @version 1.0
 */
public interface DataTableMapper extends BaseDao<DataTable> {
	/**
	 * 根据数据库编号查询
	 * 
	 * @param dbId
	 * @return
	 */
	List<DataTable> findByBaseId(Integer dbId);

	/**
	 * 根据类别查询
	 * 
	 * @param type
	 * @return
	 */
	List<DataTable> findTables(ELibraryType type);

	/**
	 * 根据数据库表名获得数据量
	 * 
	 * @param tableName
	 * @return
	 */
	Integer findRowCountByName(String tableName);

	/**
	 * 按名称查找
	 * 
	 * @param name
	 * @return
	 */
	DataTable findByName(String name);

	/**
	 * 增加行数
	 * 
	 * @param id
	 * @param num
	 */
	void increaseRowCount(@Param("id") Integer id, @Param("num") Integer num);

	/**
	 * 减少行数
	 * 
	 * @param id
	 * @param num
	 */
	void decreaseRowCount(@Param("id") Integer id, @Param("num") Integer num);
}

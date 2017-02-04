package cn.com.cms.framework.base.dao;

import java.util.List;

import cn.com.cms.data.model.DataTable;

/**
 * 数据库表格服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface LibraryTableDao {

	public DataTable find(Integer tableId);

	public List<DataTable> findDataTables(Integer baseId);

	public DataTable getDataTable(Integer baseId);

}

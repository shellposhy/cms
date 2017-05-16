package cn.com.cms.library.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.base.data.DbTable;
import cn.com.cms.data.dao.DataFieldMapper;
import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.dao.BaseDbDao;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.dao.LibraryTableDao;
import cn.com.cms.library.dao.LibraryMapper;
import cn.com.cms.library.model.BaseLibrary;

/**
 * 数据库表格服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryTableService implements LibraryTableDao {
	@Resource
	private AppConfig appConfig;
	@Resource
	private DataTableMapper dataTableMapper;
	@Resource
	private LibraryMapper<?> libraryMapper;
	@Resource
	private DataFieldMapper dataFieldMapper;
	@Resource
	private BaseDbDao dbDao;

	/**
	 * 获得表
	 * 
	 * @param tableId
	 * @return
	 */
	public DataTable find(Integer tableId) {
		return dataTableMapper.find(tableId);
	}

	/**
	 * 获得数据库的表格数
	 * 
	 * @param baseId
	 */
	public List<DataTable> findDataTables(Integer baseId) {
		return dataTableMapper.findByBaseId(baseId);
	}

	/**
	 * 获得数据表格
	 * 
	 * @param baseId
	 * @return
	 */
	public DataTable getDataTable(Integer baseId) {
		BaseLibrary<?> library = libraryMapper.find(baseId);
		String tableName = getDataTableName(library, false);
		Integer count = dataTableMapper.findRowCountByName(tableName);
		if (null == count)
			return null;
		if (count >= appConfig.getDataTableMaxRows()) {
			try {
				library.setDataFields(dataFieldMapper.findFieldsByDBId(baseId));
				return createTable(library);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			return this.dataTableMapper.findByName(this.getDataTableName(library, false));
		}
	}

	private String getDataTableName(BaseLibrary<?> library, boolean onCreateTable) {
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
	 * 建表、更新表数量
	 * 
	 * @param library
	 * @return
	 */
	private DataTable createTable(BaseLibrary<?> library) {
		String dbTableName = this.getDataTableName(library, true);
		DbTable dbTable = new DbTable(dbTableName, library.getDataFields());
		dbDao.createTable(dbTable);
		DataTable dataTable = new DataTable();
		dataTable.setBaseId(library.getId());
		dataTable.setName(dbTableName);
		dataTable.setRowCount(0);
		dataTableMapper.insert(dataTable);
		libraryMapper.addDataTables(library.getId());
		return dataTable;
	}
}

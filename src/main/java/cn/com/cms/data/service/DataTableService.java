package cn.com.cms.data.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.model.DataTable;

/**
 * 数据表的服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class DataTableService {
	@Resource
	private DataTableMapper dataTableMapper;

	/**
	 * 查询全部数据表
	 * 
	 * @return
	 */
	public List<DataTable> findAll() {
		return dataTableMapper.findAll();
	}

	/**
	 * 根据数据库编号分组查询
	 * 
	 * @return
	 */
	public List<DataTable> findTablesByGroupByBaseId() {
		return dataTableMapper.findTablesByGroupByBaseId();
	}

	/**
	 * 根据数据库编号查询
	 * 
	 * @param dbId
	 * @return
	 */
	public List<DataTable> findByBaseId(Integer baseId) {
		return dataTableMapper.findByBaseId(baseId);
	}
}

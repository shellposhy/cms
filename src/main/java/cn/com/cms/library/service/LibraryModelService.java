package cn.com.cms.library.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.Result;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.dao.ColumnModelMapper;
import cn.com.cms.library.model.ColumnModel;

/**
 * 数据库模板服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryModelService {
	@Resource
	private ColumnModelMapper columnModelMapper;

	public List<ColumnModel> findAllByType(ELibraryType type) {
		return columnModelMapper.findAllByType(type);
	}

	public List<ColumnModel> findAll() {
		return columnModelMapper.findAll();
	}

	public Result<ColumnModel> findByName(String name, ELibraryType type, int firstResult, int maxResult) {
		Result<ColumnModel> result = new Result<ColumnModel>();
		result.setList(columnModelMapper.findByName(name, type, firstResult, maxResult));
		result.setTotalCount(columnModelMapper.countByName(name, type));
		return result;
	}

	public ColumnModel find(Integer id) {
		return columnModelMapper.find(id);
	}

	public void insert(ColumnModel entity) {
		columnModelMapper.insert(entity);
	}

	public void update(ColumnModel entity) {
		columnModelMapper.update(entity);
	}

	public void delete(Integer id) {
		columnModelMapper.delete(id);
	}

	public void batchDelete(Integer[] ids) {
		columnModelMapper.batchDelete(ids);
	}
}

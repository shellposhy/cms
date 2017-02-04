package cn.com.cms.library.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.library.dao.ColumnModelFieldMapMapper;
import cn.com.cms.library.model.ColumnModelFieldMap;

/**
 * 数据库模板字段服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LibraryModelFieldMapService {

	@Resource
	private ColumnModelFieldMapMapper columnModelFieldMapMapper;

	public List<ColumnModelFieldMap> findAll() {
		return columnModelFieldMapMapper.findAll();
	}

	public List<ColumnModelFieldMap> findByColumnModelId(int columnModelId) {
		return columnModelFieldMapMapper.findByColumnModelId(columnModelId);
	}

	public ColumnModelFieldMap find(Integer id) {
		return columnModelFieldMapMapper.find(id);
	}

	public void insert(ColumnModelFieldMap entity) {
		columnModelFieldMapMapper.insert(entity);
	}

	public void batchInsert(List<ColumnModelFieldMap> list) {
		columnModelFieldMapMapper.batchInsert(list);
	}

	public void update(ColumnModelFieldMap entity) {
		columnModelFieldMapMapper.update(entity);
	}

	public void delete(Integer id) {
		columnModelFieldMapMapper.delete(id);
	}

	public void deleteByColumnId(int id) {
		columnModelFieldMapMapper.deleteByColumnModelId(id);
	}

	public void batchDelete(Integer[] ids) {
		columnModelFieldMapMapper.batchDelete(ids);
	}
}

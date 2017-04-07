package cn.com.cms.library.dao;

import java.util.List;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.library.model.ColumnModelFieldMap;

/**
 * 数据库模板字段Map
 * 
 * @author shishb
 * @version 1.0
 */
public interface ColumnModelFieldMapMapper extends BaseDao<ColumnModelFieldMap> {
	List<ColumnModelFieldMap> findByColumnModelId(int columnModelId);

	void batchInsert(List<ColumnModelFieldMap> list);

	void deleteByColumnModelId(int id);

	void batchDelete(Integer[] ids);
}
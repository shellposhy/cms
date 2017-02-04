package cn.com.cms.framework.base.dao;

import java.util.List;

import cn.com.cms.framework.base.BaseEntity;

/**
 * 数据库公共操作基础方法
 * 
 * @author shishb
 */
public interface BaseDao<T extends BaseEntity> {
	List<T> findAll();

	T find(Integer id);

	void insert(T entity);

	void update(T entity);

	void delete(Integer id);
}

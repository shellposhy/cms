package cn.com.cms.view.dao;

import java.util.List;

import cn.com.cms.view.model.ViewModelCategory;

/**
 * 页面模板目录表服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface ViewModelCategoryMapper {

	void insert(ViewModelCategory viewModelCategory);

	ViewModelCategory find(int id);

	List<ViewModelCategory> findAll();

	List<ViewModelCategory> findByParentId(int parentId);

	void update(ViewModelCategory viewModelCategory);

	void delete(int id);
}
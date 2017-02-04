package cn.com.cms.view.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.view.constant.EModelType;
import cn.com.cms.view.model.ViewModel;

/**
 * 页面发布-首页首页模板服务类
 * 
 * @author shishb
 */
public interface ViewModelMapper extends BaseDao<ViewModel> {

	List<ViewModel> findByPage(@Param("firstResult") int firstResult, @Param("maxResult") int maxResult);

	int count();

	public List<ViewModel> findByType(EModelType modelType);

	List<ViewModel> findByCategoryId(int categoryId);
}

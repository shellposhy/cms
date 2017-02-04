package cn.com.cms.view.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.view.model.ViewItem;

/**
 * 页面发布-首页区域内容类型Mapper类
 * <p>
 * 主要用户首页发布配置内容
 * 
 * @author shishb
 */
public interface ViewItemMapper extends BaseDao<ViewItem> {
	List<ViewItem> findByModelId(Integer modelId);

	List<ViewItem> findByPage(Integer firstResult, Integer maxResult);

	ViewItem findByModelIdAndCode(@Param("modelId") Integer modelId, @Param("code") String code);

	void deleteByModelId(Integer modelId);

	void batchInsert(List<ViewItem> list);
}

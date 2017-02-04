package cn.com.cms.view.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.view.model.ViewContent;

/**
 * 首页内容配置Mapper类
 * 
 * @author shishb
 * @version 1.0
 */
public interface ViewContentMapper extends BaseDao<ViewContent> {

	/**
	 * 根据页面编号和区域编号查询
	 * 
	 * @param pageId
	 *            页面编号
	 * @param itemId
	 *            区域编号
	 * @return {@link ViewContent}
	 */
	ViewContent findByPageIdAndItemId(@Param("pageId") Integer pageId, @Param("itemId") Integer itemId);

	/**
	 * 根据页面编号查询内容列表
	 * 
	 * @param pageId
	 *            页面编号
	 * @return {@link List}
	 */
	List<ViewContent> findByPageId(Integer pageId);

	/**
	 * 根据页面编号和区域编号查询
	 * 
	 * @param pageId
	 *            页面编号
	 * @param itemId
	 *            区域编号
	 * @return {@link ViewContent}
	 */
	void deleteByPageIdAndItemId(@Param("pageId") Integer pageId, @Param("itemId") Integer itemId);

	/**
	 * 根据pageId和itemId进行删除
	 * 
	 * @param pageId
	 *            页面id
	 * @param itemId
	 *            区域id
	 * @return
	 * 
	 */
	void deleteByPageId(@Param("pageId") Integer pageId);
}

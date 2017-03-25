package cn.com.cms.view.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.view.constant.EPageType;
import cn.com.cms.view.model.ViewPage;

/**
 * 首页发布Mapper类
 */
public interface ViewPageMapper extends BaseDao<ViewPage> {

	/**
	 * 分页查询结果集
	 * 
	 * @param qs
	 * @param firstResult
	 * @param pageSize
	 * @throws NullPointerException
	 * @return
	 */
	List<ViewPage> search(@Param("qs") String qs, @Param("first") int first, @Param("size") int size);

	/**
	 * 根据查询串统计查询到的结果数
	 * 
	 * @param qs
	 *            查询串
	 * @return int
	 */
	int count(@Param("qs") String qs);

	/**
	 * 根据页面发布状态统计页面总数
	 * 
	 * @param status
	 *            页面发布状态
	 * @return {@link Integer}
	 */
	int countByStatus(EDataStatus status);

	/**
	 * 删除（支持批量删除）
	 * 
	 * @param pageIds
	 *            首页编号数组
	 * @return {@link Void}
	 */
	void batchDelete(Integer[] ids);

	/**
	 * 获得所有可选用的系统首页
	 * 
	 * @param pageType
	 *            首页类型
	 * @param status
	 *            首页发布状态
	 * @return {@link List}
	 */
	List<ViewPage> findByTypeAndStatus(@Param("pageType") EPageType pageType, @Param("status") EDataStatus status);

	/**
	 * 根据类别查询
	 * 
	 * @param pageType
	 *            首页类型
	 * @return {@link List}
	 */
	List<ViewPage> findByType(@Param("pageType") Integer pageType);
	
	/**
	 * 根据发布状态查询
	 * 
	 * @param status
	 * @return
	 */
	List<ViewPage> findByStatus(@Param("status") Integer status);
}

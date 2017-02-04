package cn.com.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.user.model.UserGroup;

/**
 * 用户组服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserGroupMapper extends BaseDao<UserGroup> {

	/**
	 * 根据用户组名称查询
	 * 
	 * @param name
	 * @return
	 */
	UserGroup findByName(String name);

	/**
	 * 查询是否没有用户的用户组
	 * 
	 * @param
	 */
	List<UserGroup> findHasUser();

	/**
	 * 根据用户组编码查询
	 * 
	 * @param code
	 * @return
	 */
	UserGroup findByCode(String code);

	/**
	 * 分页查询用户组
	 * 
	 * @param first
	 * @param size
	 * @return
	 */
	List<UserGroup> search(@Param("first") int first, @Param("size") int size);

	/**
	 * 根据关键词分页查询用户组
	 * 
	 * @param qs
	 * @param first
	 * @param size
	 * @return
	 */
	List<UserGroup> searchByQs(@Param("qs") String qs, @Param("first") int first, @Param("size") int size);

	/**
	 * 统计用户组数量
	 * 
	 * @return
	 */
	int count();

	/**
	 * 根据关键词查询统计用户组数目
	 * 
	 * @param qs
	 * @return
	 */
	int countByQs(String qs);

	/**
	 * 统计当前日期有没有新增用户组
	 * 
	 * @return
	 */
	int countByToday();

	/**
	 * 统计用户组下的所有权限数目
	 * 
	 * @param actionCode
	 * @param groupId
	 * @return
	 */
	int countActionForGroup(@Param("actionCode") String actionCode, @Param("groupId") int groupId);

	/**
	 * 批量删除用户组
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(Integer[] ids);

	/**
	 * 根据用户编号查找用户组
	 * 
	 * @param userId
	 * @return
	 */
	List<UserGroup> findByUserId(Integer userId);

	/**
	 * 查询用户的默认页面
	 * 
	 * @param userId
	 * @return
	 */
	Integer findDefaultPageIdByUserId(Integer userId);
}

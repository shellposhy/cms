package cn.com.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.user.model.User;

/**
 * 用户服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserMapper extends BaseDao<User> {
	/**
	 * 根据用户名查找
	 * 
	 * @param name
	 */
	User findByName(String name);

	/**
	 * 根据用户名查找
	 * 
	 * @param realName
	 */
	List<User> findByRealName(String realName);

	List<User> findByOrgId(int orgID);

	User findByPhone(String phoneNumber);

	int countByOrgAndUserName(@Param("orgID") Integer orgID, @Param("name") String name);

	List<User> findByOrgAndUserName(@Param("orgID") Integer orgID, @Param("name") String name,
			@Param("firstResult") int firstResult, @Param("maxResult") int maxResult);

	/**
	 * 关键词分页查询所有用户
	 * 
	 * 
	 * @param qs
	 *            关键词
	 * @param first
	 *            第一条
	 * @param size
	 *            页大小
	 */
	List<User> searchUsers(@Param("qs") String qs, @Param("first") int first, @Param("size") int size);

	/**
	 * 统计Ip用户
	 * 
	 * @param qs
	 *            关键字
	 */
	int countUsers(@Param("qs") String qs);

	/**
	 * 关键词分页查询IP用户
	 * 
	 * 
	 * @param qs
	 *            关键词
	 * @param first
	 *            第一条
	 * @param size
	 *            页大小
	 */
	List<User> searchIpUsers(@Param("qs") String qs, @Param("first") int first, @Param("size") int size);

	/**
	 * 获得密码和所有IP用户
	 * 
	 * @return
	 */
	List<User> searchAllIpPwdUsers();

	/**
	 * 关键词分页查询密码用户
	 * 
	 * @param qs
	 *            关键词
	 * @param first
	 *            第一条
	 * @param size
	 *            页大小
	 */
	List<User> searchPwUsers(@Param("qs") String qs, @Param("first") int first, @Param("size") int size);

	/**
	 * 统计Ip用户
	 * 
	 * @param qs
	 *            关键字
	 */
	int countIpUsers(@Param("qs") String qs);

	/**
	 * 统计密码用户
	 * 
	 * @param qs
	 *            关键字
	 */
	int countPwUsers(@Param("qs") String qs);

	/**
	 * 统计IP+密码用户
	 * 
	 * @param qs
	 *            关键字
	 */
	int countIPAndPwUsers(@Param("qs") String qs);

	/**
	 * 今日新增用户
	 */
	int countByToday(@Param("qs") String qs);

	void batchPetrify(List<User> users);

	void petrify(@Param("user") User user);

	/**
	 * 查找所有活着的用户
	 * 
	 * @return
	 */
	List<User> findAlive(@Param("qs") String qs);

	User findByNamePwd(@Param("name") String name, @Param("pwd") String pwd);
}

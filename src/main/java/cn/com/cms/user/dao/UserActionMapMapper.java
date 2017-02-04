package cn.com.cms.user.dao;

import java.util.List;

import cn.com.cms.user.model.UserActionMap;

/**
 * 用户组权限服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserActionMapMapper {
	/**
	 * 根据用户组查询所有权限节点
	 * 
	 * @param groupId
	 * @return
	 */
	List<Integer> findActionIdsByGroupId(Integer groupId);

	/**
	 * 批量新增
	 * 
	 * @param list
	 * @return
	 */
	void batchInsert(List<UserActionMap> list);

	/**
	 * 根据用户组删除
	 * 
	 * @param groupId
	 * @return
	 */
	void deleteByGroupId(Integer groupId);

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<UserActionMap> findAll();

	/**
	 * 查询所有前台权限
	 * 
	 * @param groupId
	 * @return
	 */
	List<Integer> findFrontActionIdsByGroupId(Integer groupId);

	/**
	 * 获得所有后台权限
	 * 
	 * @param groupId
	 * @return
	 */
	List<Integer> findAdminActionIdsByGroupId(Integer groupId);

}

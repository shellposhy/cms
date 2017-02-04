package cn.com.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.user.model.UserGroupMap;

/**
 * 用户组用户映射服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserGroupMapMapper {
	/**
	 * 根据用户组编号删除
	 * 
	 * @param groupId
	 */
	void deleteByGroupId(Integer groupId);

	/**
	 * 根据用户编号查询所有的用户组
	 * 
	 * @param userId
	 * @return
	 */
	List<Integer> findGroupIdsByUserId(Integer userId);

	/**
	 * 根据用户组编号查询所有的用户
	 * 
	 * @param groupId
	 */
	List<Integer> findUserIdsByGroupId(Integer groupId);

	/**
	 * 根据用户组编号分页查询所有的用户
	 * 
	 * @param groupId
	 * @param qs
	 * @param first
	 * @param size
	 * @return
	 */
	List<Integer> searchUserIdsByGroupId(@Param("groupId") Integer groupId, @Param("qs") String qs,
			@Param("first") int first, @Param("size") int size);

	/**
	 * 统计指定用户组所有用户数
	 * 
	 * @param qs
	 * @param groupId
	 * @return
	 */
	int countUsers(@Param("qs") String qs, @Param("groupId") Integer groupId);

	/**
	 * 获得不具有指定用户组的所有用户编号
	 * 
	 * @param groupId
	 * @param qs
	 * @param first
	 * @param size
	 * @return
	 */
	List<Integer> findUserIdsNotInGroupId(@Param("groupId") Integer groupId, @Param("qs") String qs,
			@Param("first") int first, @Param("size") int size);

	/**
	 * 统计不具有指定用户组所有用户数
	 * 
	 * @param qs
	 * @param groupId
	 * @return
	 */
	int countNotInGroupIdUser(@Param("qs") String qs, @Param("groupId") Integer groupId);

	/**
	 * 批量新增
	 * 
	 * @param list
	 * @return
	 */
	void batchInsert(List<UserGroupMap> list);

	/**
	 * 根据用户编号删除用户组
	 * 
	 * @param id
	 * @return
	 */
	void deleteByUserId(Integer id);
}

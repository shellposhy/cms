package cn.com.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.user.model.UserDataAuthority;

/**
 * 用户数据权限服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserDataAuthorityMapper {

	/**
	 * 根据用户组编号查询用户组数据权限
	 * 
	 * @param groupId
	 * @param type
	 * @return
	 */
	List<UserDataAuthority> findByGroupId(@Param("groupId") Integer groupId, @Param("type") ELibraryType type);

	/**
	 * 查询所有用户组数据权限
	 * 
	 * @return
	 */
	List<UserDataAuthority> findAll();

	/**
	 * 根据用户组编号删除用户组数据权限
	 * 
	 * @param groupId
	 * @return
	 */
	void deleteByGroupID(Integer groupId);

	/**
	 * 批量新增
	 * 
	 * @param list
	 * @return
	 */
	void batchInsert(List<UserDataAuthority> list);
}

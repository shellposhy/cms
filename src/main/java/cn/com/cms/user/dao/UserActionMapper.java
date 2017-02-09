package cn.com.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.user.model.UserAction;

/**
 * 权限服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserActionMapper {
	/**
	 * 获取全部权限
	 * 
	 * @return
	 */
	List<UserAction> findAll();

	/**
	 * 根据编码查询
	 * 
	 * @param code
	 * @return
	 */
	UserAction findByCode(String code);

	/**
	 * 获得后台权限树
	 * 
	 * @return
	 */
	List<UserAction> findAdmin();

	/**
	 * 根据权限编号查询
	 * 
	 * @param ids
	 * @return
	 */
	List<UserAction> findAdminByIds(@Param("ids") List<Integer> ids);
}
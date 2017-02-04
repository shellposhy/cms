package cn.com.cms.user.vo;

import cn.com.cms.user.model.UserGroup;

/**
 * 用户组VO对象
 * 
 * @author shishb
 * @version 1.0
 */
public class UserGroupVO {
	private Integer id;
	private String name;
	private String memo;
	private String userStr;

	public Integer getId() {
		return id;
	}

	public String getUserStr() {
		return userStr;
	}

	public void setUserStr(String userStr) {
		this.userStr = userStr;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 静态调用方法生成
	 * @param userGroup
	 * @return
	 */
	public static UserGroupVO convertUserGroup(UserGroup userGroup) {
		UserGroupVO userGroupVo = new UserGroupVO();
		userGroupVo.setId(userGroup.getId());
		userGroupVo.setMemo(userGroup.getMemo());
		userGroupVo.setName(userGroup.getName());
		return userGroupVo;
	}
}

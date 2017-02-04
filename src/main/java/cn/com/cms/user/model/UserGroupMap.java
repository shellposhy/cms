package cn.com.cms.user.model;

/**
 * 用户组用户映射表
 * 
 * @author shishb
 * @version 1.0
 */
public class UserGroupMap {
	private Integer id;
	private Integer groupID;
	private Integer userID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}

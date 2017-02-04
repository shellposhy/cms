package cn.com.cms.user.model;

/**
 * 用户组权限映射表
 * 
 * @author shishb
 * @version 1.0
 */
public class UserActionMap {
	private Integer id;
	private Integer groupID;
	private Integer actionID;
	private Integer type;

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

	public Integer getActionID() {
		return actionID;
	}

	public void setActionID(Integer actionID) {
		this.actionID = actionID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}

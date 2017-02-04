package cn.com.cms.user.model;

/**
 * 机构用户组表
 * 
 * @author shishb
 * @version 1.0
 */
public class OrgGroupMap {
	private Integer id;
	private Integer orgId;
	private Integer groupId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}

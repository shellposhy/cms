package cn.com.cms.user.model;

import java.util.Date;

import cn.com.cms.framework.base.TreeNodeEntity;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.user.constant.EActionType;

/**
 * 数据权限实体类
 * 
 * @author shishb
 * @version 1.0
 */
public class UserDataAuthority extends TreeNodeEntity<UserDataAuthority> {
	private static final long serialVersionUID = 5008539918109602334L;
	private Integer id;
	private Integer groupId;
	private ELibraryType objType;
	private Integer objId;
	private boolean allDataTime;
	private String allowActionType;
	private Date startDataTime;
	private Date endDateTime;
	private Date createTime;
	private Integer creatorId;
	private Date updateTime;
	private Integer updaterId;

	public ELibraryType getObjType() {
		return objType;
	}

	public void setObjType(ELibraryType objType) {
		this.objType = objType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public boolean isAllDataTime() {
		return allDataTime;
	}

	public void setAllDataTime(boolean allDataTime) {
		this.allDataTime = allDataTime;
	}

	public String getAllowActionType() {
		return allowActionType;
	}

	public void addAllowActionType(EActionType type) {
		if (null != allowActionType && !allowActionType.isEmpty()) {
			allowActionType += SystemConstant.COMMA_SEPARATOR + type;
		} else {
			allowActionType = type.toString();
		}
	}

	public void setAllowActionType(String allowActionType) {
		this.allowActionType = allowActionType;
	}

	public Date getStartDataTime() {
		return startDataTime;
	}

	public void setStartDataTime(Date startDataTime) {
		this.startDataTime = startDataTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Integer updaterId) {
		this.updaterId = updaterId;
	}

}

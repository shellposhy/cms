package cn.com.cms.user.model;

import java.util.Date;

import cn.com.cms.framework.base.tree.TreeNodeEntity;
import cn.com.cms.library.constant.EStatus;

public class Org extends TreeNodeEntity<Org> {
	private static final long serialVersionUID = 1L;
	private String code;
	private Integer orderID;
	private EStatus status;
	private boolean inherit;
	private Date createTime;
	private Integer creatorId;
	private Date updateTime;
	private Integer updaterId;
	private String treeSelId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
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

	public String getTreeSelId() {
		return treeSelId;
	}

	public void setTreeSelId(String treeSelId) {
		this.treeSelId = treeSelId;
	}

	public boolean isInherit() {
		return inherit;
	}

	public void setInherit(boolean inherit) {
		this.inherit = inherit;
	}

}

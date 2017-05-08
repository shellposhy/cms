package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.Date;

/**
 * <code>model</code>基类
 * 
 * @author shishb
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 3988400631289702405L;
	protected Integer id;
	protected Integer creatorId;
	protected Date createTime;
	protected Integer updaterId;
	protected Date updateTime;

	public BaseEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Integer updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}

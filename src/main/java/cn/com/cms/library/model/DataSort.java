package cn.com.cms.library.model;

import java.util.Date;

import cn.com.cms.framework.base.TreeNodeEntity;
import cn.com.cms.library.constant.EStatus;

/**
 * 数据分类对象
 * 
 * @author shishb
 * @version 1.0
 */
public class DataSort extends TreeNodeEntity<DataSort> {
	private static final long serialVersionUID = 1L;
	private String code;
	private String pathCode;
	private int type;
	private Integer baseId;
	private Integer orderId;
	private EStatus status;
	private boolean forSys;
	private Date createTime;
	private Integer creatorId;
	private Date updateTime;
	private Integer updaterId;
	private boolean forDataNode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public boolean isForSys() {
		return forSys;
	}

	public void setForSys(boolean forSys) {
		this.forSys = forSys;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isForDataNode() {
		return forDataNode;
	}

	public void setForDataNode(boolean forDataNode) {
		this.forDataNode = forDataNode;
	}

}

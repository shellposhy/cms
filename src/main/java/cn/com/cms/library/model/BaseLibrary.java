package cn.com.cms.library.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.framework.base.TreeNodeEntity;
import cn.com.cms.library.constant.ELibraryNodeType;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.constant.EStatus;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 数据库基础类
 * <p>
 * 其他数据库如(图片库、媒体库、新闻库等)可以继承此基础类
 * 
 * @author shishb
 * @version 1.0
 * @see TreeNodeEntity
 * @see BaseEntity
 */
public class BaseLibrary<T extends BaseLibrary<T>> extends TreeNodeEntity<T> {
	private static final long serialVersionUID = 1L;
	protected String code;
	protected String pathCode;
	protected ELibraryType type;
	protected ELibraryNodeType nodeType;
	protected Integer modelId;
	protected EStatus status;
	protected int orderId;
	protected Integer taskId;
	protected Date dataUpdateTime;
	protected List<DataField> dataFields;
	protected int tables;
	protected String dataFieldsStr;

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

	public void setPathCode(T parent) {
		if (null == parent) {
			this.pathCode = "/" + this.getCode() + "/";
		} else {
			this.pathCode = parent.getPathCode() + this.getCode() + "/";
		}
	}

	public ELibraryType getType() {
		return type;
	}

	public void setType(ELibraryType type) {
		this.type = type;
	}

	public ELibraryNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(ELibraryNodeType nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public int getTables() {
		return tables;
	}

	public void setTables(int tables) {
		this.tables = tables;
	}

	public Date getDataUpdateTime() {
		return dataUpdateTime;
	}

	public void setDataUpdateTime(Date dataUpdateTime) {
		this.dataUpdateTime = dataUpdateTime;
	}

	public String getDataUpdateTimeStr() {
		return DateTimeUtil.formatDateTime(dataUpdateTime);
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public List<DataField> getDataFields() {
		return dataFields;
	}

	public void setDataFields(List<DataField> dataFields) {
		this.dataFields = dataFields;
	}

	public void addDataField(DataField dataField) {
		if (null == dataFields) {
			dataFields = new ArrayList<DataField>();
		}
		if (!dataFields.contains(dataField)) {
			dataFields.add(dataField);
		}
	}

	public String getDataFieldsStr() {
		return dataFieldsStr;
	}

	public void setDataFieldsStr(String dataFieldsStr) {
		this.dataFieldsStr = dataFieldsStr;
	}

}

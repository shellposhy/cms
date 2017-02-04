package cn.com.cms.library.model;

import cn.com.cms.framework.base.BaseEntity;

/**
 * 数据库模板字段映射表
 * 
 * @author shishb
 * @version 1.0
 */
public class ColumnModelFieldMap extends BaseEntity {
	private static final long serialVersionUID = 8985677374453856104L;

	private Integer columnModelId;
	private Integer fieldId;

	public Integer getColumnModelId() {
		return columnModelId;
	}

	public void setColumnModelId(Integer columnModelId) {
		this.columnModelId = columnModelId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

}

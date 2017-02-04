package cn.com.cms.data.model;

import cn.com.cms.framework.base.BaseEntity;

/**
 * 数据表格的表格
 * 
 * @author shishb
 * @version 1.0
 */
public class DataTable extends BaseEntity {
	private static final long serialVersionUID = 7028169567635866363L;

	private Integer baseId;
	private String name;
	private Integer rowCount;

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
}

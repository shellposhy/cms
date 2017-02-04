package cn.com.cms.library.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.library.constant.ELibraryType;

/**
 * 数据库字段表
 * 
 * @author shishb
 * @version 1.0
 */
public class DataBaseFieldMap extends BaseEntity {
	private static final long serialVersionUID = -6341669371715998976L;

	private Integer baseId;
	private Integer fieldId;
	private ELibraryType type;
	private boolean isDisplay;

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public ELibraryType getType() {
		return type;
	}

	public void setType(ELibraryType type) {
		this.type = type;
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

}

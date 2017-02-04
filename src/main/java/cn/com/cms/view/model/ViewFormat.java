
package cn.com.cms.view.model;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.view.constant.EFormatType;

/**
 * 显示方式对象
 * 
 * @author shishb
 * @version 1.0
 */
public class ViewFormat extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer baseId;
	private EFormatType formatType;
	private String formatFields;

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public EFormatType getFormatType() {
		return formatType;
	}

	public void setFormatType(EFormatType formatType) {
		this.formatType = formatType;
	}

	public String getFormatFields() {
		return formatFields;
	}

	public void setFormatFields(String formatFields) {
		this.formatFields = formatFields;
	}

}

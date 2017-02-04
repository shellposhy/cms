package cn.com.cms.library.model;

import java.util.List;

import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.library.constant.ELibraryType;

/**
 * 数据库模板库
 * 
 * @author shishb
 * @version 1.0
 */
public class ColumnModel extends BaseEntity {
	private static final long serialVersionUID = -1667940019940586638L;

	private String name;
	private String code;
	private ELibraryType type;
	private String describe;
	private String memo;
	private boolean forSys;

	private String dataFieldsStr;
	private String moreDataFieldsStr;
	private List<DataField> dataFields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ELibraryType getType() {
		return type;
	}

	public void setType(ELibraryType type) {
		this.type = type;
	}

	public String getDescribe() {
		return describe;
	}

	public String getFullName() {
		return name + "（" + describe + "）";
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDataFieldsStr() {
		return dataFieldsStr;
	}

	public void setDataFieldsStr(String dataFieldsStr) {
		this.dataFieldsStr = dataFieldsStr;
	}

	public String getMoreDataFieldsStr() {
		return moreDataFieldsStr;
	}

	public void setMoreDataFieldsStr(String moreDataFieldsStr) {
		this.moreDataFieldsStr = moreDataFieldsStr;
	}

	public List<DataField> getDataFields() {
		return dataFields;
	}

	public void setDataFields(List<DataField> dataFields) {
		this.dataFields = dataFields;
	}

	public boolean isForSys() {
		return forSys;
	}

	public void setForSys(boolean forSys) {
		this.forSys = forSys;
	}

}

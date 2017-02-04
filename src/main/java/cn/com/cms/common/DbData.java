package cn.com.cms.common;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.cms.data.model.DataField;
import cn.com.cms.library.constant.EDataType;

/**
 * 数据库数据对象
 * 
 * @author shishb
 * @version 1.0
 */
public class DbData {

	private List<Map.Entry<DataField, Object>> entryList;
	private boolean hasBlob;
	private Integer ver;
	private String description;

	public DbData() {
		hasBlob = false;
		entryList = new ArrayList<Map.Entry<DataField, Object>>();
	}

	public void add(DataField field, Object value) {
		EDataType dataType = field.getDataType();
		if (EDataType.Blob.equals(dataType) || EDataType.MediumBlob.equals(dataType)) {
			hasBlob = true;
		}
		entryList.add(new AbstractMap.SimpleEntry<DataField, Object>(field, value));
	}

	public List<Entry<DataField, Object>> getEntryList() {
		return entryList;
	}

	public boolean hasBlob() {
		return hasBlob;
	}

	public int fieldCount() {
		return entryList.size();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}
}

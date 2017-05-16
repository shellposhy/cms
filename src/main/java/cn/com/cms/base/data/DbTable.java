package cn.com.cms.base.data;

import java.util.List;

import cn.com.cms.data.model.DataField;

/**
 * 数据表格
 * 
 * @author shishb
 * @version 1.0
 */
public class DbTable {
	private String name;
	private List<DataField> fieldList;
	private List<String> names;
	private List<DataField> adds;
	private List<DataField> drops;
	private List<DataField> changes;

	public DbTable() {
	}

	public DbTable(String name, List<DataField> fieldList) {
		this.name = name;
		this.fieldList = fieldList;
	}

	public List<DataField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<DataField> fieldList) {
		this.fieldList = fieldList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DataField> getAdds() {
		return adds;
	}

	public void setAdds(List<DataField> adds) {
		this.adds = adds;
	}

	public List<DataField> getChanges() {
		return changes;
	}

	public void setChanges(List<DataField> changes) {
		this.changes = changes;
	}

	public List<DataField> getDrops() {
		return drops;
	}

	public void setDrops(List<DataField> drops) {
		this.drops = drops;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}

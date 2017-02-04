package cn.com.cms.library.vo;

import cn.com.cms.library.model.ColumnModel;

/**
 * 数据库模板VO
 * 
 * @author shishb
 * @version 1.0
 */
public class ColumnModelVo {
	private Integer id;
	private String name;
	private String type;
	private String describe;
	private boolean forSys;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static ColumnModelVo convertFromColumnModel(ColumnModel columnModel) {
		ColumnModelVo columnModelVo = new ColumnModelVo();
		columnModelVo.setId(columnModel.getId());
		columnModelVo.setName(columnModel.getName());
		columnModelVo.setType(columnModel.getType().getTitle());
		columnModelVo.setDescribe(columnModel.getDescribe());
		columnModelVo.setForSys(columnModel.isForSys());
		return columnModelVo;
	}

	public boolean isForSys() {
		return forSys;
	}

	public void setForSys(boolean forSys) {
		this.forSys = forSys;
	}

}

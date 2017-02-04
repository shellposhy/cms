package cn.com.cms.system.model;

import cn.com.cms.system.contant.ESysParamType;

/**
 * 系统初始化参数对象
 * 
 * @author shishb
 * @version 1.0
 */
public class SysParameter {
	private Integer id;
	private String name;
	private String code;
	private String value;
	private ESysParamType paramType;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ESysParamType getParamType() {
		return paramType;
	}

	public void setParamType(ESysParamType paramType) {
		this.paramType = paramType;
	}
}
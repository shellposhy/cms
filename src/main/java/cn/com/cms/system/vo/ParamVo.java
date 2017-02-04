package cn.com.cms.system.vo;

import cn.com.cms.system.model.SysParameter;

/**
 * 系统参数VO
 * 
 * @author shishb
 * @version 1.0
 */
public class ParamVo {
	private Integer id;
	private String name;
	private String code;
	private String value;
	private String paramType;

	public static ParamVo convert(SysParameter parameter) {
		ParamVo vo = new ParamVo();
		vo.setId(parameter.getId());
		vo.setName(parameter.getName());
		vo.setCode(parameter.getCode());
		vo.setValue(parameter.getValue());
		vo.setParamType(parameter.getParamType().getName());
		return vo;
	}

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

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
}

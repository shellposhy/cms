package cn.com.cms.data.util;

/**
 * 高级查询，封装数据字段实体类
 * 
 * @author shishb
 * @version 1.0
 * 
 */
public class DataFieldVo {
	private Integer id;
	private String name;
	private String code;
	private Integer dataType;

	public DataFieldVo() {
		super();
	}

	public DataFieldVo(Integer id, String name, String code, Integer dataType) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.dataType = dataType;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
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
}

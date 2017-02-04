package cn.com.cms.library.constant;

/**
 * 数据库字段枚举类型
 * 
 * @author shishb
 * @version 1.0
 */
public enum EDataFieldType {
	Required("必须字段"), Normal("普通字段");

	private final String title;

	EDataFieldType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}

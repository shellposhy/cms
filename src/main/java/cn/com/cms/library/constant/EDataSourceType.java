package cn.com.cms.library.constant;

/**
 * 数据源类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EDataSourceType {
	STANDALONE("独立"), BINDING("绑定"), GROUP("组合");

	private final String title;

	EDataSourceType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}

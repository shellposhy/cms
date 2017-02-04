package cn.com.cms.library.constant;

/**
 * 数据源来源枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EDataNavigateType {
	DATA_BASE(" 数据库"), DATA_CATEGORY("数据库分类"), DATA_SORT("数据标签");
	private String title;

	private EDataNavigateType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}

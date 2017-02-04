package cn.com.cms.view.constant;

/**
 * 链接类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ENameLinkType {

	UserLink("自定义"), ColumnLink("专题或栏目首页"), NormalListLink("通用列表");
	private String title;

	private ENameLinkType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public static ENameLinkType valueOf(int ordinal) {

		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];

	}
}

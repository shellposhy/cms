package cn.com.cms.view.constant;

/**
 * 页面类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EPageType {
	UserPage("自定义"), SysPage("首页"), SubjectPage("专题页");

	public String getTitle() {
		return title;
	}

	private String title;

	private EPageType(String title) {
		this.title = title;
	}

	public static EPageType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}

package cn.com.cms.user.constant;

/**
 * 用户组登录页面配置枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELoginPageType {
	/**
	 * 系统首页-0
	 */
	SysPage("系统首页"),
	/**
	 * 自定义-1
	 */
	UserPage("自定义");

	public static ELoginPageType valueOf(int ordinal) {

		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];

	}

	private final String title;

	ELoginPageType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}

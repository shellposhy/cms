package cn.com.cms.user.constant;

/**
 * 登录类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELoginType {
	Default("Default"), IP("IP"), IP_PASSOWRD("IP&PASSWORD"), Phone("Phone");

	private final String title;

	ELoginType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

}

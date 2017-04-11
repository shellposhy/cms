package cn.com.cms.user.constant;

/**
 * 用户操作权限分类
 * 
 * @author shishb
 * @version 1.0
 */
public enum EActionUserType {
	Admin("后台/运营权限"), Client("前端/客户权限"), Crawler("数据抓取");

	private String title;

	private EActionUserType(String title) {
		this.title = title;
	}

	public static EActionUserType valueOf(int index) {
		return EActionUserType.values()[index];
	}

	public String getTitle() {
		return title;
	}
}

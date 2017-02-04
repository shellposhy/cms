package cn.com.cms.library.constant;

/**
 * 数据库字段访问权限类别
 * 
 * @author shishb
 * @version 1.0
 */
public enum EAccessType {
	Sys("系统"), Hidden("隐藏"), ReadOnly("只读"), Modifiable("可编辑");
	private String title;

	public String getTitle() {
		return title;
	}

	private EAccessType(String title) {
		this.title = title;
	}
}

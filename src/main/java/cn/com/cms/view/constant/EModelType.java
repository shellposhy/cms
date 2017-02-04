package cn.com.cms.view.constant;

/**
 * 模板类型
 * 
 * @author shishb
 * @version 1.0
 */
public enum EModelType {
	Index("首页模板"), Subject("专题模板"), List("列表页模板"), Detail("细览页模板");
	private final String title;

	EModelType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

}
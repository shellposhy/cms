package cn.com.cms.view.constant;

/**
 * 标题显示方式枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EFormatType {
	TAG_TITLE_BRACKET("[显示项A] 标题"), TAG_TITLE_COLON("显示项A : 标题"), TITLE_TAG_BRACKET("标题 [显示项A]"), TITLE("标题");

	private String title;

	private EFormatType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}

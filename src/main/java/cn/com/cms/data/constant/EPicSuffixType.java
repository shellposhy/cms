package cn.com.cms.data.constant;

/**
 * 图片后缀类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EPicSuffixType {
	jpg(".jpg"), jpeg(".jpeg"), png(".png"), gif(".gif"), bmp(".bmp");

	private EPicSuffixType(String title) {
		this.title = title;
	}

	private String title;

	public String getTitle() {
		return title;
	}
}

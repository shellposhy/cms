package cn.com.cms.library.constant;

/**
 * 图片库图片尺寸大小枚举类
 * 
 * @author shishb
 * @version 1.0
 */
public enum EImgSizeType {
	NO_LIMIT("不限制"), WIDTH_MAX_LIMIT("限制最大宽度"), HEIGHT_MAX_LIMIT("限制最大高度"), WIDTH_CONSTANT_LIMIT(
			"固定宽度"), HEIGHT_CONSTANT_LIMIT("固定高度");

	private final String title;

	EImgSizeType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}

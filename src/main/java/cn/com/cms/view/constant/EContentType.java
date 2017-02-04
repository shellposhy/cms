package cn.com.cms.view.constant;

/**
 * 页面内容类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EContentType {
	UserContent("自定义"), DbDataListContent("数据库文章"), MulPictures("图片联播");

	private String title;

	private EContentType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public static EContentType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];

	}

}

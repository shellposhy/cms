package cn.com.cms.view.constant;

/**
 * 页面项目枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EItemType {
	Default("默认列表"), Headline("头条新闻"), OneImgList("主图列表"), ImgList("图片列表"), Sort("数据分类");

	private final String title;

	EItemType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}

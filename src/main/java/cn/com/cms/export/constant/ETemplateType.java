package cn.com.cms.export.constant;

/**
 * 导出模板类型
 * 
 * @author shishb
 * @version 1.0
 */
public enum ETemplateType {
	BIMSwb("BIM商务标书"), BIMJsb("BIM技术标书");

	private ETemplateType(String title) {
		this.title = title;
	}

	public static ETemplateType valuesof(int index) {
		return ETemplateType.values()[index];
	}

	private String title;

	public String getTitle() {
		return title;
	}
}

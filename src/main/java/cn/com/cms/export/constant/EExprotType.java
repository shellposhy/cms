package cn.com.cms.export.constant;

/**
 * 导出文件类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EExprotType {
	Word(".doc"), Pdf(".pdf"), Excel(".xls");

	private EExprotType(String title) {
		this.title = title;
	}

	public static EExprotType valuesof(int index) {
		return EExprotType.values()[index];
	}

	private String title;

	public String getTitle() {
		return title;
	}
}

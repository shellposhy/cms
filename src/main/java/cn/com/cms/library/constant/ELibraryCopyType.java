package cn.com.cms.library.constant;

/**
 * 数据复制/迁移类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELibraryCopyType {
	Copy("数据复制"), Movie("数据移库");

	private ELibraryCopyType(String title) {
		this.title = title;
	}

	public static ELibraryCopyType valueof(int index) {
		return ELibraryCopyType.values()[index];
	}

	private String title;

	public String getTitle() {
		return title;
	}
}

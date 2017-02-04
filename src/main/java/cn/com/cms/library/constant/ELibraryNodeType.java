package cn.com.cms.library.constant;

/**
 * 数据库节点类型
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELibraryNodeType {
	Lib("库"), Directory("目录");

	private final String title;

	ELibraryNodeType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static ELibraryNodeType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

}

package cn.com.cms.library.constant;

/**
 * 系统数据库类别枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELibraryType {
	SYSTEM_DATA_BASE("系统数据库", "default"), USER_DATA_BASE("用户数据库", "user");

	private final String title;
	private final String code;

	ELibraryType(String title, String code) {
		this.title = title;
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public String getCode() {
		return code;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static ELibraryType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

	/**
	 * 数据库类型
	 * 
	 * @return
	 */
	public static ELibraryType[] dataBases() {
		ELibraryType[] result = { ELibraryType.SYSTEM_DATA_BASE };
		return result;
	}

}

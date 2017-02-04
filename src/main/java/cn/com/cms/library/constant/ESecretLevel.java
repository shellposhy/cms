package cn.com.cms.library.constant;

/**
 * 安全级别枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ESecretLevel {
	Unclassified("0-不保密"), Micro("1-初级保密"), Low("2-低级保密"), Middle("3-中级保密"), High("4-高级保密"), Top("5-顶级保密");

	private final String title;

	ESecretLevel(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static ESecretLevel valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}

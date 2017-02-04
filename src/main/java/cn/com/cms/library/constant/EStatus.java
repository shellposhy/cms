package cn.com.cms.library.constant;

/**
 * 数据库状态
 * 
 * @author shishb
 * @version 1.0
 */
public enum EStatus {
	Petrified("锁定"), Normal("正常"), Stop("停止"), Repairing("修复");

	private final String title;

	EStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static EStatus valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}

package cn.com.cms.data.constant;

/**
 * 逻辑性枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELogicType {
	And("And"), Or("Or"), Not("Not");

	private final String title;

	ELogicType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public static ELogicType valueOf(int index) {
		return ELogicType.values()[index];
	}
}
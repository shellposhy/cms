package cn.com.cms.library.constant;

/**
 * 数据状态枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EDataStatus {
	Original("起草"), Edited("已编辑"), Sent("待审核"), Yes("审核通过"), No("审核未通过"), Publish("已发布"), Deleted("已删除");
	private final String title;

	EDataStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public static EDataStatus valueOf(int index) {
		return EDataStatus.values()[index];
	}
}
package cn.com.cms.system.contant;

/**
 * 操作目标枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELogTargetType {
	Sys, User, UserGroup, DataBase, DataSort, Log, Article, Attachment;
	private static final String[] chineseValue = { "系统", "用户", "用户组", "数据库", "数据标签", "日志", "文章", "附件" };

	public String toChinese() {
		return chineseValue[this.ordinal()];
	}
}

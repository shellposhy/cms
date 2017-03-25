package cn.com.cms.system.contant;

/**
 * 拦截类型枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EInterceptStatus {
	NoSession, NoPermission, Pass;
	private static final String[] chineseValue = { "未登录", "没有权限", "通过" };

	public String toChinese() {
		return chineseValue[this.ordinal()];
	}
}

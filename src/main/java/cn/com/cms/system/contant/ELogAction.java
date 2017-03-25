package cn.com.cms.system.contant;

/**
 * 日志动作类型
 * 
 * @author shishb
 * @version 1.0
 */
public enum ELogAction {
	Login, Logout, SaveInsert, Delete, Edit, SaveUpdate, Search, View, Download, Print;
	private static final String[] chineseValue = { "登录", "退出", "保存添加", "删除", "准备修改", "保存修改", "查询", "文章细览", "下载", "打印" };

	public String toChinese() {
		return chineseValue[this.ordinal()];
	}
}

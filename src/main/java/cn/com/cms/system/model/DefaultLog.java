package cn.com.cms.system.model;

import java.io.Serializable;

/**
 * 定义日志类型
 */
public class DefaultLog implements Serializable {
	public static enum ELogAction {
		Login, Logout, SaveInsert, Delete, Edit, SaveUpdate, Search, View, Download, Print;

		private static final String[] chineseValue = { "登录", "退出", "保存添加", "删除", "准备修改", "保存修改", "查询", "文章细览", "下载",
				"打印" };

		public String toChinese() {
			return chineseValue[this.ordinal()];
		}
	}

	public static enum ELogTargetType {
		Sys, User, UserGroup, DataBase, DataSort, Log, Article, Attachment;
		private static final String[] chineseValue = { "系统", "用户", "用户组", "数据库", "数据标签", "日志", "文章", "附件" };

		public String toChinese() {
			return chineseValue[this.ordinal()];
		}
	}

	public static enum EInterceptStatus {
		// 未登录
		NoSession,
		// 没有权限
		NoPermission,
		// 通过
		Pass;

		private static final String[] chineseValue = { "未登录", "没有权限", "通过" };

		public String toChinese() {
			return chineseValue[this.ordinal()];
		}
	}

	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String ip;
	private String url;
	private long time;
	private ELogAction logAction;
	private ELogTargetType logTargetType;
	private int target;
	private String targetName;
	private String docUUID;
	private EInterceptStatus interceptStatus;
	private String method;
	private String uri;
	private Integer id;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ELogAction getLogAction() {
		return logAction;
	}

	public void setLogAction(ELogAction logAction) {
		this.logAction = logAction;
	}

	public ELogTargetType getLogTargetType() {
		return logTargetType;
	}

	public void setLogTargetType(ELogTargetType logTargetType) {
		this.logTargetType = logTargetType;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public String getDocUUID() {
		return docUUID;
	}

	public void setDocUUID(String docUUID) {
		this.docUUID = docUUID;
	}

	public EInterceptStatus getInterceptStatus() {
		return interceptStatus;
	}

	public void setInterceptStatus(EInterceptStatus interceptStatus) {
		this.interceptStatus = interceptStatus;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId).append(",").append(userName).append(",").append(ip).append(",").append(time).append(",")
				.append(logAction.ordinal()).append(",").append(logTargetType.ordinal()).append(",")
				.append(interceptStatus.ordinal()).append(",").append(target).append(",")
				.append(targetName.toLowerCase()).append(",").append(docUUID).append(",").append(url.toLowerCase());
		return sb.toString();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

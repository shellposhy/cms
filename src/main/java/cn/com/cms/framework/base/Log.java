package cn.com.cms.framework.base;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import cn.com.cms.system.contant.EInterceptStatus;
import cn.com.cms.system.contant.ELogAction;
import cn.com.cms.system.contant.ELogTargetType;

/**
 * 定义日志类型
 * 
 * @author shishb
 * @version 1.0
 */
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String method;
	private String uri;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
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

	/**
	 * 重写toString方法
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId).append(",").append(userName).append(",").append(ip).append(",").append(time).append(",")
				.append(logAction.ordinal()).append(",").append(logTargetType.ordinal()).append(",")
				.append(interceptStatus.ordinal()).append(",").append(target).append(",")
				.append(targetName.toLowerCase()).append(",").append(docUUID).append(",").append(url.toLowerCase());
		return sb.toString();
	}

	/**
	 * 比较两个日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int dateCompare(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(Calendar.YEAR) * 10000 + cal1.get(Calendar.MONTH) * 100 + cal1.get(Calendar.DATE)
				- (cal2.get(Calendar.YEAR) * 10000 + cal2.get(Calendar.MONTH) * 100 + cal2.get(Calendar.DATE));
	}

	/**
	 * 解析一行日志
	 * 
	 * @param lineLog
	 * @return
	 */
	public static Log formatLog(String lineLog) {
		Log baseLog = new Log();
		String[] values = lineLog.split(",", 11);
		baseLog.setUserId(Integer.valueOf(values[0]));
		baseLog.setUserName(values[1]);
		baseLog.setIp(values[2]);
		baseLog.setTime(Long.valueOf(values[3]));
		baseLog.setLogAction(ELogAction.values()[Integer.valueOf(values[4])]);
		baseLog.setLogTargetType(ELogTargetType.values()[Integer.valueOf(values[5])]);
		baseLog.setInterceptStatus(EInterceptStatus.values()[Integer.valueOf(values[6])]);
		baseLog.setTarget(Integer.valueOf(values[7]));
		baseLog.setTargetName(values[8]);
		baseLog.setDocUUID(values[9]);
		baseLog.setUrl(values[10]);
		return baseLog;
	}

	/**
	 * 日志数据是否符合查询条件
	 * 
	 * @param query
	 * @param log
	 * @return
	 */
	public static boolean isMatchQuery(String query, Log log) {
		if (log.getLogAction().toChinese().contains(query))
			return true;
		if (log.getLogTargetType().toChinese().contains(query))
			return true;
		if (log.getInterceptStatus().toChinese().contains(query))
			return true;
		if (String.valueOf(log.getUserId()).contains(query))
			return true;
		if (log.getUserName().contains(query))
			return true;
		if (log.getIp().contains(query))
			return true;
		if (log.getUrl().contains(query))
			return true;
		if (log.getTargetName().contains(query))
			return true;
		return false;
	}
}

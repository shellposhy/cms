package cn.com.cms.system.vo;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import cn.com.cms.framework.base.BaseLog;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 日志对象VO
 * 
 * @author shishb
 * @version 1.0
 */
public class LogVo {
	private String dateStr = "";
	private String userName = "";
	private String ip = "";
	private String url = "";
	private String logAction = "";
	private String targetName = "";

	public static List<LogVo> changeVoList(List<BaseLog> list, int type) {
		List<LogVo> result = Lists.newArrayList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LogVo logVo = new LogVo();
				BaseLog log = list.get(i);
				logVo.setIp(log.getIp());
				logVo.setDateStr(DateTimeUtil.formatDateTime(new Date(Long.valueOf(log.getTime()))));
				logVo.setUrl(log.getUrl());
				logVo.setUserName(log.getUserName());
				logVo.setTargetName(log.getTargetName());
				logVo.setLogAction(log.getLogAction().toChinese());
				if (!log.getTargetName().equals("null")) {
					logVo.setTargetName(log.getLogTargetType().toChinese() + "(" + log.getTargetName() + ")");
				} else {
					logVo.setTargetName(log.getLogTargetType().toChinese());
				}
				result.add(logVo);
			}
		}
		return result;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
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

	public String getLogAction() {
		return logAction;
	}

	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
}

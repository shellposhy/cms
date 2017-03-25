package cn.com.cms.system.model;

import java.util.Date;

import cn.com.cms.framework.base.BaseEntity;
import cn.com.cms.system.contant.ELogAction;
import cn.com.cms.system.contant.ELogTargetType;

/**
 * 访问记录对象
 * 
 * @author shishb
 * @version 1.0
 */
public class RecordVisit extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer groupId;
	private ELogAction action;
	private ELogTargetType target;
	private Integer visits;
	private Date visitTime;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer hour;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public ELogAction getAction() {
		return action;
	}

	public void setAction(ELogAction action) {
		this.action = action;
	}

	public ELogTargetType getTarget() {
		return target;
	}

	public void setTarget(ELogTargetType target) {
		this.target = target;
	}

	public Integer getVisits() {
		return visits;
	}

	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

}

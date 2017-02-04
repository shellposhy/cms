package cn.com.cms.system.model;

import java.util.Date;

import cn.com.cms.library.constant.EStatus;

/**
 * 日历事件对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CalendarEvent {
	private Integer id;
	private String eventName;
	private Date eventTime;
	private EStatus status;
	private String memo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}

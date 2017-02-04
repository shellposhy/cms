package cn.com.cms.system.dao;

import java.util.Date;

import cn.com.cms.system.model.CalendarEvent;

/**
 * 日历事件服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface CalendarEventMapper {
	CalendarEvent findRecentlyEvent(Date today);
}

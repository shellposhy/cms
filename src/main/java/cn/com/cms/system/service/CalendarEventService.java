package cn.com.cms.system.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.cms.base.AppConfig;
import cn.com.cms.system.dao.CalendarEventMapper;
import cn.com.cms.system.model.CalendarEvent;
import cn.com.people.data.util.ChineseCalendar;
import cn.com.people.data.util.DateTimeUtil;

@Service
public class CalendarEventService {
	Logger log = Logger.getLogger(CalendarEventService.class.getName());
	@Resource
	private CalendarEventMapper calendarEventMapper;
	@Resource
	private AppConfig appConfig;

	public Map<String, Object> calendarEventData() {
		Map<String, Object> calendarData = new HashMap<String, Object>();
		Date today = new Date();
		int pd_sdate_y = DateTimeUtil.getCurrentYear();
		int pd_sdate_m = DateTimeUtil.getCurrentMonth();
		int pd_sdate_d = DateTimeUtil.getCurrentDay();
		calendarData.put("pd_sdate_y", String.valueOf(pd_sdate_y));
		calendarData.put("pd_sdate_m", String.valueOf(pd_sdate_m));
		calendarData.put("pd_sdate_d", String.valueOf(pd_sdate_d));
		String pd_sdate_w = DateTimeUtil.getWeekOfDate(today);
		calendarData.put("pd_sdate_w", pd_sdate_w);
		CalendarEvent simpleCalendarEvent = calendarEventMapper.findRecentlyEvent(today);
		if (null != simpleCalendarEvent) {
			String event = simpleCalendarEvent.getEventName();
			calendarData.put("event", event);
			Date eventDate = simpleCalendarEvent.getEventTime();
			long interTheDays = Math.abs(DateTimeUtil.getDaysOfDate(today, eventDate));
			calendarData.put("interTheDays", String.valueOf(interTheDays));
		}
		ChineseCalendar chineseCalendar = new ChineseCalendar();
		calendarData.put("birthpet", chineseCalendar.getBirthpet());
		calendarData.put("chineseYear", chineseCalendar.getCYear());
		calendarData.put("chineseMonthAndDay", chineseCalendar.getCMonthAndDay());
		return calendarData;
	}
}

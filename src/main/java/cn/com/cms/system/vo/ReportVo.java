package cn.com.cms.system.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import cn.com.people.data.util.DateTimeUtil;

/**
 * 统计报表VO
 * 
 * @author shishb
 * @version 1.0
 */
public class ReportVo<T> {
	private String title; // 图表标题
	private String info; // 附属信息
	private List<T> data; // 生成图表数据

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 根据时间类型获得时间段
	 * 
	 * @param type
	 *            (最近1天 最近7天 最近30天)
	 * @return
	 */
	public static List<Date> staticDateStartAndDateEnd(int type) {
		List<Date> result = Lists.newArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		if (type == 1) {
			result.add(DateTimeUtil.getStartTimeOfDate(calendar.getTime()));
			result.add(DateTimeUtil.getEndTimeOfDate(calendar.getTime()));
		} else if (type == 7) {
			Calendar calendarTem = Calendar.getInstance();
			calendarTem.add(Calendar.DAY_OF_MONTH, -7);
			result.add(DateTimeUtil.getStartTimeOfDate(calendarTem.getTime()));
			result.add(DateTimeUtil.getEndTimeOfDate(calendar.getTime()));
		} else if (type == 30) {
			Calendar calendarTem = Calendar.getInstance();
			calendarTem.add(Calendar.DAY_OF_MONTH, -30);
			result.add(DateTimeUtil.getStartTimeOfDate(calendarTem.getTime()));
			result.add(DateTimeUtil.getEndTimeOfDate(calendar.getTime()));
		}
		return result;
	}
}

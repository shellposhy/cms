package cn.com.cms.system.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import cn.com.cms.system.model.RecordVisit;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 线型图以及柱状图实体VO
 * 
 * @author shishb
 * @version 1.0
 */
public class BarLineChartVo {
	private String dataLable; // 用于线型图中线条识别
	private List<List<Object>> xaxisOpt; // x轴可控显示类容
	private List<List<Object>> yaxisOpt; // y轴可控显示类容
	private List<List<Object>> data; // 数轴坐标

	public String getDataLable() {
		return dataLable;
	}

	public void setDataLable(String dataLable) {
		this.dataLable = dataLable;
	}

	public List<List<Object>> getXaxisOpt() {
		return xaxisOpt;
	}

	public void setXaxisOpt(List<List<Object>> xaxisOpt) {
		this.xaxisOpt = xaxisOpt;
	}

	public List<List<Object>> getYaxisOpt() {
		return yaxisOpt;
	}

	public void setYaxisOpt(List<List<Object>> yaxisOpt) {
		this.yaxisOpt = yaxisOpt;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	/**
	 * 线状数据处理
	 * 
	 * @param timePeroid
	 * @param recordVisits
	 * @return
	 */
	public static ReportVo<BarLineChartVo> changeValue2LineChart(int timePeroid, List<RecordVisit> recordVisits) {
		// 返回对象
		ReportVo<BarLineChartVo> result = new ReportVo<BarLineChartVo>();
		List<BarLineChartVo> dataList = Lists.newArrayList();

		// 直方图数据
		BarLineChartVo object = new BarLineChartVo();
		List<List<Object>> objectDataList = Lists.newArrayList();
		List<List<Object>> objectXaxisList = Lists.newArrayList();
		List<List<Object>> objectYaxisList = Lists.newArrayList();

		// 记录转换
		List<RecordVisit> recordVoList = sortDataAndFillLine(recordVisits);
		double avevage = 0;

		for (RecordVisit visit : recordVoList) {
			// 数轴坐标
			List<Object> data = new ArrayList<Object>();
			data.add(visit.getHour());
			data.add(visit.getVisits() / timePeroid);
			objectDataList.add(data);

			// x轴坐标
			List<Object> x_axis = new ArrayList<Object>();
			x_axis.add(visit.getHour());
			x_axis.add(String.valueOf(visit.getHour()).concat("时"));
			objectXaxisList.add(x_axis);
			// y轴坐标
			List<Object> y_axis = new ArrayList<Object>();
			y_axis.add(visit.getVisits() / timePeroid);
			y_axis.add(String.valueOf(visit.getVisits() / timePeroid).concat("次"));
			objectYaxisList.add(y_axis);
			// 总访问次数
			avevage = avevage + visit.getVisits();
		}
		object.setData(objectDataList);
		object.setXaxisOpt(objectXaxisList);
		object.setYaxisOpt(objectYaxisList);
		long avevageFinal = Math.round(avevage / 24 / timePeroid);

		// 图表数据
		if (timePeroid == 1) {
			result.setTitle("昨日");
		} else {
			StringBuilder title = new StringBuilder();
			title.append("最近").append(String.valueOf(timePeroid)).append("日");
			result.setTitle(title.toString());
		}
		RecordVisit maxVisit = recordVisits.get(0);
		RecordVisit minVisit = new RecordVisit();
		if (recordVisits.size() == 24) {
			minVisit = recordVisits.get(recordVisits.size() - 1);
		} else {
			for (RecordVisit recordVisit : recordVoList) {
				if (recordVisit.getVisits() == 0) {
					minVisit = recordVisit;
					break;
				}
			}
			if (null == minVisit.getVisits()) {
				minVisit = recordVisits.get(recordVisits.size() - 1);
			}
		}
		StringBuilder info = new StringBuilder();
		if (maxVisit.getVisits() / timePeroid == minVisit.getVisits() / timePeroid) {
			info.append("此时间段内，最高浏览量与最低浏览量均为").append(maxVisit.getVisits() / timePeroid).append("次。");
		} else {
			info.append("平均").append(avevageFinal).append("次，最高在").append(maxVisit.getHour()).append("时，浏览量达到")
					.append(maxVisit.getVisits() / timePeroid).append("次。最低在").append(minVisit.getHour())
					.append("时，浏览量为").append(minVisit.getVisits() / timePeroid).append("次。");
		}

		// 返回结果
		result.setInfo(info.toString());
		dataList.add(object);
		result.setData(dataList);
		return result;
	}

	/**
	 * 直方图数据处理
	 * 
	 * @param timePeroid
	 * @param recordVisits
	 * @return
	 */
	public static ReportVo<BarLineChartVo> changeValue2BarChart(int timePeroid, List<RecordVisit> recordVisits) {
		// 返回对象
		ReportVo<BarLineChartVo> result = new ReportVo<BarLineChartVo>();
		List<BarLineChartVo> dataList = Lists.newArrayList();

		// 直方图数据
		BarLineChartVo object = new BarLineChartVo();
		List<List<Object>> objectDataList = Lists.newArrayList();
		List<List<Object>> objectXaxisList = Lists.newArrayList();
		List<List<Object>> objectYaxisList = Lists.newArrayList();

		// 记录转换
		List<RecordVisit> recordVoList = sortDataAndFillBar(timePeroid, recordVisits);
		double avevage = 0;

		for (RecordVisit visit : recordVoList) {
			// 数轴坐标
			List<Object> data = new ArrayList<Object>();
			data.add(DateTimeUtil.toLongTime(visit.getYear(), visit.getMonth(), visit.getDay()));
			data.add(visit.getVisits());
			objectDataList.add(data);

			// x轴坐标
			List<Object> x_axis = new ArrayList<Object>();
			x_axis.add(DateTimeUtil.toLongTime(visit.getYear(), visit.getMonth(), visit.getDay()));
			x_axis.add(String.valueOf(visit.getMonth()).concat("/").concat(String.valueOf(visit.getDay())));
			objectXaxisList.add(x_axis);
			// y轴坐标
			List<Object> y_axis = new ArrayList<Object>();
			y_axis.add(visit.getVisits());
			y_axis.add(String.valueOf(visit.getVisits()).concat("次"));
			objectYaxisList.add(y_axis);
			// 总访问次数
			avevage = avevage + visit.getVisits();
		}
		object.setData(objectDataList);
		object.setXaxisOpt(objectXaxisList);
		object.setYaxisOpt(objectYaxisList);
		long avevageFinal = Math.round(avevage / timePeroid);

		// 图表数据
		String title = new String();
		if (timePeroid == 1) {
			result.setInfo("");
			title = "昨日";
		} else {
			RecordVisit maxVisit = recordVisits.get(0);
			RecordVisit minVisit = new RecordVisit();
			if (recordVisits.size() == timePeroid) {
				minVisit = recordVisits.get(recordVisits.size() - 1);
			} else {
				for (RecordVisit recordVisit : recordVoList) {
					if (recordVisit.getVisits() == 0) {
						recordVisit.setVisitTime(DateTimeUtil.toDate(recordVisit.getYear(), recordVisit.getMonth(),
								recordVisit.getDay()));
						minVisit = recordVisit;
						break;
					}
				}
				if (null == minVisit.getVisits()) {
					minVisit = recordVisits.get(recordVisits.size() - 1);
				}
			}
			StringBuilder info = new StringBuilder();
			if (maxVisit.getVisits().equals(minVisit.getVisits())) {
				info.append("此时间段内，最高浏览量与最低浏览量均为").append(maxVisit.getVisits()).append("次。");
			} else {
				info.append("平均").append(avevageFinal).append("次，最高在")
						.append(DateTimeUtil.formatDate(maxVisit.getVisitTime())).append("，浏览量达到")
						.append(maxVisit.getVisits()).append("次。最低在")
						.append(DateTimeUtil.formatDate(minVisit.getVisitTime())).append("，浏览量为")
						.append(minVisit.getVisits()).append("次。");
			}
			result.setInfo(info.toString());
			StringBuilder mainTitle = new StringBuilder();
			title = mainTitle.append("最近").append(String.valueOf(timePeroid)).append("日").toString();
		}
		// 返回结果
		result.setTitle(title);
		dataList.add(object);
		result.setData(dataList);
		return result;
	}

	/**
	 * 补全线性图并按24小时排序
	 * 
	 * @param dateList
	 * @param recordVisits
	 * @return
	 */
	private static List<RecordVisit> sortDataAndFillBar(int timePeroid, List<RecordVisit> recordVisits) {
		List<Date> dateList = new ArrayList<Date>();
		for (int i = 1; i <= timePeroid; i++) {
			Calendar calendarImp = Calendar.getInstance();
			calendarImp.add(Calendar.DAY_OF_MONTH, -i);
			Date temDate = calendarImp.getTime();
			dateList.add(temDate);
		}
		List<RecordVisit> recordVoList = Lists.newArrayList();
		Collections.reverse(dateList);
		for (Date date : dateList) {
			String datatime = DateTimeUtil.format(date, "yyyy-MM-dd");
			int flag = 0;
			for (RecordVisit recordVisit : recordVisits) {
				if (datatime.equals(DateTimeUtil.format(recordVisit.getVisitTime(), "yyyy-MM-dd"))) {
					recordVoList.add(recordVisit);
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				RecordVisit recordVisit = new RecordVisit();
				recordVisit.setYear(DateTimeUtil.getYear(date));
				recordVisit.setDay(DateTimeUtil.getDay(date));
				recordVisit.setMonth(DateTimeUtil.getMonth(date) + 1);
				recordVisit.setVisits(0);
				recordVoList.add(recordVisit);
			}
		}
		return recordVoList;
	}

	/**
	 * 补全线性图并按24小时排序
	 * 
	 * @param recordVisits
	 * @return
	 */
	private static List<RecordVisit> sortDataAndFillLine(List<RecordVisit> recordVisits) {
		List<RecordVisit> recordVoList = new ArrayList<RecordVisit>();
		for (int hour = 0; hour < 24; hour++) {
			int flag = 0;
			for (RecordVisit recordVisit : recordVisits) {
				if (recordVisit.getHour() == hour) {
					recordVoList.add(recordVisit);
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				RecordVisit recordVisit = new RecordVisit();
				recordVisit.setHour(hour);
				recordVisit.setVisits(0);
				recordVoList.add(recordVisit);
			}
		}
		return recordVoList;
	}
}

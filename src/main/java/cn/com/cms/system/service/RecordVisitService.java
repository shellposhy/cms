package cn.com.cms.system.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.system.dao.RecordVisitMapper;
import cn.com.cms.system.model.RecordVisit;

/**
 * 访问请求服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class RecordVisitService {
	@Resource
	private RecordVisitMapper recordVisitMapper;

	public void insert(RecordVisit record) {
		recordVisitMapper.insert(record);
	}

	public void batchInsert(List<RecordVisit> recordList) {
		recordVisitMapper.batchInsert(recordList);
	}

	/**
	 * 查询某个用户(或所有用户)在某个时间的访问量
	 * 
	 * @param userId
	 *            用户ID,null:查所有用户
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public int countByUserTime(Integer userId, Date startTime, Date endTime) {
		return recordVisitMapper.countByUserTime(userId, startTime, endTime);
	}

	/**
	 * 按时间段对某个(或所有)用户访问量排序(查询用户访问量最高的时间段)
	 * 
	 * @param userId
	 *            用户ID null:所有用户
	 * @param startTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间
	 * @param groupByYear
	 *            按年统计，每年返回一个结果，groupby的参数只能有一个true，否则会出错
	 * @param groupByMonth
	 *            按月统计，每月返回一个结果
	 * @param groupByDay
	 *            按日统计，每日返回一个结果，不同月份，相同日期，会分到不同组
	 * @param groupByHour
	 *            按小时统计，每小时返回一个结果，不同日期，相同小时，会分到相同组
	 * @return
	 */
	public List<RecordVisit> rankTimePeriodInUser(Integer userId, Date startTime, Date endTime, boolean groupByYear,
			boolean groupByMonth, boolean groupByDay, boolean groupByHour) {
		return recordVisitMapper.rankTimePeriodInUser(userId, startTime, endTime, groupByYear, groupByMonth, groupByDay,
				groupByHour);
	}

	/**
	 * 按时间段对某个(或所有)用户组访问量排序(查询用户组访问量最高的时间段)
	 * 
	 * @param groupId
	 *            用户组id，null:查所有组
	 * @param startTime
	 * @param endTime
	 * @param groupByYear
	 *            按年分组,groupBy的参数只可有一个true，如果全为false，就只按groupId分组
	 *            例：rankUserGroupInTime(null,start,end,false,false,false,false)
	 * @param groupByMonth
	 *            按月分组
	 * @param groupByDay
	 *            按日分组，不同月份，相同日期，会分到不同组
	 * @param groupByHour
	 *            按小时分组，不同日期，相同小时，会分到相同组
	 * @return
	 */
	public List<RecordVisit> rankTimePeriodInUserGroup(Integer groupId, Date startTime, Date endTime,
			boolean groupByYear, boolean groupByMonth, boolean groupByDay, boolean groupByHour) {
		return recordVisitMapper.rankTimePeriodInUserGroup(groupId, startTime, endTime, groupByYear, groupByMonth,
				groupByDay, groupByHour);
	}

	/**
	 * 查询某个用户组在某个时间的访问量
	 * 
	 * @param groupId
	 *            用户组
	 * @param year
	 *            查询年份,null:查所有年,下同
	 * @param month
	 * @param day
	 * @param hour
	 * @return
	 */
	public int countByUserGroupTime(int groupId, Date startTime, Date endTime) {
		return recordVisitMapper.countByUserGroupTime(groupId, startTime, endTime);
	}

	/**
	 * 查询用户访问量排行，按访问量把用户从高到低排序
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<RecordVisit> rankUserVisits(Date startTime, Date endTime) {
		return recordVisitMapper.rankUserVisits(startTime, endTime);
	}

	/**
	 * @param record
	 */
	public void update(RecordVisit record) {
		recordVisitMapper.update(record);
	}

}

package cn.com.cms.system.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.system.model.RecordVisit;

public interface RecordVisitMapper extends BaseDao<RecordVisit> {
	void batchInsert(List<RecordVisit> recordList);

	int countByUserTime(@Param("userId") Integer userId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	List<RecordVisit> rankTimePeriodInUser(@Param("userId") Integer userId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime, @Param("groupByYear") boolean groupByYear,
			@Param("groupByMonth") boolean groupByMonth, @Param("groupByDay") boolean groupByDay,
			@Param("groupByHour") boolean groupByHour);

	List<RecordVisit> rankTimePeriodInUserGroup(@Param("groupId") Integer groupId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime, @Param("groupByYear") boolean groupByYear,
			@Param("groupByMonth") boolean groupByMonth, @Param("groupByDay") boolean groupByDay,
			@Param("groupByHour") boolean groupByHour);

	int countByUserGroupTime(@Param("groupId") int groupId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	List<RecordVisit> rankUserVisits(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}

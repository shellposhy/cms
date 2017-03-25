package cn.com.cms.system.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.system.model.RecordDoc;

public interface RecordDocMapper extends BaseDao<RecordDoc> {
	List<RecordDoc> rankDocInUser(@Param("userId") Integer userId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime, @Param("firstResult") int firstResult, @Param("maxResult") int maxResult);

	List<RecordDoc> rankDocInUserGroup(@Param("groupId") Integer groupId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime, @Param("firstResult") int firstResult, @Param("maxResult") int maxResult);

	List<RecordDoc> rankDocInDb(@Param("baseId") Integer baseId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime, @Param("firstResult") int firstResult, @Param("maxResult") int maxResult);
}

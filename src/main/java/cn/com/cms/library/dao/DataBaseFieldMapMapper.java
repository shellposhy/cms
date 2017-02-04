package cn.com.cms.library.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.library.model.DataBaseFieldMap;

public interface DataBaseFieldMapMapper extends BaseDao<DataBaseFieldMap> {

	List<DataBaseFieldMap> findByDBIdAndIsDisplay(@Param("dbId") Integer dbId, @Param("isDisplay") boolean isDisplay);

	List<DataBaseFieldMap> findByDBId(Integer dbId);

	void batchDeleteByDBId(Integer dbId);

	int countByFieldId(Integer fieldId);

	void deleteByDbIdAndFieldId(@Param("dbId") Integer dbId, @Param("fieldId") Integer fieldId);
}
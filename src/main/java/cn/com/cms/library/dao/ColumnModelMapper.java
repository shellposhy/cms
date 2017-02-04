package cn.com.cms.library.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.ColumnModel;

/**
 * 数据库模板Map
 * 
 * @author shishb
 * @version 1.0
 */
public interface ColumnModelMapper extends BaseDao<ColumnModel> {

	List<ColumnModel> findByName(@Param("name") String name, @Param("type") ELibraryType type,
			@Param("firstResult") int firstResult, @Param("maxResult") int maxResult);

	List<ColumnModel> findAllByType(@Param("type") ELibraryType type);

	int countByName(@Param("name") String name, @Param("type") ELibraryType type);

	void batchDelete(Integer[] ids);

}
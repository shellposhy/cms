package cn.com.cms.library.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.DataSort;

/**
 * 数据分类Dao
 * 
 * @author shishb
 * @version 1.0
 */
public interface DataSortMapper {
	List<DataSort> findAll();

	List<DataSort> findByName(String name);

	DataSort findByCode(@Param("code") String code, @Param("dbId") Integer dbId);

	DataSort findByDbAndCode(@Param("code") String code, @Param("dbId") Integer dbId);

	DataSort findByNameAndParentId(@Param("name") String name, @Param("parentId") Integer parentId);

	List<DataSort> findByDBId(Integer dbId);

	List<DataSort> findRootByDBId(Integer dbId);

	List<DataSort> findByDbIdAndType(@Param("dbId") Integer dbId, @Param("type") Integer type);

	List<DataSort> findByNameDbIdAndType(@Param("name") String name, @Param("dbId") Integer dbId,
			@Param("type") Integer type);

	List<DataSort> findNoCommonSort();

	DataSort findByCodeAndDB(@Param("code") String code, @Param("dbId") Integer dbId);

	List<DataSort> findByNameAndDBId(@Param("name") String name, @Param("dbId") Integer dbId);

	List<DataSort> findByParentId(Integer parentId);

	List<DataSort> findByLibType(@Param("type") ELibraryType type);

	DataSort find(Integer id);

	DataSort findByPathCode(@Param("pathCode") String pathCode);

	void insert(DataSort dataSort);

	void update(DataSort DataSort);

	void delete(Integer id);
}

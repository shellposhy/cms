package cn.com.cms.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.library.constant.EDataFieldType;

/**
 * 数据库操作服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface DataFieldMapper extends BaseDao<DataField> {

	/**
	 * 根据名称查询字段
	 * 
	 * @param name
	 * @return
	 */
	List<DataField> findByName(String name);

	/**
	 * 根据编码查询字段
	 * 
	 * @param code
	 * @return
	 */
	List<DataField> findByCode(String code);

	/**
	 * 根据数据库查询所有字段
	 * 
	 * @param dbId
	 * @return
	 */
	List<DataField> findFieldsByDBId(Integer dbId);

	/**
	 * 获取必须字段
	 * 
	 * @param required
	 * @return
	 */
	List<DataField> findByRequired(boolean required);

	/**
	 * 查询多个数据库均含有的排序字段字段
	 * 
	 * @param forOrder
	 * @param baseIds
	 * @param size
	 * @return
	 */
	List<DataField> findFieldsInEveryBase(@Param("forOrder") boolean forOrder, @Param("baseIds") List<Integer> baseIds,
			@Param("size") int size);

	/**
	 * 获得数据的字段
	 * 
	 * @param forOrder
	 * @param baseId
	 * @return
	 */
	List<DataField> findFieldsInBase(@Param("forOrder") boolean forOrder, @Param("baseId") Integer baseId);

	/**
	 * 查询多个数据库均含有的查询字段
	 * 
	 * @param accessType
	 * @param baseIds
	 * @return
	 */
	List<DataField> findFieldsInBasesByAccess(@Param("accessType") int accessType,
			@Param("baseIds") List<Integer> baseIds, @Param("size") int size);

	/**
	 * 查询数据库的操作类型字段
	 * 
	 * @param accessType
	 * @param baseId
	 * @return
	 */
	List<DataField> findFieldsInBaseByAccess(@Param("accessType") int accessType, @Param("baseId") Integer baseId);

	/**
	 * 查询多数据库的操作类型字段
	 * 
	 * @param accessType
	 * @param baseId
	 * @return
	 */
	List<DataField> findFieldsInBasesByAccesses(@Param("accessType") int accessType,
			@Param("baseIds") List<Integer> baseIds);

	/**
	 * 查询需要显示和必填的字段
	 * 
	 * @param forDisplay
	 * @param required
	 * @return
	 */
	List<DataField> findByIsDisplayAndRequire(@Param("forDisplay") boolean forDisplay,
			@Param("required") boolean required);

	/**
	 * 根据类别查询
	 * 
	 * @param type
	 * @return
	 */
	List<DataField> findByType(@Param("type") EDataFieldType type);

	/**
	 * 根据数据字段类型和是否显示查询
	 * 
	 * @param type
	 * @param forDisplay
	 * @return
	 */
	List<DataField> findByTypeAndForDisplay(@Param("type") EDataFieldType type,
			@Param("forDisplay") boolean forDisplay);

	/**
	 * 根据code查询单个字段
	 * 
	 * @param code
	 * @return
	 */
	DataField getByCode(String code);

	/**
	 * 查询多个数据库均含有的字段
	 * 
	 * @param baseIds
	 * @return
	 */
	List<DataField> findFieldsByDBIds(@Param("baseIds") List<Integer> baseIds);

	/**
	 * 查询库显示的字段
	 * 
	 * @param baseId
	 * @return
	 */
	List<DataField> findDisplayFieldsByDBId(@Param("baseId") Integer baseId);

	/**
	 * 根据模板编号查询字段
	 * 
	 * @param modelId
	 * @return
	 */
	List<DataField> findFieldsByModelId(@Param("modelId") Integer modelId);

	/**
	 * 根据模板编号查询显示字段
	 * 
	 * @param modelId
	 * @return
	 */
	List<DataField> findDisplayFieldsByModelId(@Param("modelId") Integer modelId);

	/**
	 * 分页查询
	 * 
	 * @param qs
	 * @param first
	 * @param size
	 * @return
	 */
	List<DataField> findByPage(@Param("qs") String qs, @Param("first") Integer first, @Param("size") Integer size);

	/**
	 * 统计
	 * 
	 * @param qs
	 * @return
	 */
	int count(@Param("qs") String qs);
}
package cn.com.cms.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.system.contant.ESysParamType;
import cn.com.cms.system.model.SysParameter;

/**
 * 系统初始化参数数据库持久层
 * 
 * @author shishb
 * @version 1.0
 */
public interface SysParameterMapper {

	/**
	 * 分页查询
	 * 
	 * @param word
	 * @param first
	 * @param size
	 * @return
	 */
	List<SysParameter> findByWord(@Param("word") String word, @Param("first") Integer first,
			@Param("size") Integer size);

	/**
	 * 统计
	 * 
	 * @param word
	 * @return
	 */
	int count(@Param("word") String word);

	/**
	 * 新增
	 * 
	 * @param sysParameter
	 * @return
	 * 
	 */
	void insert(SysParameter sysParameter);

	/**
	 * 按主键查询
	 * 
	 * @param id
	 * @return
	 */
	SysParameter find(int id);

	/**
	 * 全部查询
	 * 
	 * @return
	 */
	List<SysParameter> findAll();

	/**
	 * 根据类别查询
	 * 
	 * @param paramType
	 * @return
	 */
	List<SysParameter> findByParamType(ESysParamType paramType);

	/**
	 * 更新
	 * 
	 * @param sysParameter
	 * @return
	 */
	void update(SysParameter sysParameter);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	void delete(int id);
}
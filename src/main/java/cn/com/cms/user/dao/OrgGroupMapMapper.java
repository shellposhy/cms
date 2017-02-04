package cn.com.cms.user.dao;

import java.util.List;

import cn.com.cms.user.model.OrgGroupMap;

/**
 * 组织用户组服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface OrgGroupMapMapper {
	/**
	 * 根据用户组删除
	 * 
	 * @param groupId
	 * @return
	 */
	void deleteByGroupId(Integer groupId);

	/**
	 * 根据机构编号获取用户组
	 * 
	 * @param orgId
	 * @return
	 */
	List<Integer> findGroupIdsByOrgId(Integer orgId);

	/**
	 * 批量新增
	 * 
	 * @param list
	 * @return
	 */
	void batchInsert(List<OrgGroupMap> list);

	/**
	 * 新增数据
	 * 
	 * @param orgGroupMap
	 * @return
	 */
	void insert(OrgGroupMap orgGroupMap);

	/**
	 * 根据机构编号删除
	 * 
	 * @param orgId
	 * @return
	 */
	void deleteByOrgId(Integer orgId);
}

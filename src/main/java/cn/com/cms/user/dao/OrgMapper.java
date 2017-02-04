package cn.com.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.user.model.Org;

/**
 * 组织机构服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface OrgMapper extends BaseDao<Org> {
	/**
	 * 根据父节点查询
	 * 
	 * @param parentId
	 * @return
	 */
	List<Org> findByParentId(int parentId);

	/**
	 * 没有用户的机构名称
	 * 
	 * @return
	 */
	List<Org> findAllNoUserOrgs();

	/**
	 * 根据父节点和编码查询
	 * 
	 * @param parentId
	 * @param code
	 * @return
	 */
	Org findByCodeAndParentId(@Param("parentId") Integer parentId, @Param("code") String code);
}

package cn.com.cms.page.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.page.model.WebUser;

/**
 * 客户Dao层
 * 
 * @author shishb
 * @version 1.0
 */
public interface WebUserMapper extends BaseDao<WebUser> {

	/**
	 * 根据账户和密码查询
	 * 
	 * @param name
	 * @param pass
	 * @return
	 */
	WebUser findByNameAndPass(@Param("name") String name, @Param("pass") String pass);
}

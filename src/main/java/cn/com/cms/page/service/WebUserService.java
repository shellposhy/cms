package cn.com.cms.page.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.page.dao.WebUserMapper;
import cn.com.cms.page.model.WebUser;

/**
 * 客户服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class WebUserService {
	@Resource
	private WebUserMapper webUserMapper;

	/**
	 * 保存或更新客户信息
	 * 
	 * @param webUser
	 * @return
	 */
	public void saveOrUpdate(WebUser webUser) {
		if (null != webUser.getId() && webUser.getId().intValue() > 0) {
			webUserMapper.update(webUser);
		} else {
			webUserMapper.insert(webUser);
		}
	}

	/**
	 * 根据用户名和密码查询
	 * 
	 * @param name
	 * @param pass
	 * @return
	 */
	public WebUser findByNameAndPass(String name, String pass) {
		return webUserMapper.findByNameAndPass(name, pass);
	}

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public WebUser findByUserId(Integer id) {
		return webUserMapper.find(id);
	}
}

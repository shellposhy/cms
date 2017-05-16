package cn.com.cms.framework.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.user.model.User;

@Service
public class UserSecurityService {

	/**
	 * 获得系统当前用户
	 * 
	 * @param request
	 * @return
	 */
	public User currentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(SystemConstant.CURRENT_USER);
	}
}

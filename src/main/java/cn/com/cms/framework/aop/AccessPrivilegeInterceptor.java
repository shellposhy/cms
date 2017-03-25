package cn.com.cms.framework.aop;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cms.base.AppConfig;
import cn.com.cms.framework.base.BaseLog;
import cn.com.cms.framework.security.UserSecurityService;
import cn.com.cms.system.contant.EInterceptStatus;
import cn.com.cms.system.service.LogService;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserActionService;
import cn.com.people.data.util.SystemUtil;

/**
 * 权限拦截器
 * 
 * @author shishb
 * @version 1.0
 */
@Component
public class AccessPrivilegeInterceptor implements HandlerInterceptor {
	private static Logger log = Logger.getLogger(AccessPrivilegeInterceptor.class.getName());

	@Resource
	private UserActionService actionService;
	@Resource
	private UserSecurityService securityService;
	@Resource
	private LogService logService;
	@Resource
	private AppConfig appConfig;

	/**
	 * 前置处理动作
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @return
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		log.debug("==========uri=>" + uri + "====");
		User currentUser = securityService.currentUser(request);
		// is has the access privilege
		boolean type = actionService.isHaveAccessPrivilege(request, currentUser);
		// is record the uri logs
		if (appConfig.getAppLogSwitch() == 1) {
			excuteLog(request, currentUser, type);
		}
		if (!type) {
			response.sendRedirect("/static/default/403.html");
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 后置处理动作
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @return
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 拦截器拦截完成后处理的动作
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 日志服务处理类
	 * 
	 * @param request
	 * @param user
	 * @param accessType
	 * @return
	 */
	public void excuteLog(HttpServletRequest request, User user, boolean accessType) {
		BaseLog log = new BaseLog();
		String qs = request.getQueryString();
		log.setUri(request.getRequestURI());
		if (qs == null || "null".equals(qs)) {
			log.setUrl(request.getRequestURI());
		} else {
			log.setUrl(request.getRequestURI() + "?" + qs);
		}
		String id = request.getParameter("searchIdStr");
		if (id == null) {
			id = request.getParameter("id");
		}
		if (id != null && !id.isEmpty() && !id.contains(",") && !id.equals("new")) {
			log.setId(Integer.valueOf(id));
		}
		log.setTargetName(request.getParameter("name"));
		log.setTime(new Date().getTime());
		log.setIp(SystemUtil.parseClientIp(request));
		log.setMethod(request.getMethod());
		if (null != user) {
			log.setUserName(user.getRealName());
			log.setUserId(null != user.getId() ? user.getId() : null);
		}
		if (accessType) {
			log.setInterceptStatus(EInterceptStatus.Pass);
		} else {
			if (null != user) {
				log.setInterceptStatus(EInterceptStatus.NoPermission);
			} else {
				log.setInterceptStatus(EInterceptStatus.NoSession);
			}
		}
		saveLog(log);
	}

	/**
	 * 保存日志处理动作
	 * 
	 * @param log
	 * @return
	 */
	private void saveLog(BaseLog log) {
		if (log.getUri().contains("/admin")) {
			logService.logAdmin(log);
		} else {
			logService.logUser(log);
		}
	}
}

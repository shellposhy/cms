package cn.com.cms.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import cn.com.cms.common.SystemConstant;
import cn.com.cms.user.model.User;

/**
 * Servlet Filter implementation class UrlFilter
 */
@Component
public class UrlFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public UrlFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		httpServletRequest.setAttribute("appName", "数据信息管理系统");
		String uri = httpServletRequest.getRequestURI();
		if (uri.contains("/admin/security/check") || uri.contains("/page") || uri.contains("/static")
				|| uri.contains("/default") || "/".equals(uri) || uri.contains("/pic") || uri.contains("/tmp")
				|| uri.contains(".html")) {
			chain.doFilter(request, response);
		} else {
			User user = (User) httpServletRequest.getSession().getAttribute(SystemConstant.CURRENT_USER);
			if (null != user) {
				chain.doFilter(request, response);
			} else {
				httpServletRequest.getRequestDispatcher("/admin/login").forward(request, response);
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}

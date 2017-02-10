package cn.com.cms.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.cms.common.SystemConstant;
import cn.com.cms.user.model.User;
import cn.com.cms.util.MessageResources;

/**
 * Servlet Filter implementation class UrlFilter
 */
public class UrlFilter implements Filter {
	private static Logger log = Logger.getLogger(UrlFilter.class.getName());

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
		HttpServletRequest hRequest = (HttpServletRequest) request;
		hRequest.setAttribute("appName", MessageResources.getValue("app.name"));
		String uri = hRequest.getRequestURI();
		if (uri.contains("/admin/security/check") || uri.contains("/page") || uri.contains("/static")
				|| uri.contains("/default") || "/".equals(uri) || uri.contains("/pic") || uri.contains("/tmp")
				|| uri.contains(".html") || uri.contains("/admin/logout") || uri.contains("/admin/login")) {
			chain.doFilter(request, response);
		} else {
			User user = (User) hRequest.getSession().getAttribute(SystemConstant.CURRENT_USER);
			if (null != user) {
				chain.doFilter(request, response);
			} else {
				log.info("=====uri====" + uri);
				hRequest.getRequestDispatcher("/admin/login?from=" + uri).forward(request, response);
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}

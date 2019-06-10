package cn.com.cms.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.util.MessageResources;

/**
 * 拦截用户初次请求，检查用户请求，以及非法请求等
 * 
 * @author shishb
 * @version 1.0
 */
public class UrlFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(UrlFilter.class.getName());

	// 过滤器属性
	FilterConfig filterConfig;

	// 静态资源
	private static final String[] staticResourceType = { ".css", ".js", ".ico", ".jpg", ".jpeg", ".gif", ".html",
			".png", ".doc", ".pdf", ".docx", ".ppt", ".pptx", ".xls", ".xlsx" };
	private static final String[] staticResourcePath = { "/page", "/static", "/default", "/pic", "/tmp", "/doc" };
	private static final String[] excludePath = { "/admin/security/check", "/admin/logout", "/admin/login",
			"/admin/task/progress" };

	// 过滤器
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		servletRequest.setAttribute("appName", MessageResources.getValue("app.name"));
		String uri = servletRequest.getRequestURI();
		LOG.debug("============uri=" + uri);
		if ("/".equals(uri) || matchExcludePath(uri) || matchStaticPath(uri) || matchStaticResource(uri)) {
			chain.doFilter(request, response);
		} else {
			if (null != servletRequest.getSession().getAttribute(SystemConstant.CURRENT_USER)) {
				chain.doFilter(request, response);
			} else {
				servletRequest.getRequestDispatcher("/admin/login?from=" + uri).forward(request, response);
			}
		}
	}

	// 匹配静态资源
	private boolean matchStaticResource(String uri) {
		boolean result = false;
		for (String str : staticResourceType) {
			if (uri.contains(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	// 匹配静态资源路径
	private boolean matchStaticPath(String uri) {
		boolean result = false;
		for (String str : staticResourcePath) {
			if (uri.contains(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	// 匹配排除过滤路径
	private boolean matchExcludePath(String uri) {
		boolean result = false;
		for (String str : excludePath) {
			if (uri.contains(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	// 初始化
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	// 销毁
	public void destroy() {
		this.filterConfig = null;
	}

}

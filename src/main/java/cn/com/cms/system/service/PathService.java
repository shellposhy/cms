package cn.com.cms.system.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.config.AppConfig;

/**
 * 页面发布路径服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class PathService {
	@Resource
	private AppConfig appConfig;

	private static String rootPhysicalParth;
	private static String staticPhysicalParth;
	private static String subpagePhysicalParth;
	private static String templatePhysicalParth;
	private static String rootUrl;
	private static String staticUrl;
	private static String subpageUrl;
	private static String classesPath = PathService.class.getResource("/").getPath();

	/**
	 * 获取根路径
	 */
	public String getRootPhysicalParth() {
		if (null == rootPhysicalParth) {
			rootPhysicalParth = classesPath.substring(0, classesPath.indexOf("WEB-INF"));
		}
		return rootPhysicalParth;
	}

	/**
	 * 获取静态文件路径
	 */
	public String getStaticPhysicalParth() {
		if (null == staticPhysicalParth) {
			staticPhysicalParth = classesPath.substring(0, classesPath.indexOf("WEB-INF")) + "static";
		}
		return staticPhysicalParth;
	}

	/**
	 * 获取二级页面路径
	 */
	public String getSubpagePhysicalParth() {
		if (null == subpagePhysicalParth) {
			subpagePhysicalParth = classesPath.substring(0, classesPath.indexOf("WEB-INF")) + "subpage";
		}
		return subpagePhysicalParth;
	}

	/**
	 * 获取真实物理路径
	 */
	public String getTemplatePhysicalParth() {
		if (null == templatePhysicalParth) {
			templatePhysicalParth = classesPath.substring(0, classesPath.indexOf("classes"))
					+ appConfig.getTemplateHome();
		}
		return templatePhysicalParth;
	}

	/**
	 * 获取根路径
	 */
	public String getRootUrl() {
		if (null == rootUrl) {
			rootUrl = appConfig.getAppPath();
		}
		return rootUrl;
	}

	/**
	 * 获取静态url
	 */
	public String getStaticUrl() {
		if (null == staticUrl) {
			staticUrl = appConfig.getAppPath() + "/static";
		}
		return staticUrl;
	}

	/**
	 * 获取静态二级页面url
	 */
	public String getSubpageUrl() {
		if (null == subpageUrl) {
			subpageUrl = appConfig.getAppPath() + "/subpage";
		}
		return subpageUrl;
	}
}

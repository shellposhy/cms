<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="cn.com.cms.base.data.FieldCodes"%>
<%@page import="cn.com.cms.base.config.AppConfig"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%
	String queryString = request.getQueryString();
	String[] queryStrings = queryString.split("&");
	String baseId = queryStrings[0];
	String uuid = queryStrings[1];
	String path = "pic";
	String imgStr = "";
	ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
	AppConfig appConfig = (AppConfig) ac.getBean("appConfig");
	String realpath = appConfig.getAppPathHome() + "/" + path+ "/upic/" + baseId.substring(baseId.lastIndexOf("=") + 1);
	List<File> files = getFiles(realpath, new ArrayList());
	String rootStr = appConfig.getAppPathHome() + "/pic/";
	int strLength = rootStr.length();
	for (File file : files) {
		imgStr += file.getPath().substring(strLength)+ "ue_separate_ue";
	}
	if (imgStr != "") {
		imgStr = imgStr.substring(0, imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
	}
	out.print(imgStr);
%>
<%!public List getFiles(String realpath, List files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files);
				} else {
					if (!getFileType(file.getName()).equals("")) {
						files.add(file);
					}
				}
			}
		}
		return files;
	}

	public String getRealPath(HttpServletRequest request, String path) {
		ServletContext application = request.getSession().getServletContext();
		String str = application.getRealPath(request.getServletPath());
		return new File(str).getParent();
	}

	public String getFileType(String fileName) {
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while (type.hasNext()) {
			String t = type.next();
			if (fileName.endsWith(t)) {
				return t;
			}
		}
		return "";
	}%>
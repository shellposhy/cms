<%@page import="cn.com.cms.system.service.ImagePathService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="cn.com.cms.base.data.FieldCodes"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="cn.com.cms.util.Uploader"%>
<%
	ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
	ImagePathService imagePathService = (ImagePathService) ac.getBean("imagePathService");
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String baseid = request.getParameter("baseId");
	String uuid = request.getParameter("uuid");
	Uploader uploader = new Uploader(request);
	uploader.setSavePath(imagePathService.getTempPath() + "upic1/" + uuid);
	String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	uploader.setAllowFiles(fileType);
	uploader.setMaxSize(10000);
	uploader.upload();
	response.getWriter().print("{'original':'" + uploader.getOriginalName() + "','url':'"+ "tmp/upic1/" + uuid + "/"+ uploader.getFileName() + "','title':'" + uploader.getTitle()+ "','state':'" + uploader.getState() + "'}");
%>

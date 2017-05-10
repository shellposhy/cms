<%@page import="java.util.Date"%>
<%@page import="cn.com.people.data.util.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="cn.com.cms.util.Uploader"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String queryString = request.getQueryString();
	String[] queryStrings = queryString.split("&");
	String tmpBaseId = queryStrings[0];
	String tmpUuid = queryStrings[1];
	String tmpTime = queryStrings[2];
	String baseId = tmpBaseId.substring(tmpBaseId.lastIndexOf("=") + 1);
	String uuid = tmpUuid.substring(tmpUuid.lastIndexOf("=") + 1);
	String time = "";
	if (tmpTime != null && !"".equals(tmpTime) && tmpTime.length() > 11) {
		time = tmpTime.substring(tmpTime.lastIndexOf("=") + 1);
	} else {
		time = DateTimeUtil.format(new Date(), "yyyyMMdd");
	}
	Uploader up = new Uploader(request);
	up.setSavePath("doc/" + time.substring(0, 4) + "/"+ Integer.parseInt(time.substring(4, 6)) + "/" + baseId+ "/" + uuid); 
	String[] fileType = { ".rar", ".doc", ".docx", ".zip", ".pdf",".txt", ".ppt", ".pptx",".mp4",".xls", ".xlsx"};
	up.setAllowFiles(fileType);
	up.setMaxSize(10000);
	up.upload();
	response.getWriter().print("{'url':'" + up.getUrl() + "','fileType':'" + up.getType()+ "','state':'" + up.getState() + "','original':'"+ up.getOriginalName() + "'}");
%>

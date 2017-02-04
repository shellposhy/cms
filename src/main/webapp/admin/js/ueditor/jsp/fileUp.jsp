<%@page import="java.util.Date"%>
<%@page import="cn.com.people.data.util.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cn.com.people.data.pds.util.Uploader"%>


<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String queryString = request.getQueryString();
	String[] queryStrings = queryString.split("&");
	//数据库id
	String tmpBaseId = queryStrings[0];
	//uuid
	String tmpUuid = queryStrings[1];
	//创建时间
	String tmpTime = queryStrings[2];
	String baseId = tmpBaseId.substring(tmpBaseId.lastIndexOf("=") + 1);
	String uuid = tmpUuid.substring(tmpUuid.lastIndexOf("=") + 1);
	String time = "";
	//判断是否新建或修改
	if (tmpTime != null && !"".equals(tmpTime) && tmpTime.length() > 11) {
		time = tmpTime.substring(tmpTime.lastIndexOf("=") + 1);
	} else {
		time = DateTimeUtil.format(new Date(), "yyyyMMdd");
	}
	Uploader up = new Uploader(request);
	//附件上传地址
	up.setSavePath("doc/" + time.substring(0, 4) + "/"
			+ Integer.parseInt(time.substring(4, 6)) + "/" + baseId
			+ "/" + uuid); //保存路径
	String[] fileType = { ".rar", ".doc", ".docx", ".zip", ".pdf",
			".txt", ".swf", ".wmv" }; //允许的文件类型
	up.setAllowFiles(fileType);
	up.setMaxSize(10000); //允许的文件最大尺寸，单位KB
	up.upload();
	response.getWriter().print(
			"{'url':'" + up.getUrl() + "','fileType':'" + up.getType()
					+ "','state':'" + up.getState() + "','original':'"
					+ up.getOriginalName() + "'}");
%>

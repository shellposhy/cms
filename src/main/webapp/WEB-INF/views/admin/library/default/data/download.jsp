<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>附件下载</title></head>
<body>
	<%
		String fileName = (String) request.getAttribute("fileName");
		String filePath = (String) request.getAttribute("filePath");
		File fileLoad = new File(filePath);
		if (!fileLoad.exists()) {
			response.sendError(404, "File not found!");
			return;
		} else {
			ServletOutputStream o = response.getOutputStream();
			byte b[] = new byte[1024];
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.setContentType("application/x-tar");
			long fileLength = fileLoad.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length);
			FileInputStream in = new FileInputStream(fileLoad);
			int n = 0;
			while ((n = in.read(b)) != -1) {
				o.write(b, 0, n);
			}
			out.clear();
			out = pageContext.pushBody();
		}
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作出错</title>
<script type="text/javascript">
	function showDetail() {
		msg = document.getElementById('msg_detail');
		btn = document.getElementById('btn_detail');
		if (msg.style.display && msg.style.display == 'none') {
			msg.style.display = '';
			btn.innerHTML = '关闭详细信息';
		} else {
			msg.style.display = 'none';
			btn.innerHTML = '显示详细信息';
		}
	}
</script>
<style>
body {
	padding: 0;
	margin: 0;
	font: normal 12px/180% "宋体";
	color: #000;
	text-align: center;
	background: #bde7f7;
}

h1, h2, h3, h4, h5, h6, hr, p, blockquote, dl, dt, dd, ul, ol, li, pre,
	form, button, input, textarea, th, td {
	margin: 0;
	padding: 0;
}

div {
	margin: 0 auto;
	text-align: left;
	font: normal 12px/180% "宋体";
}

a {
	color: #000;
}

a:link, a:visited {
	text-decoration: none
}

a:hover {
	text-decoration: underline
}

img {
	border: none
}

.s1 {
	background: url("/static/default/css/images/s1.jpg");
	width: 423px;
	height: 221px;
	padding: 100px 40px 0 205px;
	margin-top: 75px;
}

.s1 p {
	font-size: 28px;
	font-family: \9ED1\4F53;
	font-weight: normal;
	color: #4c5053;
	line-height: 45px;
}

.s1 p i {
	font-family: Arial, Helvetica, sans-serif;
	font-weight: normal;
	font-style: normal;
	font-size: 22px;
}

.center {
	text-align: center;
	display: block;
	margin-bottom: 20px;
}
</style>
</head>
<%
	StringBuffer exception_msg = new StringBuffer();
	Exception e = (Exception) request.getAttribute("exception");
	java.io.StringWriter sw = new java.io.StringWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(sw);
	e.printStackTrace(pw);
	exception_msg.append(sw.toString());
%>
<body>
	<div class="s1">
		<span class="center">
			<a href="/" target="_blank"><img src="${appPath }/static/default/css/images/logo.png" width="63" height="60" /></a>
		</span>
		<p>
			抱歉，操作出错啦……<br /> <i>Oops! Operation error. </i>
		</p>
		<a href="javascript:history.back();">返回</a>
		<a id="btn_detail" href="javascript:showDetail();">显示详细信息</a>
	</div>
	<div id="msg_detail" style="display: none; height: 400px; width: 650px; overflow: scroll">
		<%=exception_msg.toString().replace("\r\n", "<br>")%>
	</div>
</body>
</html>
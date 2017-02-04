<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<div id="content">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" id="info_page_src" value="51">
				<li><a href="${appPath }/admin">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">文章细览</a></li>
			</ul>
		</div>
		<div class="container-fliud">
			<div class="row-fliud offset1">
				<div class="span10 well aticl_pve pricehover img_center">
					<div class="page-header center">
						<h2 class="">${data.title}</h2>
						<small>${data.authors} ${data.docTime}</small>
					</div>
					${data.content}
				</div>
			</div>
		</div>
	</div>
	</div>
	<div>
</body>
</html>
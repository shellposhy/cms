<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${appName}</title>
<link rel="stylesheet" href="${appPath}/admin/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<link id="bs-css" href="${appPath}/admin/css/bootstrap-cerulean.css" rel="stylesheet" />
<link href="${appPath}/admin/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${appPath}/admin/css/charisma-app.css" rel="stylesheet" />
<link href="${appPath}/admin/css/jquery-ui-1.9.0.custom.css" rel="stylesheet" />
<link href='${appPath}/admin/css/fullcalendar.css' rel='stylesheet' />
<link href='${appPath}/admin/css/fullcalendar.print.css' rel='stylesheet' media='print' />
<link href='${appPath}/admin/css/chosen.css' rel='stylesheet' />
<link href='${appPath}/admin/css/jquery.multiselect.css' rel='stylesheet' />
<link href='${appPath}/admin/css/uniform.default.css' rel='stylesheet' />
<link href='${appPath}/admin/css/colorbox.css' rel='stylesheet' />
<link href='${appPath}/admin/css/jquery.cleditor.css' rel='stylesheet' />
<link href='${appPath}/admin/css/jquery.noty.css' rel='stylesheet' />
<link href='${appPath}/admin/css/noty_theme_default.css' rel='stylesheet' />
<link href='${appPath}/admin/css/elfinder.min.css' rel='stylesheet' />
<link href='${appPath}/admin/css/elfinder.theme.css' rel='stylesheet' />
<link href='${appPath}/admin/css/jquery.iphone.toggle.css' rel='stylesheet' />
<link href='${appPath}/admin/css/opa-icons.css' rel='stylesheet' />
<link href='${appPath}/admin/css/uploadify.css' rel='stylesheet' />
<link href='${appPath}/admin/css/treetable/jquery.treetable.css' rel='stylesheet' />
<link href='${appPath}/admin/css/treetable/jquery.treetable.theme.default.css' rel='stylesheet' />
<link rel="stylesheet" href="${appPath}/admin/css/base.css" type="text/css" />
<script type="text/javascript" src="${appPath}/admin/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${appPath}/admin/js/javascript/cmstree.js"></script>
<script type="text/javascript" src="${appPath}/admin/js/javascript/commen.js"></script>
<script type="text/javascript" src="${appPath}/admin/js/javascript/chart.js"></script>
<script type="text/javascript">
	var appPath = "${appPath}"
</script>
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script type="text/javascript" src="${appPath}/admin/js/javascript/html5.js"></script>
	<![endif]-->
<sitemesh:head />
</head>
<body>
	<!--content-->
	<div class="content_wrap container-fluid">
		<div class="row-fluid">
			<%@include file="left.jsp"%>
			<div class="content_main">
				<sitemesh:body />
			</div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
	<!--/content-->
	<!-- jQuery-->
	<script src="${appPath}/admin/js/jquery-ui-1.9.0.custom.min.js"></script>
	<script src="${appPath}/admin/js/jquery.cookie.js"></script>
	<!-- bootstrap -->
	<script src="${appPath}/admin/js/bootstrap-transition.js"></script>
	<script src="${appPath}/admin/js/bootstrap-alert.js"></script>
	<script src="${appPath}/admin/js/bootstrap-modal.js"></script>
	<script src="${appPath}/admin/js/bootstrap-dropdown.js"></script>
	<script src="${appPath}/admin/js/bootstrap-scrollspy.js"></script>
	<script src="${appPath}/admin/js/bootstrap-tab.js"></script>
	<script src="${appPath}/admin/js/bootstrap-tooltip.js"></script>
	<script src="${appPath}/admin/js/bootstrap-popover.js"></script>
	<script src="${appPath}/admin/js/bootstrap-button.js"></script>
	<script src="${appPath}/admin/js/bootstrap-collapse.js"></script>
	<script src="${appPath}/admin/js/bootstrap-carousel.js"></script>
	<script src="${appPath}/admin/js/bootstrap-typeahead.js"></script>
	<script src="${appPath}/admin/js/bootstrap-tour.js"></script>
	<!-- calander -->
	<script src='${appPath}/admin/js/fullcalendar.min.js'></script>
	<!-- data table -->
	<script src='${appPath}/admin/js/jquery.dataTables.js'></script>
	<!-- chart -->
	<script src="${appPath}/admin/js/excanvas.js"></script>
	<script src="${appPath}/admin/js/jquery.flot.min.js"></script>
	<script src="${appPath}/admin/js/jquery.flot.pie.min.js"></script>
	<script src="${appPath}/admin/js/jquery.flot.stack.js"></script>
	<script src="${appPath}/admin/js/jquery.flot.resize.min.js"></script>
	<!-- select -->
	<script src="${appPath}/admin/js/jquery.chosen.min.js"></script>
	<script src="${appPath}/admin/js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="${appPath}/admin/js/jquery.colorbox.min.js"></script>
	<!-- editor -->
	<script src="${appPath}/admin/js/jquery.cleditor.min.js"></script>
	<script src="${appPath}/admin/js/jquery.noty.js"></script>
	<script src="${appPath}/admin/js/jquery.elfinder.min.js"></script>
	<script src="${appPath}/admin/js/jquery.raty.min.js"></script>
	<script src="${appPath}/admin/js/jquery.iphone.toggle.js"></script>
	<script src="${appPath}/admin/js/jquery.autogrow-textarea.js"></script>
	<script src="${appPath}/admin/js/jquery.uploadify-3.1.min.js"></script>
	<!-- validate -->
	<script type="text/javascript" src="${appPath}/admin/js/jquery.metadata.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery-ui-timepicker-zh-CN.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery.form.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/json2.js"></script>
	<!-- application script for Charisma -->
	<script src="${appPath}/admin/js/charisma.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery.ztree.core-3.4.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery.ztree.excheck-3.4.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery.ztree.exedit-3.4.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${appPath}/admin/js/ueditor/lang/zh-cn/zh-cn.js"></script>
	<!-- dropdown -->
	<script src="${appPath}/admin/js/jquery.multiselect.js"></script>
	<script src="${appPath}/admin/js/jquery.rotate.js"></script>
	<script src="${appPath}/admin/js/jquery.contextmenu.r2.packed.js"></script>
	<script type="text/javascript" src="${appPath}/admin/js/jquery.treetable.js"></script>
</body>
</html>
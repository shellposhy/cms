<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
<div id="content">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="1">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">首页</a></li>
		</ul>
	</div>
	<!--  first -->
	<div class="sortable row-fluid ui-sortable">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/view/page" target="_self" data-original-title="页面发布管理">
			<span class="icon32 icon-red icon-book"></span>
			<div>页面管理</div>
			<div class="fontlt">查看</div> 
			<span class="notification red">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/view/model" target="_self" data-original-title="页面模板管理">
			<span class="icon32 icon-blue icon-book-empty"></span>
			<div>模板管理</div>
			<div class="fontlt">查看</div>
			 <span class="notification blue">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/task" target="_self"  data-original-title="系统任务管理">
			<span class="icon32 icon-red icon-bookmark"></span>
			<div>系统任务</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
	</div>
	<!-- second -->
	<div class="sortable row-fluid ui-sortable mt20">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/system/library" target="_self" data-original-title="数据库、数据管理">
			<span class="icon32 icon-orange icon-document"></span>
			<div>系统数据</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/library/model" target="_self" data-original-title="数据库模板管理">
			<span class="icon32 icon-color icon-archive"></span>
			<div>数据库模板</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/data/qs" target="_self" data-original-title="数据检索服务">
			<span class="icon32 icon-green icon-search"></span>
			<div>数据查询</div>
			<div class="fontlt">查看</div> 
			<span class="notification green">查看</span>
		</a>
	</div>
	<!-- three -->
	<div class="sortable row-fluid ui-sortable mt20">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/user" target="_self"  data-original-title="用户管理">
			<span class="icon32 icon-red icon-user"></span>
			<div>用户管理</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/userGroup" target="_self" data-original-title="用户组（角色）管理">
			<span class="icon32 icon-color icon-users"></span>
			<div>用户组管理</div>
			<div class="fontlt">查看组</div> 
			<span class="notification yellow">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/report" target="_self" data-original-title="数据统计服务">
			<span class="icon32 icon-color icon-star-on"></span>
			<div>日志报告</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span>
		</a>
	</div>
	<!-- / 首页九宫格导航 -->
	<div class="index_tip_box">
		<div class="box ">
			<p class="h200 p20">
				更多帮助，请参见<a href="#">帮助文档</a>
			</p>
		</div>
	</div>
</div>
</body>
</html>

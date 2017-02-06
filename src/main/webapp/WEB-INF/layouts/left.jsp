<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 主菜单 -->
<div id="mmjsonActionTree" class="none"><%=request.getSession().getAttribute("jsonActionTree")%></div>
<div class="cmsTreeBackground">
	<div class="my_account">
		<div class="username">
			您好，${currentUser.realName }
		</div>
		<span class="dropdown floatr"> 
			<a class="dropdown-toggle block" data-toggle="dropdown" href="#">操作 <b class="caret mt10"></b></a>
			<ul class="dropdown-menu">
				<li><a href="/admin/logout" target="_self"><i class="icon icon-blue icon-locked"></i>	退出管理系统</a>	</li>
				<li><a href="/static/default/index.html"	target="_blank"><i class="icon icon-blue icon-home"></i> 查看前台页面</a></li>
			</ul> 
		</span>
	</div>
	<ul id="cmstree" class="ztree"></ul>
	<a href="#" id="hideMenuTree"><i class="icon icon-blue icon-carat-1-w"></i></a>
</div>
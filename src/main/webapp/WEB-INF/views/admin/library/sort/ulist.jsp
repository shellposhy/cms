<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
<div id="content" class="span12">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="63">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">数据库分类</a></li>
		</ul>
	</div>
	<div class="box-content">
		<ul class="nav nav-tabs clearfix">
			<li><a href="g" target="_self">全局分类</a></li>
			<li class="active"><a href="#" target="_self">数据库分类</a></li>
		</ul>
		<div id="userdb">
			<div class="action_buttons">
				<a class="btn btn-small" href="${appPath}/admin/data/sort" id="add_u_db_btn" target="_self"><i class="icon-plus"></i> 添加标签</a>
				<div class="input-append floatr">
					<input id="search_sort_u" size="16" type="text" />
					<button id="search_sort_u_btn" class="btn search_seled" type="button">搜索</button>
				</div>
			</div>
			<div class="content_wrap clearfix">
				<div class="span3">
					<ul id="dataBaseTree_sort_u" class="ztree green_tree_line"></ul>
				</div>
				<div class="span9">
					<ul id="dataBaseTree_sort_u_s" class="ztree green_tree_line">
						<h3 class='alert alert-info'>请选择左侧分数据库以查看该库的标签</h3>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${appPath}/admin/jscript/library/datasort.js"></script>
</body>
</html>
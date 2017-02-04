<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="63">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">全局分类</a></li>
			</ul>
		</div>
		<div class="box-content">
			<ul class="nav nav-tabs clearfix">
				<li class="active"><a href="#" target="_self">全局分类</a></li>
				<li><a href="u" target="_self">数据库分类</a></li>
			</ul>
			<div id="globdb">
				<input type="hidden" name="baseId" id="baseId" value="0"></input>
				<div class="action_buttons">
					<a class="btn btn-small" href="${appPath}/admin/data/sort/0/new" id="add_g_db_btn" target="_self"><i class="icon-plus"></i>添加分类</a>
					<div class="input-append floatr">
						<input id="search_gsort" size="16" type="text" />
						<button id="search_gsort_btn" class="btn" type="button">搜索</button>
					</div>
				</div>
				<div class="content_wrap clearfix">
					<div class="span3">
						<ul id="dataSortTree_g" class="ztree green_tree_line"></ul>
					</div>
					<div class="span9"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="${appPath}/admin/jscript/library/datasort.js"></script>
</body>
</html>
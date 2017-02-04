<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div id="content" class="span12">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="51">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">快捷查询</a></li>
		</ul>
	</div>
	<div class="qs_box">
		<div class="control-group tree_sel_box" id="tree_sel_db_qs">
			<div class="controls inline">
				<input id="treeSelShow" class="treeSel" type="text" readonly value="选择数据库范围" />
				<input type="hidden" class="treeSelId" name="treeSelIdDb" value="" />
			</div>
			<div class="menuContent">
				<ul id="treeSel_2" class="ztree treeNew"></ul>
				<a class="btn sel_all ml10">全选</a> 
				<a class="btn sel_none">全不选</a>
				<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i> 确定</a>
			</div>
			<input id="queryString" name="queryString" size="16" type="text" />
			<button id="qsBtn" class="btn mb9 btn-small btn-primary" type="button">搜索</button>
		</div>
	</div>
</div>
<script src="${appPath}/admin/jscript/search/quickQuery.js"></script>
</body>
</html>
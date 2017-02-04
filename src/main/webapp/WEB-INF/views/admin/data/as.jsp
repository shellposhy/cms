<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.*"%>
<html>
<body>
<div id="content" class="span12">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="52">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">高级查询</a></li>
		</ul>
	</div>
	<ul id="query_exp_tab" class="nav nav-tabs">
		<li class="active" id="muitl_tab"><a href="#multiExp" data-toggle="tab">多条件查询</a></li>
		<li id="exp_tab"><a href="#exp" data-toggle="tab">表达式查询</a></li>
	</ul>
	<div id="TabContent" class="tab-content visible">
		<div class="tab-pane fade in active " id="multiExp">
			<form class="form-horizontal" id="query_as_form_m" action="${appPath}/admin/data/0/as" method="post">
				<div class="span12">
					<div class="ml35 alert alert-info"><strong>查询范围</strong></div>
				</div>
				<div class="control-group">
					<div class="control-group" class="tree_sel_box" id="tree_sel_db_as_m">
						<div class="ml35">
							<input id="treeSelShow" class="treeSel" type="text" readonly value="选择数据库范围" />
							<input id="searchConIdStr" type="hidden" class="treeSelId" name="searchIdStr" value="" />
						</div>
						<div class="menuContent">
							<ul id="treeSel_1" class="ztree treeNew"></ul>
							<a class="btn sel_all ml10">全选</a> <a class="btn sel_none">全不选</a>
							<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="ml35"><div class="alert alert-info"><strong>查询条件</strong></div></div>
				</div>
				<div id="dataFields" class="ml35"></div>
				<div class="control-group ml35">
					<a id="addShowDatafields" class="btn btn-small" href="#"><i
						class="icon-plus"></i> 添加字段</a>
				</div>
				<div class="showDataFields clearfix ml35 none">
					<div class="control-group">
						<span>选择字段：</span>
						<select id="dbFields" class="input-xlarge" name="dbFields"></select>
						<a id="addDataField" href="#" class="btn">确定</a>
					</div>
				</div>
				<div class="span12 ml0">
					<div class="form-actions">
						<input id="conBtn" type="button" class="btn btn-primary" value="搜索" />
					</div>
				</div>
			</form>
		</div>
		<div class="tab-pane fade" id="exp">
			<form:form class="form-horizontal" id="query_as_form_e" action="${appPath}/admin/data/1/as" method="post">
				<fieldset>
					<div class="span12">
						<div class="ml35 alert alert-info"><strong>查询范围</strong></div>
					</div>
					<div class="control-group" class="tree_sel_box" id="tree_sel_db_as_e">
						<div class="ml35">
							<input id="etreeSelShow" class="treeSel" type="text" readonly value="选择数据库范围" />
							<input id="searchExpIdStr" type="hidden" class="treeSelId" name="searchIdStr" value="" />
						</div>
						<div class="menuContent">
							<ul id="treeSel_2" class="ztree treeNew"></ul>
							<a class="btn sel_all ml10">全选</a> <a class="btn sel_none">全不选</a>
							<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
						</div>
					</div>
					<div>
						<div class="span6 ml35">表达式：</div>
					</div>
					<div class="span11 ml35">
						<textarea class="span11 h200" name="expression" id="expression"
							placeholder="请输入表达式,如 Title:人民日报  AND/OR/NOT #long#Doc_Time:[20101001000000 TO 20121001121212] AND/OR/NOT #int#Page_Num:1"></textarea>
					</div>
					<div class="span12 ml0">
						<div class="form-actions">
							<input id="expBtn" type="button" class="btn btn-primary" value="搜索" />
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</div>
<script src="${appPath}/admin/jscript/search/advancedQuery.js"></script>
</body>
</html>
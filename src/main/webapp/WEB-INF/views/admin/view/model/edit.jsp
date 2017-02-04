<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="22">
				<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath }/admin/view/model" target="_self">模板管理</a><span class="divider">/</span></li>
				<li><a href="#">编辑模板</a></li>
			</ul>
		</div>
		<fieldset>
			<legend> 编辑模板 </legend>
		</fieldset>
		<iframe name="aa" src="" style="display: none"></iframe>
		<form:form modelAttribute="viewModel" enctype="multipart/form-data" class="form-horizontal u_group_form" id="group_new_form"
			action="${appPath}/admin/view/model" method="post" target="_self">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="u_group_name">名称 </label>
					<div class="controls">
						<form:hidden path="id" />
						<form:input path="name" name="name" class="typeahead" id="s_param_name" data-provide="typeahead" data-items="4" data-source='["admin","administrator","geust"]' />
						<label class="error"><form:errors path="name" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="u_group_code">编号 </label>
					<div class="controls">
						<form:input path="code" name="code" class="typeahead" id="s_param_code" />
						<label class="error"><form:errors path="code" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="modelType">模板类型</label>
					<div class="controls">
						<form:select path="modelType" class="autogrow" name="modelType" id="modelType">
							<form:options items="${columnTypes}" itemLabel="title" />
						</form:select>
						<label class="error"><form:errors path="modelType" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="u_group_code">排序号 </label>
					<div class="controls">
						<form:input path="orderId" name="orderId" class="typeahead" id="s_param_value" />
						<label class="error"><form:errors path="orderId" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group" class="tree_sel_box" id="type_tree">
					<label class="control-label" for="u_group_memo">分类</label>
					<div class="controls">
						<input class="treeRadio" type="text" readonly value="" />
						<label class="error"><form:errors path="categoryId" cssClass="error" /></label>
						<form:hidden path="categoryId" class="treeSelId" name="categoryId" value="" />
					</div>
					<div class="menuContent">
						<ul id="treeSel_1" class="ztree treeNew"></ul>
						<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">上传模板 </label>
					<div class="controls">
						<form id="pupdate_form" enctype="multipart/form-data">
							<input name="file" type="file" />
						</form>
					</div>
				</div>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</fieldset>
		</form:form>
	</div>
	<script src="${appPath}/admin/jscript/view/viewModel.js"></script>
</body>
</html>
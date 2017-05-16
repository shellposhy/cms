<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div id="content" class="span12 ml0">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="71">
			<li><a href="${appPath }/admin" target="_self">${appName}</a><span class="divider">/</span></li>
			<li><a href="#">机构</a><span class="divider">/</span></li>
			<li><a href="#">编辑</a></li>
		</ul>
	</div>
	<form:form modelAttribute="org" class="form-horizontal u_form" id="org_new_form" action="${appPath}/admin/org/save" method="post" target="_self">
		<fieldset>
			<legend>
				<span class=" floatl"></span>机构编辑
			</legend>
			<div class="clearfix ml35">
				<div class="control-group">
					<label class="control-label" for="name">机构名称 </label>
					<div class="controls">
						<form:input path="name" name="name" class="typeahead" id="name" />
						<form:hidden path="id" />
						<label class="error springerror"><form:errors path="name" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="code">机构编码 </label>
					<div class="controls">
						<form:input path="code" name="code" class="typeahead" id="code" />
						<label class="error springerror"><form:errors path="code" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="treeSelId_box">所属角色 </label>
					<div class="controls">
						<select multiple="true" data-rel="chosen" data-placeholder="请选择用户组" name="treeSelId_box" class="typeahead" id="treeSelId_box"></select>
						<form:hidden path="treeSelId" id="treeSelId" />
					</div>
					<div class="none" id="groupListJson">${groupListJson}</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inherit">用户组继承到子机构</label>
					<div class="controls">
						<form:checkbox path="inherit"/>
					</div>
				</div>
			</div>
			<div class="span10">
				<div class="control-group" id="editOrg">
					<label class="control-label" for="parentID">所属机构</label>
					<div class="controls">
						<input class="treeRadio" type="text" value="" readonly="readonly" />
						<form:hidden path="parentID" id="parentID" name="parentID" class="treeSelId" />
					</div>
					<div class="menuContent">
						<ul id="treeSel_1" class="ztree treeNew"></ul>
						<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
					</div>
				</div>
			</div>
			<div class="span12 ml0">
				<div class="form-actions">
					<form:button type="submit" class="btn btn-primary">保存</form:button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>
<script src="${appPath}/admin/jscript/user/org.js"></script>
</body>
</html>
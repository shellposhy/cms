<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div id="content" class="span12">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="61">
			<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="${appPath }/admin/userdb" target="_self">系统数据库</a><span class="divider">/</span></li>
			<li><a href="#">数据库目录</a></li>
		</ul>
	</div>
	<div class="box-content">
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane active" id="setting">
				<form:form modelAttribute="dataBase" class="form-horizontal u_group_form" id="db_new_form"
					action="${appPath}/admin/system/library/directory/save" method="post" target="_self">
					<fieldset>
						<legend>
							<span title="" class="icon32 icon-inbox floatl"></span>数据库目录编辑
						</legend>
						<div class="control-group">
							<label class="control-label" for="database_name">名称</label>
							<div class="controls">
								<form:hidden path="id" />
								<form:input path="name" name="name" class="typeahead" id="db_name" />
								<label class="error"><form:errors path="name" cssClass="error" /> </label>
							</div>
						</div>
						<div class="control-group backhide">
							<label class="control-label" for="database_name">编号</label>
							<div class="controls">
								<form:input path="code" name="code" class="typeahead" id="db_code" />
								<label class="error"><form:errors path="code" cssClass="error" /> </label>
							</div>
						</div>
						<div class="control-group backhide" class="tree_sel_box"
							id="type_tree">
							<label class="control-label" for="u_group_memo">父目录</label>
							<div class="controls">
								<input class="treeRadio" type="text" readonly value="" />
								<label class="error"><form:errors path="parentID" cssClass="error" /> </label>
								<form:hidden path="parentID" class="treeSelId" name="parentID" value="" />
							</div>
							<div class="menuContent">
								<ul id="treeSel_1" class="ztree treeNew"></ul>
								<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
							</div>
						</div>
						<form:hidden path="type" name="type" id="type" />
						<form:hidden path="type.value" name="dbtype" id="dbtype" />
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">保存</button>
							<input type="button" name="cancel" value="取消" class="btn backBtn"/>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>
<div id="manage_category" class="dialog" title="分类管理" style="display: none;"></div>
<script type="text/javascript" src="${appPath}/admin/jscript/library/directory.js"></script>
</body>
</html>
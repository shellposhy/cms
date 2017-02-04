<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="62">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath}/admin/library/model" target="_self">数据模型</a><span class="divider">/</span></li>
					<li><a href="#">编辑</a>
					</li>
				</ul>
			</div>
			<div class="box-content">
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="#setting">配置</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<!-- 配置 -->
					<div class="tab-pane active" id="setting">
						<form:form modelAttribute="columnModel" class="form-horizontal u_group_form" id="cl_new_form" action="${appPath}/admin/library/model/save" method="post" target="_self">
							<fieldset>
								<legend>
									<span title="" class="icon32 icon-inbox floatl"></span>数据模型编辑
								</legend>
								<div class="control-group">
									<label class="control-label" for="cloumn_name">名称</label>
									<div class="controls">
										<form:hidden path="id" />
										<form:input path="name" name="name" class="typeahead" id="cl_name" />
										<label class="error"><form:errors path="name" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group backhide">
									<label class="control-label" for="cloumn_code">编号</label>
									<div class="controls">
										<form:input path="code" name="code" class="typeahead" id="cl_code" />
										<label class="error"><form:errors path="code" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group" >
									<label class="control-label" for="cloumn_type">类型</label>
									<div class="controls">
										<form:select path="type" class="autogrow" name="type" id="cl_type">
											<form:options items="${typeOptions}" itemLabel="title" />
										</form:select>
										<label class="error"><form:errors path="type" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="cloumn_fields">字段组合</label>
									<div class="controls">
										<div class="feilds_form_box">
											<form:select id="moreFieldsSelect" path="" multiple="true" class="typeahead span2 multiSelect">
												<form:options items="${allFields}" itemValue="id" itemLabel="fullName" />
											</form:select>
											<form:hidden id="moreFields" path="moreDataFieldsStr" name="moreDataFieldsStr" />
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="cloumn_memo">备注</label>
									<div class="controls">
										<form:textarea path="memo" name="memo" class="typeahead" id="cl_memo" />
										<label class="error"><form:errors path="memo" cssClass="error" /> </label>
									</div>
								</div>
								<div class="form-actions">
									<button id="cl_btn" type="submit" class="btn btn-primary">保存</button>
									<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	<script src="${appPath}/admin/jscript/library/model.js"></script>
	</body>
</html>
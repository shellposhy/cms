<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html><body>
<div id="content" class="span12">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="103">
			<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="${appPath}/admin/param" target="_self">系统参数</a><span class="divider">/</span></li>
			<li><a href="#">编辑</a>
			</li>
		</ul>
	</div>
	<div class="box-content">
		<ul class="nav nav-tabs" id="myTab">
			<li class="active"><a href="#setting">配置</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane active" id="setting">
				<form:form modelAttribute="param" class="form-horizontal u_group_form" id="param_new_form" action="${appPath}/admin/param" method="post" target="_self">
					<fieldset>
						<legend>
							<span title="" class="icon32 icon-inbox floatl"></span>系统参数编辑
						</legend>
						<div class="control-group">
							<label class="control-label" for="param_name">参数名称</label>
							<div class="controls">
								<form:hidden path="id" />
								<form:input path="name" name="name" class="typeahead" id="param_name" />
								<label class="error"><form:errors path="name" cssClass="error" /> </label>
							</div>
						</div>
						<div class="control-group backhide">
							<label class="control-label" for="param_code">编码</label>
							<div class="controls">
								<form:input path="code" name="code" class="typeahead" id="param_code" />
								<label class="error"><form:errors path="code" cssClass="error" /> </label>
							</div>
						</div>
						<div class="control-group" >
							<label class="control-label" for="param_type">类型</label>
							<div class="controls">
								<form:select path="paramType" class="autogrow" name="paramType" id="param_type">
									<form:option value="SysParam">系统参数</form:option>
									<form:option value="UserField">用户扩展字段</form:option>
									<form:option value="MobileImgSize">移动端图片尺寸</form:option>
								</form:select>
								<label class="error"><form:errors path="paramType" cssClass="error" /> </label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="param_memo">参数值</label>
							<div class="controls">
								<form:input path="value" name="value" class="typeahead" id="param_memo" />
								<label class="error"><form:errors path="value" cssClass="error" /> </label>
							</div>
						</div>
						<div class="form-actions">
							<button id="param_btn" type="submit" class="btn btn-primary">保存</button>
							<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>
<script src="${appPath}/admin/jscript/param/param.js"></script>
</body>
</html>
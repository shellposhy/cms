<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="span12 ml0">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="21">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath}/admin/view/page" target="_self">页面管理</a><span class="divider">/</span></li>
				<li><a href="#">新建</a></li>
			</ul>
		</div>
		<form id="viewPageForm" class="form-horizontal viewPageForm active" action="${appPath}/admin/view/page" method="post">
			<fieldset>
				<legend>
					<span title="" class="icon32 icon-inbox floatl active"></span>页面编辑
				</legend>
				<div class="control-group">
					<label class="control-label" for="title">页面名称 </label>
					<div class="controls">
						<input id="title" name="title" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="code">页面编号 </label>
					<div class="controls">
						<input id="code" name="code" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="viewPage_Type">页面类型</label>
					<div class="controls">
						<select class="autogrow" name="pageType" id="viewPage_Type">
							<option value="1">首页</option>
							<option value="2">专题页</option>
						</select>
					</div>
				</div>
				<div class="control-group" id="subjectModel">
					<label class="control-label" for="u_group_memo">页面模板</label>
					<div class="controls">
						<select id="para" name="modelId">
						</select>
					</div>
				</div>
				<div class="control-group" id="logodiv">
					<label class="control-label">模板预览</label>
					<div class="controls">
						<img id="logo" alt="logo" src="#" width="250" height="250" align="left">
					</div>
				</div>
				<div class="form-actions">
					<button id="viewPage_btn" type="submit" class="btn btn-primary">保存</button>
					<button name="cancel" value="取消" class="btn backBtn">取消</button>
				</div>
			</fieldset>
		</form>
	</div>
	<div class="modal hide fade form-horizontal" id="editModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>开始配置</h3>
		</div>
		<div class="modal-body">
			<p>您现在是否需要对该页面进行内容配置？</p>
		</div>
		<div class="modal-footer">
			<a href="#" id="edit_ok" class="btn btn-primary">是</a>
			<a href="#" id="close_ok" class="btn" data-dismiss="modal">否</a>
		</div>
	</div>
	<script src="${appPath}/admin/jscript/view/viewPage.js"></script>
</body>
</html>
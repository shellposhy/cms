<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="22">
				<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath }/admin/view/model" target="_self">模板管理</a><span class="divider">/</span></li>
				<li><a href="#">文件管理</a></li>
			</ul>
		</div>
		<div class="box-content">
			<ul id="tab" class="nav nav-tabs nav_tabs_for_datatables">
				<li class="active"><a href="#list_tab" data-toggle="tab">模板文件</a></li>
				<!--  <li><a href="#update_tab" data-toggle="tab">更新模板</a></li>-->
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane clearfix  tree_separated fade in active" id="list_tab">
					<div class="content_wrap mt30 clearfix">
						<div class="left_tree">
							<ul id=filesTree class="ztree green_tree_line"></ul>
						</div>
						<div class="right_con">
							<form id="fileMng" class="form-horizontal" method="post">
								<input type="hidden" id="viewModelId" value="${viewModel.id}">
								<div class="control-group">
									<label class="control-label">文件名：</label>
									<div class="controls">
										<input type="text" id="fileName" name="fileName" readonly="readonly" value="">
									</div>
									<br> <label class="control-label">文件内容：</label>
									<div class="controls">
										<textarea style="height: 500px; width: 755px;" name="newfileContent" id="newfileContent"></textarea>
										<img alt="图片" src="#" id="imgFile" hidden="true">
									</div>
								</div>
								<div class="form-actions">
									<input type="button" value="保存模板内容" id="fileSave" class="btn btn-primary">
								</div>
								<input type="hidden" id="fileContent" name="fileContent">
							</form>
						</div>
					</div>
				</div>
				<div class="tab-pane clearfix fade" id="update_tab">
					<div class="pupdate_warp">
						<form id="pupdate_form" action="${appPath}/admin/view/model/import/${viewModel.id}" method="post" enctype="multipart/form-data">
							<span class="pl20">选择文件:</span>
							<input class="input-file uniform_on" name="file" type="file" />
							<input class="btn" type="submit" value="上传" />
							<div class="error_div ml20"></div>
						</form>
						<div class="progress progress-striped progress-success active none" id="pupdate_progress">
							<div class="bar"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${appPath}/admin/jscript/view/viewModel.js"></script>
</body>
</html>
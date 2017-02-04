<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
	<body>
		<div id="content" class='span12 tree_separated' >
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="22">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#">模板管理</a></li>
				</ul>
			</div>
			<div class="box-content">
				<div class="padlrn span12 action_buttons">
					<a class="btn btn-small" href="#" id="add_directory_model" target="_self"><i class="icon-plus"></i> 添加模板目录</a>
					<div class="input-append floatr">
						<input id="search_model" size="16" type="text" />
						<button id="search_model_btn" class="btn" type="button">搜索</button>
					</div>
				</div>
				<div class="content_wrap mt30 clearfix">
					<div class="left_tree">
						<ul id=directoryModelTree class="ztree green_tree_line"></ul>
					</div>
					<div class="right_con">
						<ul id="models" class="show_sel_itme clearfix">
							<h3 class='alert alert-info'>请选择左侧目录以查看模板</h3>
						</ul>
					</div>
				</div>
			</div>
		</div>
	<script src="${appPath}/admin/jscript/view/viewModel.js"></script>
	</body>
</html>
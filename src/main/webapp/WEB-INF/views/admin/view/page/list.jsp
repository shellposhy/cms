<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="21">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">页面管理</a></li>
			</ul>
		</div>
		<div class="padlrn span6 action_buttons">
			<a class="btn" href="${appPath}/admin/view/page/new" target="_self"><i class="icon-plus"></i> 添加</a>
			<a class="btn delete_list" href="#"><i class="icon-trash"></i> 删除</a>
			<a class="btn" href="#" id="publish_list"><i class="icon-share"></i> 首页发布</a>
			<!-- <a class="btn" href="#" id="subPublish_list"><i class="icon-share"></i> 二级页面发布</a> -->
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="view_page_list">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />名称</label></th>
					<th width="180px">类型</th>
					<th width="180px">状态</th>
					<th width="180px">创建时间</th>
					<th width="180px">发布时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>名称</th>
					<th width="180px">类型</th>
					<th width="180px">状态</th>
					<th width="180px">创建时间</th>
					<th width="180px">发布时间</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<div class="modal hide fade form-horizontal" id="noticeModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>确定发布</h3>
		</div>
		<div class="modal-body">
			<p>您确定要发布这些页面吗？发布时间比较耗时，请耐心等待！</p>
		</div>
		<div class="modal-footer">
			<span class="loading floatl none"></span>
			<a href="#" class="btn btn-primary">发布</a>
			<a href="#" class="btn" data-dismiss="modal">取消</a>
		</div>
	</div>
	<script src="${appPath}/admin/jscript/view/viewPage.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="102">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">任务列表</a></li>
			</ul>
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="taskList">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />事务名称</label></th>
					<th>类型</th>
					<th>进度</th>
					<th>状态</th>
					<th>创建日期</th>
					<th>更新日期</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>事务名称</th>
					<th>类型</th>
					<th>进度</th>
					<th>状态</th>
					<th>创建日期</th>
					<th>更新日期</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<script src="${appPath}/admin/jscript/task/task.js"></script>
</body>
</html>
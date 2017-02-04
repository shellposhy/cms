<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="103">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">系统参数列表</a></li>
			</ul>
		</div>
		<div class="padlrn span6 action_buttons">
			<a class="btn btn-small" href="${appPath}/admin/param/new" target="_self"><i class="icon-plus"></i> 添加</a> 
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="paramList">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />参数名称</label></th>
					<th>参数类型</th>
					<th>编码</th>
					<th>参数值</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>参数名称</th>
					<th>参数类型</th>
					<th>编码</th>
					<th>参数值</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<script src="${appPath}/admin/jscript/param/param.js"></script>
</body>
</html>
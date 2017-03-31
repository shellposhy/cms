<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="101">
				<li><a href="${appPath }/admin">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">数据库字段管理</a></li>			
			</ul>
		</div>
		<div class="padlrn span6 action_buttons">
			<a class="btn btn-small" href="${appPath}/admin/data/field/new" target="_self"><i class="icon-plus"></i> 添加</a> 
			<!--<a class="btn btn-small delete_list" href="#"><i class="icon-trash"></i>删除</a>-->
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit" id="dataField">
			<thead>
				<tr>
					<th><label class="checkbox inline">名称</label></th>
					<th>编码</th>
					<th>数据类型</th>
					<th>是否无符号</th>
					<th>长度</th>
					<th>准确度</th>
					<th>是否必填</th>
					<th>是否唯一</th>
					<th>是否多值</th>
					<th>索引类型</th>
					<th>类型</th>
					<th>操作类型</th>
					<th>是否排序</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>名称</th>
					<th>编码</th>
					<th>数据类型</th>
					<th>是否无符号</th>
					<th>长度</th>
					<th>准确度</th>
					<th>是否必填</th>
					<th>是否唯一</th>
					<th>是否多值</th>
					<th>索引类型</th>
					<th>类型</th>
					<th>操作类型</th>
					<th>是否排序</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<script src="${appPath}/admin/jscript/library/datafield.js"></script>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
<div id="content" class="span12">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="51">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">"${searchDbName}"的数据</a></li>
		</ul>
	</div>
	<div id="godatadiv" class="mb20"></div>
	<div class="box-content">
		<div class="btn-toolbar floatl"> 
		  <a class="btn btn-small" id="into_as_search" href="${appPath }/admin/data/as"><i></i> 进入高级查询</a>
		</div>
		<table  cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="dataTable">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />标题</label></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>标题</th>
				</tr>
			</tfoot>
		</table>
</div>
</div>
<input type="hidden" id="mSearch" value="${mSearch}" />
<input type="hidden" id="searchIdStr" value="${searchIdStr }">
<input type="hidden" id="iType" value="${iType }">
<script type="text/javascript" 	src="${appPath}/admin/jscript/data/data.js"></script>
</body></html>
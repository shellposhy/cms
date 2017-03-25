<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="92">
				<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">前台日志</a> <input type="hidden" id="logType" value="0" /></li>
			</ul>
		</div>
		<div class="box-content">
			<ul id="tab" class="nav nav-tabs nav_tabs_for_datatables">
				<li><a href="${appPath}/admin/log/1/list" target="_self">后台日志</a></li>
				<li class="active"><a href="#" data-toggle="tab">前台日志</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div id="ulist_tab" class="tab-pane clearfix fade in active">
					<div class="tab-pane fade in active" id="password">
						<div class="padlrn span6 action_buttons">
							<label class="datapictip floatl">日期：</label>
							<input class="datepicker floatl" type="text" />
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit" id="accessLogMng">
							<thead>
								<tr>
									<th>用户名</th>
									<th>访问地址</th>
									<th>操作类型</th>
									<th>操作对象</th>
									<th>IP</th>
									<th>操作时间</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
							<tfoot>
								<tr>
									<th>用户名</th>
									<th>访问地址</th>
									<th>操作类型</th>
									<th>操作对象</th>
									<th>IP</th>
									<th>操作时间</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${appPath}/admin/jscript/log/log.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="93">
				<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">日志报告</a> <input type="hidden" id="logType" value="0" /></li>
			</ul>
		</div>
		<ul class="nav nav-tabs">
			<li class="active"><a href="#">系统概况报告</a></li>
		</ul>
		<div class="input-append input-prepend report_form_div clearfix">
			<form id="com_report_form">
				<div class="span6">
					<div class="btn-group btn-group-report floatl" data-toggle="buttons-radio">
						<a href="#" value="7" class="btn btn-success">最近7天</a>
						<a href="#" value="30" class="btn btn-success active">最近30天</a>
						<input type="hidden" id="timePeroid" name="timePeroid" value="30" class="valid" />
					</div>
				</div>
				<span class="error_div"></span>
			</form>
		</div>

		<div class="row-fluid sortdisable ui-sortable">
			<div class="box">
				<div class="box-header well">
					<h2><i class="icon-list-alt"></i> 浏览量统计</h2>
				</div>
				<div class="box-content">
					<div>
						<h2 class="rep_title"></h2>
						<h3 class="rep_info"></h3>
					</div>
					<div id="sysPvCount" class="center report_box"></div>
				</div>
			</div>
			<div class="box">
				<div class="box-header well">
					<h2><i class="icon-list-alt"></i> 平均日浏览时间分布</h2>
				</div>
				<div class="box-content">
					<div>
						<h2 class="rep_title"></h2>
						<h3 class="rep_info"></h3>
					</div>
					<div id="hotHourPv" class="center report_box"></div>
				</div>
			</div>
		</div>
		
		<div class="row-fluid sortdisable">
			<div class="box span6 ">
				<div class="box-header well">
					<h2><i class="icon-list-alt"></i> 用户数据统计</h2>
				</div>
				<div class="box-content">
					<div>
						<h2 class="rep_title"></h2>
						<h3 class="rep_info"></h3>
					</div>
					<div id="userDataCount" class="center report_box pie"></div>
				</div>
			</div>
			<div class="box span6">
				<div class="box-header well">
					<h2>
						<i class="icon-list-alt"></i> 完成项目列表
					</h2>
				</div>
				<div class="box-content">
					<div id="projectFinishList" class="report_box">
						<ul class="dashboard-list">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${appPath}/admin/jscript/log/report.js"></script>
</body>
</html>
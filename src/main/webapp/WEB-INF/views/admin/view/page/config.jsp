<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="">
		<div class="">
			<ul class="breadcrumb ind_f_tree" value="21">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath}/admin/viewPage" target="_self">页面管理</a><span class="divider">/</span></li>
				<li><a href="#">页面配置</a></li>
			</ul>
		</div>
		<div class="form-horizontal">
			<div class="control-group">
				<label class="control-label" for="pageConfigeName">页面名称</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" size="16" id="pageConfigeName" name="pageConfigeName" value="${pageTitle }" readonly="readonly" />
						<a class="btn" href="${appPath}/admin/view/page" target="_self">返回</a>
					</div>
				</div>
			</div>
		</div>
		<iframe id="iFramePageConf" name="iFramePageConf" width="100%" onload="iframeFix(this)" frameborder="0" scrolling="auto" 
			src="${appPath}/admin/view/page/config/preview/${pageId}"></iframe>
	</div>

	<div class="modal hide fade form-horizontal areaConfModal" id="areaConfModal">
		<form:form modelAttribute="viewContent" action="${appPath}/admin/view/page/config/${pageId }/saveItem" id="viewItemForm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>配置</h3>
			</div>
			<div class="modal-body">
				<form:hidden path="itemCode" id="curItemId" name="curItemId" />
				<form:hidden path="pageId" name="pageId" value="${pageId }" />
				<ul class="nav nav-tabs">
					<li class="active"><a href="#formDb" data-toggle="tab">数据源</a></li>
				</ul>
				<div class="tab-content" style="overflow: visible;">
					<div class="tab-pane active" id="formDb">
						<table>
							<tr>
								<td><label class="control-label">区域标题：</label></td>
								<td><div class="relative_tree_box"><div class="inline"><form:input path="name" id="name" name="name" /></div></div></td>
							</tr>
							<tr>
								<td><label class="control-label">数据来源：</label></td>
								<td>
									<div class="relative_tree_box" id="itemContentSrc">
										<div class="inline">
											<input class="treeRadio" type="text" value="" readonly="readonly" />
											<form:hidden path="content" id="content" name="content" class="treeSelId" />
										</div>
										<div class="menuContent">
											<ul id="treeSel_1" class="ztree treeNew"></ul>
											<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i> 确定</a>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td><label class="control-label">数据筛选：</label></td>
								<td><input type="hidden" id="switch_value" /> <input type="checkbox" class="checkbox" id="filter_witch" /></td>
							</tr>
							<tr id="filter_tr">
								<td><label class="control-label">筛选条件：</label></td>
								<td><div class="relative_tree_box"><div class="inline"><form:input path="filterCondition" id="filterCondition" name="filterCondition" /></div></div></td>
							</tr>
							<tr>
								<td><label class="control-label" for="itemNameLinkType ">连接类型：</label></td>
								<td>
									<div class="relative_tree_box">
										<form:hidden path="nameLinkType" id="nameLinkType" name="nameLinkType" value="NormalListLink" />
										<!-- <label><input id='nameLinkType_opt_2' name='nameLinkType_opt' class='nameLinkType_opt' type='radio' onChange="linkTypeChange()" value='ColumnLink' checked>通用二级页&nbsp;&nbsp;</label>-->
										<label><input id='nameLinkType_opt_1' name='nameLinkType_opt' class='nameLinkType_opt' type='radio' onChange="linkTypeChange()" value='NormalListLink' checked>通用列表页&nbsp;&nbsp;</label>
										<!-- <label><input id='nameLinkType_opt_0' name='nameLinkType_opt' class='nameLinkType_opt' type='radio' onChange="linkTypeChange()" value='UserLink'>自定义</label>-->
									</div>
								</td>
							</tr>
							<tr id="link_tr" hidden="true">
								<td><div id="nameLink_Label"><label class="control-label" for="nameLink ">连接地址：</label></div></td>
								<td><div id="nameLink_Value"><form:input path="nameLink" name="nameLink" /></div></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane" id="formCus">
						<table>
							<tr>
								<td><label class="control-label">模板内容：</label></td>
								<td><div class="relative_tree_box" id="itemContentFt"><textarea id="contentHtml" rows="10" cols="100"> </textarea></div></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<a href="#" id="btn_save" class="btn btn-primary">保存</a>
				<a href="#" id="btn_cancel" class="btn" data-dismiss="modal">取消</a>
			</div>
		</form:form>
	</div>
	<script src="${appPath}/admin/jscript/view/config.js"></script>
</body>
</html>
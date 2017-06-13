<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
	<body>
		<div id="content" class="span12 ml0">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="81">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath }/admin/userGroup" target="_self">角色管理</a><span class="divider">/</span></li>
					<li><a href="#">角色配置</a></li>
				</ul>
			</div>
			<input type="hidden" id="actionTree" value='${jsonActionTree}'>
			<form:form modelAttribute="userGroup" class="form-horizontal u_group_form" id="group_new_form" action="${appPath}/admin/userGroup" method="post" target="_self">
				<fieldset>
					<legend>
						<span title=".icon32  .icon-users " class="icon32 icon-users floatl"></span>角色编辑
					</legend>
					<div class="control-group">
						<label class="control-label" for="u_group_name">角色名称 </label>
						<div class="controls">
							<form:hidden path="id" />
							<form:hidden path="allAdminAuthority" name="allAdminAuthority" value="" />
							<form:hidden path="allDataAuthority" name="allDataAuthority" value=""  />
							<form:input path="name" name="name" class="typeahead" id="u_group_name" data-provide="typeahead" />
							<label class="error"><form:errors path="name" cssClass="error" /></label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="u_group_code">角色编号 </label>
						<div class="controls">
							<form:input path="code" name="code" class="typeahead" id="u_group_code" />
							<p class="help-block">请用英文和数字填写，用于精确查找该条数据</p>
							<label class="error"><form:errors path="code" cssClass="error" /> </label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="u_group_memo">角色描述</label>
						<div class="controls">
							<form:textarea path="memo" class="autogrow" name="memo" id="u_group_memo"></form:textarea>
							<label class="error"><form:errors path="memo" cssClass="error" /> </label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="u_group_secretLevel">保密级别</label>
						<div class="controls">
							<form:select path="secretLevel" class="autogrow" name="secretLevel" id="u_group_secretLevel">
								<form:option value="0">0-不保密</form:option>
								<form:option value="1">1-初级保密</form:option>
								<form:option value="2">2-低级保密</form:option>
								<form:option value="3">3-中级保密</form:option>
								<form:option value="4">4-高级保密</form:option>
								<form:option value="5">5-顶级保密</form:option>
							</form:select>
							<label class="error"><form:errors path="secretLevel" cssClass="error" /> </label>
						</div>
					</div>
					<div class="control-group"  id="use_group_tree_sel">
						<label class="control-label" for="u_group_memo">后台功能权限配置</label>
						<div class="controls">
							<span class="btn ml10 allAuthor disabled">完全权限</span>
							<span class="btn ml10 notAllAuthor ">详细定制</span>
							<input class="treeSel " type="text" readonly value="" />
							<form:hidden path="treeSelId" class="treeSelId" name="treeSelId" value="" />
							<a class="menuBtn btn" href="#">选择</a>
						</div>
						<div class="menuContent">
							<ul id="treeSel_1" class="ztree treeNew"></ul>
							<a class="btn sel_all ml10">全选</a> <a class="btn sel_none">全不选</a>
							<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>
								确定</a>
						</div>
					</div>
					<div class="control-group"  id="use_group_tree_sel_db">
						<label class="control-label" for="u_group_memo">数据权限配置</label>
						<div class="controls">
							<span class="btn ml10 allAuthorDb disabled" >完全权限</span>
							<span class="btn ml10 notAllAuthorDb ">详细定制</span> 
						</div>
					</div>
					<!--  treeTable  -->
					<div class = "control-group one_input"  id = "user_group_readOnly_tree">
						<div class="controls" >
							 <table id="purview_table" class="tree_table table table-bordered no_uniform">
						        <thead>
						         	<tr><th rowspan="2">数据项</th><th colspan="2"  class="text-center">后台</th><th colspan="3">前台</th></tr>
							          <tr><th>可读</th><th>可写</th><th>浏览</th><th>下载</th><th>打印</th></tr>
						        </thead>
						        <tbody>
						        	 <jsp:include page="dataTree.jsp"/> 
						      	</tbody>
						      </table>
						</div>
					</div>
					<!--  /treeTable  -->
					<div class="control-group">
						<label class="control-label" for="u_group_memo">前台首页类型</label>
						<form:hidden path='defaultPageType' name='defaultPageType' id='defaultPageType' />
						<div class="controls" id="systemIndex">
							<select id="para" name="para">
								<option value="SysPage">系统首页</option>
								<option value="UserPage">自定义首页</option>
							</select>
						</div>
					</div>
					<div class="control-group"  id="defaultPageSel_Area">
						<label class="control-label" for="u_group_memo">系统首页</label>
						<form:hidden path='defaultPageID' name='defaultPageID' id='defaultPageID' />
						<div class="controls" id="sysPage">
							<select id="pageSel" name="paraSel">
								<option value="1">系统首页</option>
							</select>
						</div>
					</div>
					<div class="control-group"  id="defaultPageUrl_area">
						<label class="control-label" for="u_group_memo">自定义首页URL</label>
						<div class="controls">
							<form:input path="defaultPageUrl" name="defaultPageUrl" 
								id="defaultPageUrl" />
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">保存</button>
						<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
					</div>
				</fieldset>
			</form:form>
		</div>
	<script src="${appPath}/admin/jscript/user/userGroup.js"></script>
	</body>
</html>
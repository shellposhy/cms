<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div id="content" class="span12">
	<input type="hidden" id="baseId" value="${dataBase.id}" />
	 <input type="hidden" id="fieldsStr" value="${fieldsStr}" />
	 <input type="hidden" id="tableId" value="${tableId }">
	<input type="hidden" id="dataId" value="${dataId }">
	<input type="hidden" id="appHomePath" value="${appPath }">
	<div class="row-fluid">
		<form:form modelAttribute="dataVo" class="form-horizontal span12" id="Article_new_form"
			action="${appPath}/admin/system/library/data/save/${dataBase.id}" method="post" target="_self">
			<form:hidden path="id" />
			<form:hidden path="uuid" />
			<form:hidden path="createTime" />
			<fieldset>
				<legend>
					<span title="" class="icon32 icon-blue icon-note  floatl"></span>添加稿件
				</legend>
					<div class="postbox_left">
						<div class="control-group">
							<label class="control-label" for="Title">标题</label>
							<div class="controls">
								<form:input path="fieldMap[Title]" class="span6 typeahead" id="Title" name="Title" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Project_ID">项目名称</label>
							<div class="controls ">
								<form:select path="fieldMap[Project_ID]"  id="Project_ID" >
								</form:select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="editor1">正文</label>
							<div class="controls pr">
								<form:textarea path="fieldMap[Content]" class="areaEditor" id="contentArea" />
								<span class="futu_imgs">附图:<input class=" input-mini uneditable-input" value="3" disabled="true" id="Imgs" name="Imgs" />张</span>
							</div>
						</div>
					</div>
					<div class="postbox_right">
						<div class="control-group">
							<label class="control-label" for="Intro_Title">肩标题</label>
							<div class="controls">
								<form:input path="fieldMap[Intro_Title]" class="span6 typeahead" id="Intro_Title" name="Intro_Title" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Sub_Title">副标题</label>
							<div class="controls">
								<form:input path="fieldMap[Sub_Title]" class="span6 typeahead" id="Sub_Title" name="Sub_Title" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Channel_Name">频道名称</label>
							<div class="controls">
								<form:input path="fieldMap[Channel_Name]" class="span6 typeahead" id="Channel_Name" name="Channel_Name" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Colum">栏目</label>
							<div class="controls">
								<form:input path="fieldMap[Colum]" class="span6 typeahead" id="Colum" name="Colum" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Order_ID">顺序</label>
							<div class="controls">
								<form:input path="fieldMap[Order_ID]" class="spinner" id="Order_ID" name="Order_ID" min="0" step="1" value="0" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Authors">作者</label>
							<div class="controls">
								<form:input path="fieldMap[Authors]" class="span6 typeahead" id="Authors" name="Authors" />
							</div>
						</div>		
						<div class="control-group">
							<label class="control-label" for=Keywords>关键词</label>
							<div class="controls mini_input_inline">
								<form:input path="fieldMap[Keywords]" class="input-big" id="Keywords" name="Keywords" />
							</div>
						</div>
						<div class="control-group" id=sort_tree>
							<label class="control-label" for="Sort_Ids">数据分类</label>
							<div class="controls ">
								<input class="treeRadio" type="text" value="" readonly="readonly" />
								<form:hidden path="fieldMap[Sort_Ids]" id="Sort_Ids" name="Sort_Ids" class="treeSelId" />
								<a class="menuBtn btn" href="#">选择</a>
							</div>
							<div class="menuContent">
								<ul id="treeSel_1" class="ztree treeNew"></ul>
								<a class="btn sel_all ml10">全选</a> <a class="btn sel_none">全不选</a>
								<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Summary">摘要</label>
							<div class="controls ">
								<form:textarea path="fieldMap[Summary]" class="span6 typeahead" id="Summary" name="Summary" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Data_Status">稿件状态</label>
							<div class="controls ">
								<input data-no-uniform="true" checked="true" type="checkbox" class="ios_toggle">
								<input id="Data_Status" class="ios_toggle_hidden" name="Data_Status" type="hidden" value="Normal" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Creator_ID">创建</label>
							<div class="controls ">
								<input class="input-small" id="Creator_ID" name="Creator_ID" type="text" />
								<span class="inline_label">创建时间</span>
								<input id="Create_Time" name="Update_Time" class="input-xlarge uneditable-input" type="text" disabled="true" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Sub_Title">修改</label>
							<div class="controls ">
								<input class="input-small" id="Updater_ID" name="Updater_ID" type="text" />
								<span class="inline_label">修改时间</span>
								<input id="Create_Time" name="Update_Time" class="input-xlarge uneditable-input" type="text" disabled="true" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Doc_Time">文档时间</label>
							<div class="controls ">
								<form:input path="fieldMap[Doc_Time]" class="input-big timepicker " id="Doc_Time" name="Doc_Time" placeholder="请选择文档时间" readonly="true" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Page_Num">版次</label>
							<div class="controls ">
								<form:input path="fieldMap[Page_Num]" class="input-small spinner" id="Page_Num" name="Page_Num" />
								<span class="inline_label">版名</span>
								<form:input path="fieldMap[Page_Name]" id="Page_Name" name="Page_Name" class="input-small" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Coords">热区</label>
							<div class="controls ">
								<form:input path="fieldMap[Coords]" class="span6 typeahead" id="Coords" name="Coords" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Style">样式</label>
							<div class="controls ">
								<form:input path="fieldMap[Style]" class="span6 typeahead" id="Style" name="Style" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Memo">备用</label>
							<div class="controls ">
								<form:input path="fieldMap[Memo]" class="span6 typeahead" id="Memo" name="Memo" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Issue">期号</label>
							<div class="controls ">
								<form:input path="fieldMap[Issue]" class="input-small spinner" id="Issue" name="Issue" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Gen_Issue">总期号</label>
							<div class="controls ">
								<form:input path="fieldMap[Gen_Issue]" class="input-small spinner" id="Gen_Issue" name="Gen_Issue" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Due_Num">届数</label>
							<div class="controls ">
								<form:input path="fieldMap[Due_Num]" class="input-small spinner" id="Due_Num" name="Due_Num" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Times">次数</label>
							<div class="controls ">
								<form:input path="fieldMap[Times]" class="input-small spinner" id="Times" name="Times" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Promulgate">颁布单位</label>
							<div class="controls ">
								<form:input path="fieldMap[Promulgate]" class="span6 typeahead" id="Promulgate" name="Promulgate" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Prom_Time">颁布日期</label>
							<div class="controls ">
								<form:input path="fieldMap[Prom_Time]" class="input-big datepicker" id="Prom_Time" name="Prom_Time" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Start_Time">生效日期</label>
							<div class="controls ">
								<form:input path="fieldMap[Start_Time]" class="input-big datepicker" id="Start_Time" name="Start_Time" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="End_Time">失效日期</label>
							<div class="controls ">
								<form:input path="fieldMap[End_Time]" class="input-big datepicker" id="End_Time" name="End_Time" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Leader">领导</label>
							<div class="controls ">
								<form:input path="fieldMap[Leader]" class="span3 typeahead" id="Leader" name="Leader" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Book_Name">书名</label>
							<div class="controls ">
								<form:input path="fieldMap[Book_Name]" class="span3 typeahead" id="Book_Name" name="Book_Name" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Foreign_Name">外文名称</label>
							<div class="controls ">
								<form:input path="fieldMap[Foreign_Name]" class="span3 typeahead" id="Foreign_Name" name="Foreign_Name" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Foreign_Short">外文缩写</label>
							<div class="controls ">
								<form:input path="fieldMap[Foreign_Short]" class="span3 typeahead" id="Foreign_Short" name="Foreign_Short" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Class">分类</label>
							<div class="controls ">
								<form:input path="fieldMap[Class]" class="span3 typeahead" id="Class" name="Class" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Type">类别</label>
							<div class="controls ">
								<form:input path="fieldMap[Type]" class="span3 typeahead" id="Type" name="Type" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Peoples">人物</label>
							<div class="controls ">
								<form:input path="fieldMap[Peoples]" class="span3 typeahead" id="Peoples" name="Peoples" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Places">地区</label>
							<div class="controls ">
								<form:input path="fieldMap[Places]" class="span3 typeahead" id="Places" name="Places" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Orgs">机构</label>
							<div class="controls ">
								<form:input path="fieldMap[Orgs]" class="span3 typeahead" id="Orgs" name="Orgs" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Hid_Words">隐含标引</label>
							<div class="controls ">
								<form:input path="fieldMap[Hid_Words]" class="span3 typeahead" id="Hid_Words" name="Hid_Words" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="New_Words">新词标引</label>
							<div class="controls ">
								<form:input path="fieldMap[New_Words]" class="span3 typeahead" id="New_Words" name="New_Words" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Year_Num">年度</label>
							<div class="controls ">
								<form:input path="fieldMap[Year_Num]" class="input-small spinner" id="Year_Num" name="Year_Num" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Conf_Num">会议次数</label>
							<div class="controls ">
								<form:input path="fieldMap[Conf_Num]" class="input-small spinner" id="Conf_Num" name="Conf_Num" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Secret_Level">保密级别</label>
							<div class="controls ">
								<form:select path="fieldMap[Secret_Level]"  id="Secret_Level" >
									<form:options items="${secretLevels}" itemLabel="title"  itemValue="value"/>
								</form:select>
							</div>
						</div>
				</div>	
				<div class="form-actions">
					<button type="submit" id="Article_sub" class="btn btn-primary">保存</button>
					<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
				</div>
			</fieldset>
		</form:form>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		if ($('#fieldsStr').val().indexOf('Attach') == -1)
			return;
		$('#Attach').uploadify({
			'height' : 20,
			'width' : 80,
			'removeCompleted' : false,
			'auto' : false,
			'buttonText' : '选择文件...',
			'swf' : '${appPath}/admin/js/uploadify.swf',
			'uploader' : '${appPath}/admin/upload/file?baseId=${dataBase.id}&uuid=${dataVo.uuid}&dateTime=${dataVo.createTime}',
			'cancelImg' : '${appPath}/admin/img/uploadify-cancel.png',
		});
	});
</script>
<script type="text/javascript" 	src="${appPath}/admin/jscript/data/data.js"></script>
</body>
</html>
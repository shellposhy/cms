/**
 * database general JavaScript
 * @author shishb
 * @see javascript
 * @version 1.0
 * */
$(document).ready(function() {
	// list
	init_directory_tree();
	bind_search();
	// edit
	init_lib_edit();
	bind_validate();
	init_display_fields(); 
});
var thisPath = appPath + "/admin/system/library/";

//the edit database tree list
function init_lib_edit() {
	if ($("#categories_tree_radio").length > 0) {
		var url = thisPath + "/directory/tree";
		if ($("#categories_tree_radio")[0]) {
			$.ajax({
				url : url,
				success : function(data) {
					treeRadioCom($("#categories_tree_radio .treeNew"),data.children, true);
					setTimeout("$('.treeSelId').click()", 800);
				}
			});
		}
	}
}

//init database tree 
function init_directory_tree() {
	if ($("#directoryTree").length > 0) {
		var url = thisPath + "tree";
		$.ajax({
			type : "POST",
			url : url,
			data : '[{"name":"id","value":""}]',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				if (data != null) {
					menuTreeCom($("#directoryTree"), data, true,node_click,"/admin/library/");
				}
			}
		});
	}
}

//bind the database tree click event
function node_click(nodeId, nodeType, isDir) {
	$("#libId").val(nodeId);
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", nodeId, null);
	$("#colname").html(treenode.name);
	if (isDir) {
		$('#search_u_db').hide();
		$('#search_u_db_btn').hide();
		$('#libraries').show();
		$('#data_content').hide();
		if (nodeType) {//directory
			find_by_parentId(nodeId);
		}else {//library
			find_by_parentId_init(nodeId);
		}
	}else {//not catalog
		$('#search_u_db').hide();
		$('#search_u_db_btn').hide();
		$('#libraries').hide();
		$('#data_content').show();
		find_data_by_libId(nodeId);
	}
}

//add database directory
function add_directory(selid) {
	var url = appPath + "/admin/system/library/directory/new/";
	if (selid > 0) {
		url += selid;
	} else {
		url += "0";
	}
	window.location.href = url;
}



//when the current database node has not database library,add two buttons
function find_by_parentId_init(parentId) {
	var libMenu="";
	libMenu+='<li id="add_library">';
	libMenu+=' 	<a href="'+ thisPath+ "directory/new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
	libMenu+='	 	<div class="actions"><a class="lh30 block  mt10" href="'+ 'javascript:add_directory('+ $("#libId").val()+ ')'+ '">添加目录</a></div>';
	libMenu+='</li>';
	libMenu+='<li id="add_lib">';
	libMenu+=' 	<a href="'+ thisPath+ "new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
	libMenu+='	 	<div class="actions"><a class="lh30 block db_repair mt10" href="'+ thisPath + 'new/' + $("#libId").val()+ '">添加数据库</a></div>';
	libMenu+='</li>';
	$("#libraries").html(libMenu);
}

//when the current database node is the parent node,loading the database node what the parent node is the current database node
function find_by_parentId(parentId) {
	$("#libId").val(parentId);
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", parentId, null);
	$("#colname").html(treenode.name);
	$.ajax({
		type : "GET",
		url : thisPath + "find/" + parentId,
		dataType : 'json',
		success : function(data) {
			$("#libraries").html("");
			if (data != null) {
				var data_node = data[0];
				var node_type = data_node.nodeType;
				if (node_type == "Lib") {
					var libMenu="";
					libMenu+='<li id="add_lib">';
					libMenu+=' <a href="'+ thisPath+ "new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
					libMenu+='	 <div class="actions"><a class="lh30 block db_repair mt10" href="'+ thisPath + 'new/'+ $("#libId").val()+ '">添加数据库</a></div>';
					libMenu+='</li>';
					$("#libraries").html(libMenu);
					for (var i = 0; i < data.length; i++) {
						var editLink = "";
						editLink += "<li id='dbv_"+ data[i].id+ "'>";
						editLink +=	"	<a class='' target='_blank' href='javascript:init_library("+ data[i].id+ ",false)' target='_self' ><div class='dbimg'></div></a>";
						editLink += "	<span class='dbname'><i class='dbname'>"+ data[i].name+ "</i></span>";
						editLink += "	<span class='dbtime'>更新时间：<br />"+ data[i].dataUpdateTimeStr + "</span>";
						editLink += "	<div class='actions' >";
						editLink +="		<a title='修改数据库' class='btn btn-small db_edit pop_link cboxElement' href='"+ thisPath+ "edit/"+ data[i].id+ "' target='_self'><i class='icon-pencil'></i></a>";
						editLink += "		<a title='删除数据库' class='btn btn-small ml3 db_del' href='#' onclick='delete_lib("+ data[i].id+ ")' target='_self'><i class='icon-trash'></i></a>";
						editLink += "		<a class='lh30 block db_repair mt10' href='#' onclick='repair_lib("+ data[i].id+ ")' target='_self'>修复数据库</a>";
						editLink += "	</div>";
						editLink += "	<div class='progress progress-striped progress-success active none'><div class='bar'></div></div>";
						editLink += "</li>";
						$("#libraries").append(editLink);
						if (data[i].status == "Repairing") {
							repair_pregress(data[i].id, data[i].taskId);
						}
					}
				} else if (node_type == "Directory") {
					var libMenu="";
					libMenu+='<li id="add_library">';
					libMenu+='		<a href="'+ thisPath+ "directory/new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
					libMenu+='		<div class="actions"><a class="lh30 block  mt10" href="'+ 'javascript:add_directory('+ $("#libId").val() + ')'+ '">添加目录</a></div>';
					libMenu+='</li>';
					$("#libraries").html(libMenu);
					for (var i = 0; i < data.length; i++) {
						var editLink = "";
						editLink += "<li id='dbv_"+ data[i].id+ "'>";
						editLink += "	<a class='' target='_blank' href='"+ "javascript:init_library("+ data[i].id+ ",true"+ ")"+ "' target='_self' ><div class='dbimg'></div></a>";
						editLink += "	<span class='dbname'><i class='dbname'>"+ data[i].name + "</i></span>";
						editLink += "	<div class='actions' >";
						editLink += "		<a title='修改目录' class='btn btn-small db_edit pop_link cboxElement' href='"+ thisPath+ "directory/"+ data[i].id+ "/edit"+ "' target='_self'><i class='icon-pencil'></i></a>";
						editLink += "		<a title='删除目录' class='btn btn-small ml3 db_del' href='#' onclick='delete_library("+ data[i].id+ ")' target='_self'><i class='icon-trash'></i></a>";
						editLink += "	</div>";
						editLink += "	<div class='progress progress-striped progress-success active none'><div class='bar'></div></div>";
						editLink += "</li>";
						$("#libraries").append(editLink);
					}
				}
				$('#search_u_db').show();
				$('#search_u_db_btn').show();
			}
		}
	});
}

//when the current database node is the library,loading the data
function find_data_by_libId(libId) {
	$("#libId").val(libId);
	$('#search_u_db').hide();
	$('#search_u_db_btn').hide();
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", libId, null);
	$("#colname").html(treenode.name);
	$('#into_as_search').attr("href",appPath + '/admin/data/as');
	$('#colDatas thead tr th, #colDatas tfoot tr th').remove();
	$('#colDatas thead tr').append('<th><label class="checkbox inline"><input type="checkbox" class="selAll" />标题</label></th>');
	$('#colDatas tfoot tr').append('<th>标题</th>');
	var headTitle = [{
		"mData" : "title",
		"fnRender" : function(obj) {
			var sumImg ='',attach ='';
			if (obj.aData.img) sumImg = '<div class="sum_img_div"><img class="list_sum_img" src="'+ obj.aData.img + '"/></div>';
			if (obj.aData.attach) attach = '<span class="icon icon-blue icon-attachment"></span>';
			return '<h3>'
						+'	<label class="checkbox inline mt0">'
						+'		<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '_'+ obj.aData.tableId+ '" style="opacity: 0;">'
						+ '	</label>'
						+'	<a class="data_title edit_pop_link" href="'+ thisPath+ 'data/edit/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '" target="_blank">'+ obj.aData.title+ '</a>'
						+ '	<a class="padmbt btn floatr none edit_pop_link" href = "'+ thisPath+ 'data/info/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '" target="_blank"><i class="icon-eye-open" title="稿件预览"></i></a>'
					+ '</h3>'
					+'<p class="summary clearfix" >'+ sumImg+ obj.aData.summary + attach + '</p>';
		}
	} ];
	var add_data_url = appPath + "/admin/system/library/data/new/" + libId;
	$('#add_to_dsu').attr('href', add_data_url);
	$.ajax({
		url : appPath + "/admin/system/library/data/tablehead/" + libId,
		async : false,
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				var mData = {"mData" : data[i].codeName};
				headTitle.push(mData);
				$('#colDatas thead tr,#colDatas tfoot tr').append("<th>" + data[i].name + "</th>");
			}
		}
	});
	dataTablesCom($('#colDatas'), "/admin/system/library/data/search/" + libId,headTitle, null, callback_library_data, true);
}

//datatable callback function
function callback_library_data(otd) {
	docReady();
	trHoverEdit();
	$(".action_buttons").show();
	listDelete(thisPath + "data/delete", otd);
	library_data_copy(otd);
	editPopWithDT(otd);
}

//the current database data copy to other database
function library_data_copy(dataTable){
	var dataIds;
	$("#data_copy").die().bind('click',function(){
		var findChecks = $(this).parent().nextAll(".dataTables_wrapper");
		if (findChecks.length < 1) {
			findChecks = $(this).parent().parent().nextAll(".dataTables_wrapper");
		}
		if (findChecks.find("table input[type='checkbox']").length > 0){
			var count = 0;
			var idsValue = new Array();
			findChecks.find("table tbody input[type='checkbox']").each(function() {
				if ($(this).attr("checked")&& $(this).val() != null&& $(this).val().length > 0) {
					idsValue.push($(this).val());
					count++;
				}
			});
			dataIds = idsValue.join(",");
			if (count > 0){
				$.ajax({
					type : "POST",
					url : thisPath+"data/copy/tree/"+$("#libId").val(),
					dataType : 'json',
					contentType : 'application/json',
					success : function(data) {
						var optionStr = "";
						if(null!=data&&data.length>0){
							for(var i=0;i<data.length;i++){
								optionStr+='<input type="checkbox" name="baseIds" value="'+ data[i].id+'">'+ data[i].name+'&nbsp;';
							}
						}
						$("#copyBaseList").html(optionStr);
					}
				});
				$("#copyDataModal").modal('show');
			}else{
				noty({"text" : "数据迁移时，所选择数据不能为空","layout" : "center","type" : "error"});
			}
		}
	});
	$("#copyDataModal").find('.btn-primary').click(function(){
		//catch the selected database id
		var dbIds;
		$('input:checkbox[name=baseIds]:checked').each(function(i) {
			if (0 == i) {
				dbIds = $(this).val();
			} else {
				dbIds += ("," + $(this).val());
			}
		});
		if(typeof(dbIds)=="undefined" || dbIds==null || dbIds==''){
			noty({"text" : "数据迁移时，迁到的数据库不能为空","layout" : "center","type" : "error"});
		}else{
			var jsonData = new Array();
			jsonData.push({"name" : "mSearch","value" : dataIds}, {"name" : "searchIdStr","value":dbIds});
			$.ajax({
				type : "POST",
				url : thisPath+"data/copy/0/"+$("#libId").val(),
				data : JSON.stringify(jsonData),
				dataType : 'json',
				contentType : 'application/json',
				success : function(data) {
					
				}
			});
		}
	})
}

//bind database node
function init_library(parentId, isDir) {
	var treeObj = $.fn.zTree.getZTreeObj("directoryTree");
	var treenode = treeObj.getNodeByParam("id", parentId, null);
	treeObj.expandNode(treenode, true, false, false);
	treeObj.selectNode(treenode);
	$("#colname").html(treenode.name);
	var nodeType = treenode.isParent;
	$("#libId").val(parentId);
	if (isDir == true) {
		$('#libraries').show();
		$('#data_content').hide();
		if (nodeType) {
			find_by_parentId(parentId);
		} else {
			find_by_parentId_init(parentId);
		}
	} else {
		$('#libraries').hide();
		$('#data_content').show();
		find_data_by_libId(parentId);
	}
}

//delete directory
function delete_library(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : thisPath + "directory/" + id + "/delete",
			type : 'POST',
			success : function(response) {
				$('#dbv_' + id).remove();
			}
		});
	} else
		return false;
}

//delete library
function delete_lib(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : thisPath + "delete/" + id,
			type : 'POST',
			success : function(response) {
				$('#dbv_' + id).remove();
			}
		});
	} else
		return false;
}


//database node search
function bind_search() {
	$("#search_u_db_btn").click(function() {
		if ($("#search_u_db").val()){
			$("#categoryTree .curSelectedNode").removeClass("curSelectedNode");
			var sdata = '[{"name":"sSearch","value":"'+ $("#search_u_db").val() + '"}]';
			$.ajax({
				type : "post",
				url : thisPath + "/search",
				dataType : 'json',
				contentType : 'application/json',
				data : sdata,
				success : function(data) {
					if (data.length != 0) {
						var libMenu="";
						libMenu+='<li id="add_lib">';
						libMenu+='		<a href="'+ thisPath+ "new/"+ $("#libId").val()+ '"><div class="addimg_db"></div></a>';
						libMenu+='		<div class="actions"><a class="lh30 block db_repair mt10" href="'+ thisPath+ 'new/'+ $("#libId").val()+ '">添加数据库</a></div>';
						libMenu+='</li>';
						$("#libraries").html(libMenu);
						for (var i = 0; i < data.length; i++) {
							var editLink = "";
							editLink += "<li id='dbv_"+ data[i].id+ "'>";
							editLink += "	<a class='' target='_blank' href='"+ thisPath+ 'data/'+ data[i].id+ "' target='_self' ><div class='dbimg'></div></a>";
							editLink += "	<span class='dbname'><i class='dbname'>"+ data[i].name+ "</i></span>";
							editLink += "	<span class='dbtime'>更新时间：<br />"+ data[i].dataUpdateTimeStr+ "</span>";
							editLink += "	<div class='actions' >";
							editLink += "		<a class='btn btn-small db_edit' href='"+ thisPath+ "edit/"+ data[i].id+ "' target='_self'><i class='icon-pencil'></i>修改</a>";
							editLink += "		<a class='btn btn-small ml5 db_del' href='#' onclick='delete_lib("+ data[i].id+ ")' target='_self'><i class='icon-trash'></i>删除</a>";
							editLink += "		<a class='lh30 block db_repair mt10' href='#' onclick='repair_lib("+ data[i].id+ ")' target='_self'>修复数据库</a>";
							editLink += "	</div>";
							editLink += "	<div class='progress progress-striped progress-success active none'><div class='bar'></div></div>";
							editLink += "</li>";
							$("#libraries").append(editLink);
							if (data[i].status == "Repairing") {
								repair_pregress(data[i].id,data[i].taskId);
							}
						}
					} else {
						$("#libraries").html("<h3 class='alert alert-info' >无匹配的搜索结果！</h3>");
					}
				}
			});
		} else {
			if ($("#libraries .alert-info:contains(请选择)").length) {
				$("#search_u_db").focus();
			} else {
				$("#directoryTree .curSelectedNode").removeClass("curSelectedNode");
				$("#libraries").html("<h3 class='alert alert-info'>请选择左侧分类以查看数据库</h3>");
			}
		}
	});
	$("#search_u_db").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#search_u_db_btn").trigger("click");
			$(this).blur();
		} else {
			return false;
		}
	});
}

//repair the database index
function repair_lib(id) {
	function repair(id) {
		$.ajax({
			url : thisPath + "data/repair/" + id,
			type : 'POST',
			success : function(data) {
				var thisact = $('#dbv_' + id).find(".actions");
				thisact.hide();
				var thispro = $('#dbv_' + id).find(".progress");
				var thisbar = $('#dbv_' + id).find(".progress .bar");
				thispro.show();
				thisbar.css("width", "0%");
				var oShowProgress = function showProgress() {
					$.ajax({
						type : "GET",
						url : appPath + "/admin/task/progress/" + data,
						cache : false,
						dataType : "json",
						success : function(data) {
							var barpres = data.progress + "%";
							if (data.progress < 100) {
								thisbar.css("width", barpres);
							} else if (data.progress == 100) {
								thisbar.css("width", "100%");
								thispro.hide();
								thisact.show();
								clearInterval(intInterval);
								noty({"text" : "修复成功！","layout" : "center","type" : "success","animateOpen" : {"opacity" : "show"}});
							}
						}
					});
				};
				var intInterval=setInterval(oShowProgress, 5000);
			}
		});
	}
	comConfirmModel(repair, id, "确定修复", "修复将耗时较长，确定修复?");
}

//catch the repairing database processing
function repair_pregress(id, data) {
	var thisact = $('#dbv_' + id).find(".actions");
	thisact.hide();
	var thispro = $('#dbv_' + id).find(".progress");
	var thisbar = $('#dbv_' + id).find(".progress .bar");
	thispro.show();
	thisbar.css("width", "0%");
	var oShowProgress = function showProgress() {
		$.ajax({
			type : "GET",
			url : appPath + "/admin/task/progress/" + data,
			cache : false,
			dataType : "json",
			success : function(data) {
				var barpres = data.progress + "%";
				if (data.progress < 100) {
					thisbar.css("width", barpres);
				} else if (data.progress == 100) {
					thisbar.css("width", "100%");
					thispro.hide();
					thisact.show();
					clearInterval(intIntervalPre);
					noty({"text" : "修复成功！","layout" : "center","type" : "success","animateOpen" : {"opacity" : "show"}});
				}
			}
		});
	};
	var intIntervalPre=setInterval(oShowProgress, 5000);
}

//validate the form
function bind_validate() {
	$("#db_new_form").validate({
		ignore : "",
		rules : {
			name : "required",
			code : "required",
			parentID : "required",
			moreDataFieldsStr : {
				required : function() {
					if ($("#moreFields").parents(".feilds_form_box").hasClass("hiddened")) {
						return false;
					} else {
						return true;
					}
				}
			}
		},
		messages : {
			name : "请填写数据库名称",
			code : "请填写数据库编号",
			moreDataFieldsStr : "请自定义一组字段",
			parentID : "请选择数据库所属分类"
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element);
		},
		submitHandler : function() {
			form.submit();
		},
		onkeyup : false
	});
}

//The showing database fields before the database created
function init_display_fields() {
	var columnModelId = $("#db_columnModelId").val();
	var dataFieldIdStr = $('#dataFieldsStr').val();
	var url = thisPath + "displayFields/" + columnModelId;
	var fieldsIds = new Array();
	if (null != dataFieldIdStr && "" != dataFieldIdStr) {
		fieldsIds = dataFieldIdStr.split(',');
	}
	if (null != columnModelId && "" != columnModelId) {
		$.ajax({
			type : "GET",
			url : url,
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				var temCheckCon = "";
				var dataResult = data;
				var dataSize = data.length;
				if (dataSize == fieldsIds.length&& fieldsIds.length == 0) {
					for (var i = 0; i < dataSize; i++) {
						temCheckCon += "<label><input  type='checkbox'   onclick='changeChkVal()'   rel='Reason' name='chk'   value='"+ dataResult[i].id+ "'  >"+ dataResult[i].name + "</label> ";
					}
				} else if (dataSize == fieldsIds.length&& fieldsIds.length > 0) {
					for (var i = 0; i < dataSize; i++) {
						temCheckCon += "<label><input  type='checkbox'   onclick='changeChkVal()'  checked  rel='Reason' name='chk'   value='"+ dataResult[i].id+ "'  >"+ dataResult[i].name + "</label> ";
					}
				} else {
					for (var i = 0; i < dataSize; i++) {
						var dataFd = dataResult[i].id;
						var flag = false;
						for (var j = 0; j < fieldsIds.length; j++) {
							if (dataFd == fieldsIds[j]) {
								flag = true;
								break;
							}
						}
						if (flag == false) {
							temCheckCon += "<label><input  type='checkbox' onclick='changeChkVal()'  rel='Reason' name='chk'   value='"+ dataResult[i].id+ "'  >"+ dataResult[i].name + "</label> ";
						} else {
							temCheckCon += "<label><input  type='checkbox' onclick='changeChkVal()'    rel='Reason' name='chk'  value='"+ dataResult[i].id+ "'   checked >"+ dataResult[i].name + "</label> ";
						}
					}
				}
				$('#columnModelFileds').html("");
				temCheckCon+='<label><div class="checker" id="uniform-undefined"><span class="checked"><input type="checkbox" onclick="changeChkVal()" rel="Reason" name="chk" value="16" style="opacity: 0;"></span></div>文档时间</label>';
				$('#columnModelFileds').append(temCheckCon);
				var fieldIds = "";
				var checkVals = $('#columnModelFileds :checkbox:checked');
				for (var i = 0; i < checkVals.length; i++) {
					fieldIds += checkVals[i].value + ",";
				}
				$("input:checkbox").uniform();
				fieldIds = fieldIds.substring(0, fieldIds.length - 1);
				$('#dataFieldsStr').val(fieldIds);
			}
		});
	}
}

//the change database field listener
function changeChkVal() {
	var checkElem = document.getElementsByName("chk");
	var temVal = "";
	for (var i = 0; i < checkElem.length; i++) {
		if (checkElem[i].checked) {
			temVal += checkElem[i].value + ",";
		}
	}
	temVal = temVal.substring(0, temVal.length - 1);
	$('#dataFieldsStr').val(temVal);
}
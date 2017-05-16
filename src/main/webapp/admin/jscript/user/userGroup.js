/**
 * 用户组(角色)通用脚本处理
 * @author shishb
 * @version 1.0
 * */
$(document).ready(function() {
	user_group_tb();
	user_group_header();
	userGroup_add();
});

// 定义公共参数
var dataTableUserGroup;
//表格初始化
function user_group_tb() {
	dataTableUserGroup = $('#user_group').dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "bootstrap",
		"bProcessing" : true,
		"bServerSide" : true,
		"bSort" : false,
		"sAjaxSource" : "userGroup/s",
		"fnServerData" : retrieveData,
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
		"aoColumns" : [
				{
					"mData" : "name",
					"fnRender" : function(obj) {
						return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id
								+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name
								+ '</label><button title="点击进行配置" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i></button>';
					}
				},
				{"mData" : "memo"},
				{"mData" : "userStr"},
				{
					"mData" : "id",
					"fnRender" : function(obj) {
						return '<a href="#">查看</a>';
					}
				} ],
		"fnDrawCallback" : callback_load_DataTalbe
	});
}

//dataTable回调函数处理
function callback_load_DataTalbe() {
	docReady();
	trHoverEdit();
	listDelete("userGroup/delete", dataTableUserGroup);
}

//用户组表格表头设置
function user_group_header() {
	var headTitle = [{"mData" : "name"}, {"mData" : "realName"}, {"mData" : "groupNames"}, {"mData" : "userTypeName"}];
	dataTablesCom($('#user_group_info'), "/admin/userGroup/"+ $("#groupId").val() + "/s", headTitle, null,callback_pop_top, true);
}

//dataTable弹出回调函数
function callback_pop_top(otd) {
	var popBtns = [ {obj : $("#unAddUserList_btn"),url : $("#unAddUserList_btn").attr("href")} ];
	editPopWithDT(otd, popBtns);
}

//新增角色
function userGroup_add(){
	if ($("#group_new_form").length > 0) {
		userGroup_validate();
		userGroup_admin_authority();
		usergroup_tree_menu();
		userGroup_page_deault();
	}
}

/* 提交用户组 表单验证 */
function userGroup_validate() {
	$("#group_new_form").validate({
		rules : {
			name : "required",
			code : "required"
		},
		messages : {
			name : "请填写用户组名称",
			code : "请填写用户组编号"
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

//后台管理权限切换
function userGroup_admin_authority() {
	if ($("input[name='allAdminAuthority']").val() == "false") {
		$(".notAllAuthor").addClass("disabled").siblings(".allAuthor").removeClass("disabled").end().siblings(".treeSel,.menuBtn").show();
	} else {
		$(".allAuthor").addClass("disabled").siblings(".notAllAuthor").removeClass("disabled").end().siblings(".treeSel,.menuBtn").hide();
	}
	$(".allAuthor").click(
			function() {
				if (!$(this).hasClass("disabled")) {
					$(this).addClass("disabled").siblings(".notAllAuthor").removeClass("disabled");
					$(this).siblings(".treeSel,.menuBtn").hide();
					$("input[name='allAdminAuthority']").attr("value", true);
				}
				return false;
			});
	$(".notAllAuthor").click(
			function() {
				if (!$(this).hasClass("disabled")) {
					$(this).addClass("disabled").siblings(".allAuthor").removeClass("disabled");
					$(this).siblings(".treeSel,.menuBtn").show();
					$("input[name='allAdminAuthority']").attr("value", false);
				}
				return false;
			});
}

//加载后台权限树
function usergroup_tree_menu() {
	var data = JSON.parse($('#actionTree').val());
	treeSleCom($("#use_group_tree_sel .treeNew"), data);
	var delayRsel = setTimeout("$('#use_group_tree_sel .treeSelId').click()",800);
}

//系统默认首页处理
function userGroup_page_deault() {
	var initTypeVal = $("#defaultPageType").val();
	if (initTypeVal == null || initTypeVal == "" || initTypeVal == "SysPage") {
		$("#defaultPageUrl").val("");
		$('#para').val("SysPage");
		$("#defaultPageType").val("SysPage");
		$("#defaultPageSel_Area").show();
		$("#defaultPageUrl_area").hide();
	} else if (initTypeVal == "UserPage") {
		$('#para').val("UserPage");
		$("#defaultPageID").val("");
		$("#pageSel").val("");
		$("#defaultPageSel_Area").hide();
		$("#defaultPageUrl_area").show();
	}
	
	$("#para").change(function() {
		var defaultPageTypeVal = $('#para').val();
		$("#defaultPageType").val(defaultPageTypeVal);
		if (defaultPageTypeVal == "UserPage") {
			$("#defaultPageSel_Area").hide();
			$("#defaultPageUrl_area").show();
			$("#defaultPageID").val("");
		} else if (defaultPageTypeVal == "SysPage") {
			$("#defaultPageUrl").val("");
			$("#defaultPageUrl_area").hide();
			$("#defaultPageSel_Area").show();
		}

	});
}
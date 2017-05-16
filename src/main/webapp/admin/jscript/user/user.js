$(document).ready(function() {
	init_global_org();
	user_init();
	upload_user();
});


// 初始化用户机构
function init_global_org() {
	if ($("#org_user_list").length > 0) {
		$("#org_user_list").hide();
		var url = appPath+ "/admin/org/s";
		$.ajax({
			type : "POST",
			url : url,
			contentType : 'application/json',
			success : function(data) {
				if (data.children != null) {
					menuTreeCom($("#userOrg"), data, true, user_org_tree_link, "org/",null, null, null, null);
				} else {
					$("#userOrg").html("<h3 class='alert alert-info' >暂无机构</h3>");
				}
			},
			error : function(msg) {
				$("#userOrg").html("<h3 class='alert alert-info' >暂无机构</h3>");
			}
		});
	}
}
//点击树形节点
function user_org_tree_link(id) {
	// 检索机构id检索当前机构用户
	if ($("#org_user_list .dataTable").length > 0) {
		oTableUserAll.fnDestroy();
		$(".selAll").parents("th").html('<label class="checkbox inline"><input type="checkbox" class="selAll" />用户名</label>')
	}
	user_init_list(id);
	$("#org_user_list").show().find(".dataTable").attr("style", "width:100%");
	$("#orgUserEdit").html("<a class='btn btn-small add_user'" + "  href='"+ appPath+ "/admin/user/"+ id+ "/new' 	target='_self'>"
			+ "<i class='icon-plus'></i> 添加</a> <a	class='btn btn-small delete_list' href='#'>"+ "<i	class='icon-trash'></i> 删除</a>");
}


function user_init() { // 用户编辑页面部分
	if ($("#user_new_form").length > 0) {
		init_user_edit();
		init_group_list();
		userTypeChange();
		userStatus();
		isModifyPw();
		addNewUser();
		intOrgUserEidt();
	}
}


//定义公共参数
var oTableUserAll;
function user_init_list(orgId) {
	oTableUserAll = $('#user').dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "bootstrap",
		"bProcessing" : true,
		"bServerSide" : true,
		"bSort" : false,
		"sAjaxSource" : "user/" + orgId + "/s",
		"fnServerData" : retrieveData,
		"fnServerParams" : function(aoData) {aoData.push({"name" : "iType","value" : 0});},
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
		"aoColumns" : [{
					"mData" : "name",
					"fnRender" : function(obj) {
						if (obj.aData.name == "sa") {
							return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'
									+ obj.aData.id+ '" name="idStr' + obj.aData.id+ '" value="'+ obj.aData.id
									+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>';
							}
						return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'
								+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>'
								+ '<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
					}
				}, {
					"mData" : "realName"
				}, {
					"mData" : "groupNames"
				}, {
					"mData" : "userTypeName"
				} ],
		"fnDrawCallback" : callback_user_list
	});
};

// 表格回调--所有用户
function callback_user_list() {
	docReady();
	trHoverEdit();
	trHoverBtn("wrenchbtn", "icon-wrench", "repair");
	listDelete("user/delete", oTableUserAll);
};

// 用户编辑 初始化修改页面
function init_user_edit() {
	if ($("#user_new_form #name").val()) {
		userTypeChangeBind();
		if ($("#ips").val()) { // 回显IP
			var ipStr = $("#ips").val();
			var ipArr = ipStr.split(",");
			var ipsInputDivs = "";
			for ( var i = 0; i < ipArr.length; i++) {
				var btn = ' <span class="btn remove mr4"><i class="icon-trash icon-color"></i></span>';
				if (ipArr[i].indexOf("-") >= 0) {
					var ipArrMut = ipArr[i].split("-");
					var ipsInputDiv = '<div class="ip_input_div">'
							+ '<span class="one_ip none"><input type="text" /></span>'
							+ '<span class="mut_ip active"><input class="ipVaild" type="text" value="'
							+ ipArrMut[0]
							+ '" /> — <input class="ipVaild" type="text" value="'
							+ ipArrMut[1]
							+ '" /></span>'
							+ btn
							+ '<div class="mb10"><a class="to_mut_ip none" href="#">点此切换至输入IP范围</a><a class="to_one_ip" href="#">点此切换至输入精确IP</a></div>'
							+ '</div>';
				} else {
					var ipsInputDiv = '<div class="ip_input_div">'
							+ '<span class="one_ip active"><input class="ipVaild" type="text" value="'
							+ ipArr[i]
							+ '" /></span>'
							+ '<span class="mut_ip none"><input type="text" /> — <input type="text" /></span>'
							+ btn
							+ '<div class="mb10"><a class="to_mut_ip" href="#">点此切换至输入IP范围</a><a class="to_one_ip none" href="#">点此切换至输入精确IP</a></div>'
							+ '</div>';
				}
				ipsInputDivs += ipsInputDiv;
			}
			$("#ips_div .ip_input_div").replaceWith(ipsInputDivs);
		}

	}
}

// 用户编辑 选择用户类型
function userTypeChange() {
	$("#userType input").change(function() {
		userTypeChangeBind();
	});
	ipsInput();
}

function userTypeChangeBind() {
	var userType = $('input[name = "userType"]:checked').val();
	if (userType == 0) {
		$("#psword").show().addClass("active");
		$("#ip").hide().removeClass("active");
	} else if (userType == 1) {
		$("#ip").show().addClass("active");
		$("#psword").hide().removeClass("active");
	} else if (userType == 2) {
		$("#ip").show().addClass("active");
		$("#psword").show().addClass("active");
	} else {
		$("#psword").hide().removeClass("active");
		$("#ip").hide().removeClass("active");
	}
	if (userType == 3) {
		$('#editUserOrg').before($('#phone_div'));
	} else {
		$('#position_div').after($('#phone_div'));
	}
}

// 用户编辑 ip输入框控制
function ipsInput() {
	$("#more_ip").live("click",function() {
		$(this).before('<div class="ip_input_div">'
		+ '<span class="one_ip active"><input class="ipVaild" type="text" /></span>'
		+ '<span class="mut_ip none"><input type="text" /> — <input type="text" /></span>'
		+ ' <span class="btn remove mr4"><i class="icon-trash icon-color"></i></span>'
		+ '<div class="mb10"><a class="to_mut_ip" href="#">点此切换至输入IP范围</a><a class="to_one_ip none" href="#">点此切换至输入精确IP</a></div>'
		+ '</div>');
	})
	$(".ip_input_div .remove").live("click", function() {
		$(this).parents(".ip_input_div").remove();
		$("#ips_div .active input").change();
	})
	$(".to_mut_ip").live(
			"click",
			function() {
				$(this).hide().siblings(".to_one_ip").show();
				$(this).parents(".ip_input_div").find(".one_ip").hide()
						.removeClass("active").find("input").removeClass(
								"ipVaild");
				$(this).parents(".ip_input_div").find(".mut_ip").show()
						.addClass("active").find("input").addClass("ipVaild");
				return false;
			})
	$(".to_one_ip").live(
			"click",
			function() {
				$(this).hide().siblings(".to_mut_ip").show();
				$(this).parents(".ip_input_div").find(".mut_ip").hide()
						.removeClass("active").find("input").removeClass(
								"ipVaild");
				$(this).parents(".ip_input_div").find(".one_ip").show()
						.addClass("active").find("input").addClass("ipVaild");
				return false;
			})

	$("#ips_div .active input").live(
			"change",
			function() {
				var count = 0;
				var ipsVal = new Array();
				$("#ips_div .active").each(
						function() {
							if ($(this).find("input").length == 1) {
								ipsVal.push($(this).find("input").val());
								count++;
							} else if ($(this).find("input").length == 2) {
								ipsVal.push($(this).find("input:first").val()
										+ "-"
										+ $(this).find("input:last").val());
								count++;
							}
						});
				if (count > 0) {
					var ipsData = ipsVal.join(",");
					$("#ips").val(ipsData)
				}

			})
}

// 用户编辑 状态开关
function userStatus() {
	if ($("#user_new_form #name").val()) {
		if ($("#user_new_form #status").val() == "Normal") {
			$("#status_check").attr("checked", true);
		} else if ($("#user_new_form #status").val() == "Stop") {
			$("#status_check").attr("checked", false);
		}
	} else {
		$("#user_new_form #status").val("Normal");
	}
	$('.iphone_toggle_user').iphoneStyle({
		checkedLabel : '启用',
		uncheckedLabel : '禁用',
		onChange : function() {
			var userSval = $("#user_new_form #status").val();
			if (userSval == "Normal") {
				$("#user_new_form #status").val("Stop");
			} else if (userSval == "Stop") {
				$("#user_new_form #status").val("Normal");
			}
		}
	});
}

// 加载多选的用户组option
function init_group_list() {
	if ($("#user_new_form #name").val()) {
		if ($("#treeSelId").val()) {
			var trSIdArry = ($("#treeSelId").val()).split(",");
		}
		if ($("#groupListJson").text()) {
			var groupListJson = $("#groupListJson").text();
			var groupListJsonO = new Function("return" + groupListJson)();
			var tmpOptions = "";
			for ( var i = 0; i < groupListJsonO.length; i++) {
				if ($("#treeSelId").val()) {
					var tmpOptionsTrue = "";
					for ( var j = 0; j < trSIdArry.length; j++) {
						if (trSIdArry[j] == groupListJsonO[i].id) {
							tmpOptionsTrue = "<option  selected='true' value='"
									+ groupListJsonO[i].id + "'>"
									+ groupListJsonO[i].name + "</option>";
							break;
						}
					}
					if (tmpOptionsTrue != "") {
						tmpOptions += tmpOptionsTrue;
					} else {
						tmpOptions += "<option  value='" + groupListJsonO[i].id
								+ "'>" + groupListJsonO[i].name + "</option>";
					}
				} else {
					tmpOptions += "<option value='" + groupListJsonO[i].id
							+ "'>" + groupListJsonO[i].name + "</option>";
				}
			}
			$("#user_new_form #treeSelId_box").append(tmpOptions);
		}
	} else {
		if ($("#groupListJson").text()) {
			var groupListJson = $("#groupListJson").text();
			var groupListJsonO = new Function("return" + groupListJson)();
			var temOptions = "";
			for ( var i = 0; i < groupListJsonO.length; i++) {
				tmpOptions += "<option value='" + groupListJsonO[i].id + "'>"
						+ groupListJsonO[i].name + "</option>";
			}
			$("#user_new_form #treeSelId_box").html(tmpOptions);
		}
	}
	var treeSelIds = $("#user_new_form #treeSelId_box").val();
	$("#treeSelId").val(treeSelIds);
	$("#user_new_form #treeSelId_box").change(function() {
		var treeSelIdsn = $("#user_new_form #treeSelId_box").val();
		$("#treeSelId").val(treeSelIdsn);
	});
}

// 密码用户是否修改密码
function isModifyPw() {
	if (($("#user_new_form #name").val())
			&& ($('input[name = "userType"]:checked').val() != 1)) {
		$("#user_new_form input[type='password']").parent().parent(
				".control-group").addClass("none");
		$("#user_new_form #isModifyPw_box a").each(
				function() {
					if ($(this).hasClass("yes")) {
						$(this).live(
								"click",
								function() {
									$(this).hide();
									$("#user_new_form input[type='password']")
											.parent().parent(".control-group")
											.removeClass("none");
									$(".cancel").show();
								});
					} else {
						$(this).live(
								"click",
								function() {
									$(this).hide();
									$("#user_new_form input[type='password']")
											.parent().parent(".control-group")
											.addClass("none");
									$(".yes").show();
								});
					}
				});
	} else {
		$("#isModifyPw_box").remove();
	}
}

/* 提交用户 表单验证 */
function addNewUser() {
	// IP地址验证
	jQuery.validator
			.addMethod(
					"ip",
					function(value, element) {
						var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
						return this.optional(element)
								|| (ip.test(value) && (RegExp.$1 < 256
										&& RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
					}, "Ip地址格式错误");
	$.validator
			.addMethod("ipRequired", $.validator.methods.required, "请输入IP地址");
	jQuery.validator.addMethod("pwCheck", function(value, element) {
		return this.optional(element) || /^[^\s]{6,20}$/.test(value);
	}, "密码中不能含有特殊字符");
	jQuery.validator.addClassRules("ipVaild", {
		ipRequired : function() {
			if ($("#user_new_form #ip").hasClass("active")) {
				return true;
			} else {
				return false;
			}
		},
		ip : true
	});
	$("#user_new_form").validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				specialCharValidate : true
			},
			password : {
				required : function() {
					if ($("#user_new_form #psword").hasClass("active")) {
						return true;
					} else {
						return false;
					}
				},
				minlength : 6,
				pwCheck : true

			},
			repassword : {
				required : function() {
					if ($("#user_new_form #psword").hasClass("active")) {
						return true;
					} else {
						return false;
					}
				},
				minlength : 6,
				equalTo : '#password',
				pwCheck : true
			},
			phonenumber : {
				required : function() {
					if ($("#user_new_form #phoneNumber").hasClass("active")) {
						return true;
					} else {
						return false;
					}
				},
				minlength : 6,
				equalTo : '#password',
				pwCheck : true
			}
		},
		messages : {
			name : {
				required : "请输入昵称",
				specialCharValidate : "昵称中不能有特殊字符"
			},
			password : {
				required : "请输入密码",
				minlength : "请最少输入6位密码",
				pwCheck : "密码中不能含有特殊字符"
			},
			repassword : {
				required : "请再次输入密码",
				minlength : "请最少输入6位密码",
				equalTo : "请保证两次输入密码一致",
				pwCheck : "密码中不能含有特殊字符"

			}
		},
		errorPlacement : function(error, element) {
			if (element.is(".ipVaild")) {
				error.insertAfter("div.error");
			} else {
				error.insertAfter(element);
			}
		},
		submitHandler : function() {
			form.submit();
		}
	});
}

// 用户批量导入
function upload_user() {
	var error_div = $("#inputUsers_form .error_div");
	$("#inputUsers_form").validate({
		rules : {
			file : "required"
		},
		messages : {
			file : "请选择要上传的数据包文件"
		},
		errorLabelContainer : error_div,
		submitHandler : function() {
			$("#inputUsers_form").ajaxSubmit();
			noty({
				"text" : "上传完毕",
				"layout" : "center",
				"type" : "alert",
				"animateOpen" : {
					"opacity" : "show"
				}
			});
			return false;
		},
		onkeyup : false
	});

}

// 用户所在机构的选择项初始化
function intOrgUserEidt() {
	var tmpHPage = document.location.href.split("/");
	var url = appPath+ "/admin/org/s";
	$.ajax({
		type : "POST",
		url : url,
		contentType : 'application/json',
		success : function(data) {
			if (data.children != null) {
				treeRadioCom($("#editUserOrg .treeNew"), data.children, true);
				setTimeout("$('.treeSelId').click()", 800);
			}
		}
	});
}

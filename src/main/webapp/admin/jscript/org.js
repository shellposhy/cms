/**
 * 机构管理js
 * 
 * @author shishb
 */
$(document).ready(function() {
	// 初始化机构数
	inti_parent_tree();
	loadUserGuOptions();
	// 表单验证
	form_validate();
});

// 初始化机构树
function inti_parent_tree() {
	var url = appPath + "/admin/org/getNoUserOrgTree";
	$.ajax({
		type : "GET",
		url : url,
		contentType : 'application/json',
		success : function(data) {
			if (data.children != null) {
				treeRadioCom($("#editOrg .treeNew"), data, false);
				setTimeout("$('.treeSelId').click()", 800);
			}
		}
	});
}


//加载多选的用户组option
function loadUserGuOptions() {
	if ($("#org_new_form #name").val()) {
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
			$("#org_new_form #treeSelId_box").append(tmpOptions);
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
			$("#org_new_form #treeSelId_box").html(tmpOptions);
		}
	}
	var treeSelIds = $("#org_new_form #treeSelId_box").val();
	$("#treeSelId").val(treeSelIds);

	$("#org_new_form #treeSelId_box").change(function() {
		var treeSelIdsn = $("#org_new_form #treeSelId_box").val();
		$("#treeSelId").val(treeSelIdsn);
	});
}

// 表单验证
function form_validate() {
	$("#org_new_form").validate({
		rules : {
			name : {
				required : true
			},
			code : {
				required : true
			}
		},
		messages : {
			name : {
				required : "机构名称不能为空！"
			},
			code : {
				required : "机构编码不能为空！"
			}
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
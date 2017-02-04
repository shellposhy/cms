/**
 * 页面发布管理通用脚本
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	init_load_page_list(); 
	page_form_validate();
	load_page_model("Index");
});

// 加载页面列表
function init_load_page_list() {
	if ($("#view_page_list").length) {
		page_data_table();
		publish_home_page("publish_list", "page/publish", oTableDataPage);
		publish_list_page("subPublish_list", "page/publishSub");
	}
}

// dataTable初始化
var oTableDataPage;
var markChange = 0;
function page_data_table() {
	oTableDataPage = $("#view_page_list").dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "full_numbers",
		"bProcessing" : true,
		"bServerSide" : true,
		"bDestroy" : true,
		"bRetrieve" : true,
		"bSort" : false,
		"sAjaxSource" : "page/s",
		"fnServerData" : retrieveData,
		"iDisplayLength" : 20,
		"oLanguage" : {
			"sUrl" : appPath + "/admin/js/javascript/de_CN.js"
		},
		"aoColumns" : [{
					"mData" : "title",
					"fnRender" : function(obj) {
						var link = obj.aData.title;
						if (obj.aData.status == "已发布") {
							link = '<a target="_blank" href="'+ appPath + '/'+ obj.aData.file + '">'+ obj.aData.title + '</a>'
						}
						return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id
								+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ link+ '</label>'
								+ '<button title="页面配置" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none" ><i class="icon-edit"></i></button>';
					}
				}, {
					"mData" : "type"
				}, {
					"mData" : "status"
				}, {
					"mData" : "createTime"
				}, {
					"mData" : "publishTime"
				} ],
		"fnDrawCallback" : callback_page
	});
}

//回调函数
function callback_page() {
	docReady();
	trHoverEdit(showItemEdit);
	trHoverModi();
	listDelete("page/delete", oTableDataPage);
};

//编辑回调函数
function showItemEdit() {
	$(".trHoverEdit tr .editbtn").live("click", function() {
		var pageId = $(this).parent().find("input[type='checkbox']").val();
		window.location.href = "page/" + "config/" + pageId;
	})
}

//首页发布
function publish_home_page(btnId, sUrl, oTable) {
	$("#publish_list").click(function() {
		if ($(this).parent().nextAll(".dataTables_wrapper").find("tbody input[type='checkbox']").length > 0) {
			var count = 0;
			var idsVal = new Array();
			$(this).parent().nextAll(".dataTables_wrapper").find("tbody input[type='checkbox']").each(function() {
				if ($(this).attr("checked")&& $(this).val() != null&& $(this).val().length > 0) {
					idsVal.push($(this).val());
					count++;
				}
			});
			var sData = idsVal.join(",");
			var spData = {
				name : "ids",
				value : sData
			};
			if (count > 0) {
				$('#noticeModal').modal('show');
				$("#noticeModal").find('.btn-primary').click(function() {
					var btnPrimary = $(this);
					btnPrimary.attr("disabled", true).siblings(".loading").show();
					$.ajax({
						type : 'post',
						contentType : "application/json",
						url : sUrl,
						data : JSON.stringify(spData),
						success : function(resp) {
							btnPrimary.attr("disabled", false).siblings(".loading").hide();
							oTable.fnDraw();
							setTimeout("isCheckboxStyle();",300);
							$('#noticeModal').modal('hide');
							noty({"text" : "发布成功","layout" : "center","type" : "alert","animateOpen" : {"opacity" : "show"}});
						},
						error : function(data) {
							btnPrimary.attr("disabled", false).siblings(".loading").hide();
							noty({"text" : "操作失败，请重试","layout" : "center","type" : "error"});
						}
					});
				});
			} else {
				noty({"text" : "请选择要发布的页面","layout" : "top","type" : "information"});
			}
			return false;
		}
	});
}

//二级页面发布
function publish_list_page(btnId, sUrl) {
	$("#subPublish_list").click(function() {
		$('#noticeModal').modal('show');
		$("#noticeModal").find('.btn-primary').click(function() {
				var btnPrimary = $(this);
				btnPrimary.attr("disabled", true).siblings(".loading").show();
				$.ajax({
					type : 'post',
					url : sUrl,
					success : function(resp) {
						var flag = false;
						if (resp != null && resp != "") {
							flag = true;
						}
						if (flag) {
							$("#noticeModal").find('.btn-primary').attr("disabled", false).siblings(".loading").hide();
							$('#noticeModal').modal('hide');
							noty({"text" : "发布成功","layout" : "center","type" : "alert","animateOpen" : {"opacity" : "show"}});
						}
					},
					error : function(data) {
						$("#noticeModal").find('.btn-primary').attr("disabled", false).siblings(".loading").hide();
						noty({"text" : "操作失败","layout" : "top","type" : "information"});
					}
				});
			});
		return false;
	});
}

// 页面类型模板选择事件
$('#viewPage_Type').change(function() {
	var page_Type = $('#viewPage_Type').val();
	if (page_Type == 1) {
		$('#itemContentDb').hide();
		$('#modelId').val("");
		$('#contentDbId').val("");
		load_page_model("Index");
	} else {
		load_page_model("Subject");
		$('#itemContentDb').show();
	}
});

//专题模板选择区域
function load_page_model(type) {
	var layoutId = $("#modelId").val();
	var url = appPath + "/admin/view/page/model/" + type;
	var selectOptions;
	$.ajax({
		type : "GET",
		url : url,
		dataType : 'json',
		contentType : 'application/json',
		success : function(data) {
			var layouts = data;
			var size = data.length;
			for (var i = 0; i < size; i++) {
				selectOptions += "<option value='" + layouts[i].id + "' title='"+ layouts[i].imgUrl + "'>" + layouts[i].name+ "</option>";
			}
			$("#para option").remove();
			$("#para").append(selectOptions);
			$("#para option[value='" + layoutId + "']").attr("selected", true);
			bind_model_change();
			bind_model_show_img();
		},
		error : function(data) {
			alert("失败");
		}
	});
}


//专题模板选择事件
function bind_model_change() {
	$("#para").change(function() {
		var styleId = $('#para').val();
		$("#modelId").val(styleId);
	});
}
$('#para').change(function() {
	bind_model_show_img();
});
function bind_model_show_img() {
	var obj = document.getElementById("para");
	if(obj!=null){
		if (obj.selectedIndex == -1) {
			document.getElementById("logodiv").hidden = true;
		}
		var src = obj.options[obj.selectedIndex].title;
		if (src == null || src.length == 0 || "null" == src) {
			document.getElementById("logodiv").hidden = true;
		} else {
			src = appPath + "/" + src;
			document.getElementById("logodiv").hidden = false;
			document.getElementById("logo").src = src;
		}
	}
}

// 保存配置表单验证
function itemSaveFormV() {
	$("#viewItemForm").validate({
		ignore : [],
		rules : {
			contentDBId : {
				required : true
			}
		},
		messages : {
			contentDBId : {
				required : "请选择来源"
			}
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element);
		},
		onkeyup : false
	});
}

//表单验证
function page_form_validate() {
	$("#viewPageForm").validate({
		rules : {
			title : {
				required : true,
				specialCharValidate : false
			}
		},
		messages : {
			title : {
				required : "页面名称不能为空！",
				specialCharValidate : "不能填写特殊字符！"
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

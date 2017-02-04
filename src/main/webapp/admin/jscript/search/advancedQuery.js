/**
 * 高级查询通用js处理
 * */
$(document).ready(function() {
	load_as_tree();
	add_search_field();
	and_delete_field();
	logic_select();
	multi_form_validate();
	exp_form_validate();
});


//初始化加载树
function load_as_tree(){
	select_multi_tree();
	select_expression_tree();
}  

// 加载数据库选择下拉列表
function select_expression_tree() {
	var url = appPath+"/admin/library/userTree"; 
	$.getJSON(url, function(data) {
		treeSleCom($("#tree_sel_db_as_e .treeNew"), data);
		setTimeout("$('.treeSelId').click()", 800);
	});
}

// 加载数据库选择下拉列表
function select_multi_tree() {
	var url = appPath+"/admin/library/userTree"; 
	$.getJSON(url, function(data) {
		treeSleCom($("#tree_sel_db_as_m .treeNew"), data, callback_show_field);
	});
}

// 获取已选数据库公共字段
//回调函数
function callback_show_field(ids) {
	if (ids != "") {
		showDataFields(ids);
		$("#dataFields").find("h3").remove();
	} else {
		$("#dataFields").html("<h3 class='span12'><div class='alert span6'>请选择查询范围并添加检索字段</div></h3>")
		$("#dbFields option").remove();
	}
}

// 显示字段
function showDataFields(ids) {
	var resrc_type = $('#resrc_Type').val();
	var temUrl = appPath+"/admin/data/field";
	var dataBaseIdstr = {
		"name" : "datebaseStr",
		"value" : ids
	};
	$.ajax({
		type : "POST",
		url : temUrl,
		contentType : "application/json",
		dataType : "JSON",
		data : JSON.stringify(dataBaseIdstr),
		success : function(data) {
			var options = "";
			if (data) {
				for ( var i = 0; i < data.length; i++) {
					options += "<option value='" + data[i].code + "' type='"+ data[i].dataType + "' >" + data[i].name+ "</option>"
				}
				$("#dbFields").html(options);
			}
		}
	});
}

// 确认添加字段输入框
function add_search_field() {
	$("#addDataField").live("click",function() {
		var type = $("#dbFields option:selected").attr("type");
		var name = $("#dbFields option:selected").text();
		var val = $("#dbFields option:selected").val();
		if (val == "Sort_Ids"){} else {
			if ((type != "11") && (type != "3")) {
				var newFidld = '<div class="control-group"><label class="control-label" >'
						+ name
						+ '：</label><div class="controls"><div class="inline btn_group_as" data-toggle="buttons-radio">'
						+ '<a class="btn mr4 active" value="And">必含</a>'
						+ '<a class="btn mr4" value="Or">选含</a><a class="btn mr4" value="Not">排除</a>'
						+ '<input type="hidden" name="'
						+ val
						+ '_logic" value="And" />'
						+ '</div><input class="input-xlarge mr4" type="text" name="'
						+ val
						+ '" />'
						+ '<span class="btn remove mr4"><i class="icon-trash icon-color"></i></span>'
						+ '</div></div>'
				$("#dataFields").append(newFidld);
			} else if (type == "11") {
				var newFidld = '<div class="control-group">'
						+ '<label class="control-label" >'
						+ name
						+ '：</label>'
						+ '<div class="controls">'
						+ '<div class="inline btn_group_as floatl" data-toggle="buttons-radio">'
						+ '<a class="btn mr4 active" value="And">必含</a>'
						+ '<a class="btn mr4" value="Or">选含</a><a class="btn mr4" value="Not">排除</a>'
						+ '<input type="hidden" name="'
						+ val
						+ '_logic" value="And" />'
						+ '</div>'
						+ '<span class="floatl">'
						+ '<div class="inline btn_group_as blue" data-toggle="buttons-radio">'
						+ '<a class="btn mr4" value="today">本日</a>'
						+ '<a class="btn mr4" value="month" >本月</a>'
						+ '<a class="btn mr4 active" value="date_time">时间段</a>'
						+ '<input type="hidden" name="'
						+ val
						+ '" value="date_time" /></div>'
						+ '<input class="input-medium timepicker mr4" type="text" name="'
						+ val
						+ '_start" /><span class="mr4">——</span>'
						+ '<input class="input-medium timepicker mr4"  type="text" name="'
						+ val
						+ '_end" />'
						+ '<span class="btn remove mr4"><i class="icon-trash icon-color"></i></span>'
						+ '</span>' + '</div></div>'
				$("#dataFields").append(newFidld);
				timepickerReady();
			} else if (type == "3") {
				var newFidld = '<div class="control-group">'
						+ '<label class="control-label" >'
						+ name
						+ '：</label>'
						+ '<div class="controls">'
						+ '<div class="inline btn_group_as" data-toggle="buttons-radio">'
						+ '<a class="btn mr4" value="0">未发布</a>'
						+ '<a class="btn mr4 active" value="3">已发布</a>'
						+ '<input type="hidden" name="'
						+ val
						+ '" value="3" /></div>'
						+ '<span class="btn remove mr4"><i class="icon icon-close"></i></span></div></div>'
				$("#dataFields").append(newFidld);
			}
		}
		$(this).parents(".showDataFields").hide();
	})
}

// 显示添加字段输入框
function and_delete_field() {
	$("#addShowDatafields").click(function() {
		if (!$("#searchConIdStr").val()) {
			$("#treeSelShow").click();
			return false;
		}
		$(".showDataFields").show();
	});
	$("#dataFields .remove").live("click", function() {
		$(this).parentsUntil("#dataFields").remove();// 绑定删除单个字段
	})
}

// 选择搜索逻辑 赋给隐藏域值
function logic_select() {
	$(".btn_group_as a").live("click",function() {
		var curtVal = $(this).attr("value")
		$(this).parents(".btn_group_as").find("input[type='hidden']")
				.val(curtVal)
	})
}

// 条件查询
function multi_form_validate() {
	$("#conBtn").click(function() {
		var dbIds = $("#searchConIdStr").val();
		if (dbIds == null || dbIds == "") {
			noty({
				"text" : "请先选择数据库！",
				"layout" : "center",
				"type" : "alert",
				"animateOpen" : {
					"opacity" : "show"
				}
			});
			$("#treeSelShow").click();
			return false;
		}
		// 表单提交
		$("#query_as_form_m").submit();
	});
}

// 表达式查询
function exp_form_validate() {
	$("#expBtn").click(function() {
		var dbIds = $("#searchExpIdStr").val();
		if (dbIds == null || dbIds == "") {
			noty({
				"text" : "请先选择数据库！",
				"layout" : "center",
				"type" : "alert",
				"animateOpen" : {
					"opacity" : "show"
				}
			});
			$("#etreeSelShow").click();
			return false;
		}
		// 表单提交
		$("#query_as_form_e").submit();
	});
}

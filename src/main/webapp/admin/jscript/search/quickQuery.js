/**
 * 快捷查询通用脚本
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	load_library_tree();
	quick_search();
});

// 加载数据库选择下拉列表
function load_library_tree() {
	var tmpHPage = document.location.href.split("/");
	var url = appPath+"/admin/library/userTree"; 
	$.getJSON(url, function(data) {
		treeSleCom($("#tree_sel_db_qs .treeNew"), data);
		var delayQsSel = setTimeout("$('#tree_sel_db_qs .treeSelId').click()",500);
	});
}

// 提交查询按钮
function quick_search() {
	$("#qsBtn").click(function() {
		var dbIds = $(".treeSelId").val();
		if (dbIds == null || dbIds == "") {
			noty({
				"text" : "请先选择数据库！",
				"layout" : "center",
				"type" : "alert",
				"animateOpen" : {"opacity" : "show"}
			});
			$("#treeSelShow").click();
			return false;
		}
		var queryStr = $("#queryString").val();
		var iType = "qs";
		var url = "../data?searchIdStr=" + dbIds + "&mSearch="+ encodeURI(encodeURI(queryStr)) + "&iType=" + iType;
		window.location.href = url;
	});
	$("#queryString").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#qsBtn").trigger("click");
			$(this).blur();
		} else {
			return false;
		}
	});
}
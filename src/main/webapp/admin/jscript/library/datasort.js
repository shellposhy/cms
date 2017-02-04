/**
 * 数据分类通过脚本
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	init_load_Db();
	init_global_sort();
	init_all_sort_tree();
	add_form_validate();
	data_sort_tree_link();
});

/**
 * 加载用户数据库列表
 * */
function init_load_Db() {
	if ($("#dataBaseTree_sort_u").length > 0) {
		$.ajax({
			type : "POST",
			url : "/admin/data/sort/library/0/tree",
			data : '[{"name":"id","value":""}]',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				if (data.children != null) {
					menuTreeCom($("#dataBaseTree_sort_u"), data, false,find_db_sort, "", $("#add_u_db_btn"), add_new_sort);
					bind_search_seleted();// 绑定搜索按钮检查是否已经选中
				} else {
					$("#dataBaseTree_sort_u").html("<h3 class='alert alert-info' >暂无自定义数据库</h3>");
				}
			}
		});
	}
}

/**
 * 查询数据库下面对应的分类
 * @param parentId
 * @param isparent
 * */
function find_db_sort(parentId, isparent) {
	if (isparent) {
		if ($("#dataBaseTree_sort_u_s").length > 0) {
			$("#dataBaseTree_sort_u_s").html("<h3 class='alert alert-info' >请选择数据库查看该库下分类</h3>");
		}
	} else {
		load_data_sort(parentId);
		$("#search_sort_u_btn").unbind();
		keyword_find_data_sort(parentId);
	}
}

/**
 * 加载数据库下面对应的分类
 * @param parentId
 * */
function load_data_sort(parentId) {
	$.ajax({
		type : "POST",
		url : "/admin/data/sort/" + parentId + "/s",
		data : '[{"name":"id","value":""}]',
		dataType : 'json',
		contentType : 'application/json',
		success : function(data) {
			if (data.children != null) {
				if ($("#dataBaseTree_sort_u_s").length > 0) {
					menuTreeCom($("#dataBaseTree_sort_u_s"), data, false, null,"/admin/data/sort/");
				}
			} else {
				if ($("#dataBaseTree_sort_u_s").length > 0) {
					$("#dataBaseTree_sort_u_s").html("<h3 class='alert alert-info' >该数据库下无分类</h3>");
				}
			}
		},
		error : function(msg) {
			alert("查询出错");
		}
	});
}

/**
 * 新增数据库分类
 * @param thishref
 * @param selid
 * @param isSel
 * */
function add_new_sort(thishref, selid, isSel) {
	if ((!$(".show_sel_itme li")[0]) && (isSel == true)) {
		window.location.href = thishref + "/" + selid + "/new";
		return false;
	} else {
		noty({
			"text" : "请先选择要添加的分类所属数据库",
			"layout" : "topCenter",
			"type" : "information",
			"animateOpen" : {
				"opacity" : "show"
			}
		});
		return false;
	}
}

/**
 * 关键词查询数据分类
 * @param baseId
 * */
function keyword_find_data_sort(baseId) {
	// 自定义库绑定
	$("#search_sort_u_btn").bind("click",function() {
		if ($("#search_sort_u").val()) {
			var sdata = '[{"name":"sSearch","value":"'+ $("#search_sort_u").val() + '"}]';
			$.ajax({
				type : "post",
				url : "/admin/data/sort/" + baseId + "/s",
				dataType : 'json',
				contentType : 'application/json',
				data : sdata,
				success : function(data) {
					if (data.children != null) {
						var searchdbSortU = "<h3 class='alert alert-info'> 查询结果: </h3>";
						menuTreeCom($("#dataBaseTree_sort_u_s"),data, false, null, "");
						$("#dataBaseTree_sort_u_s").prepend(searchdbSortU);
					} else {
						$("#dataBaseTree_sort_u_s").html("<h3 class='alert alert-info' >无匹配的搜索结果！</h3>");
					}
				}
			});
		} else {
			$("#search_sort_u").focus();
		}
	});
	$("#search_sort_u").keyup(function(event) {
		if ($("#search_sort_u").val()) {
			if (event.keyCode == 13) {
				$("#search_sort_u_btn").trigger("click");
				$(this).blur();
			} else {
				return false;
			}
		}
	});
}

/**
 * 绑定搜索框检查是否已经选中要搜索的节点
 */
function bind_search_seleted() {
	$(this).find(".search_seled").bind("click", function() {
		if ($(".green_tree .curSelectedNode").length < 2) {
			noty({
				"text" : "请先选择要搜索的节点",
				"layout" : "topCenter",
				"type" : "information",
				"animateOpen" : {
					"opacity" : "show"
				}
			});
		}
	});
}

/**
 * 加载全局数据分类
 */
function init_global_sort() {
	$.ajax({
		type : "POST",
		url : "/admin/data/sort/0/s",
		data : '[{"name":"id","value":""}]',
		dataType : 'json',
		contentType : 'application/json',
		success : function(data) {
			if (data.children != null) {
				menuTreeCom($("#dataSortTree_g"), data, true, null,"/admin/data/sort/");
			} else {
				$("#dataSortTree_g").html("<h3 class='alert alert-info' >暂无全局分类</h3>");
			}
		}
	});
	keyword_global_sort_search();
}

/**
 * 全局数据分类关键词查询
 * */
function keyword_global_sort_search() {
	$("#search_gsort_btn").bind("click",function() {
		if ($("#search_gsort").val()) {
			var sdata = '[{"name":"sSearch","value":"'+ $("#search_gsort").val() + '"}]';
			$.ajax({
				type : "post",
				url : "/admin/data/sort/0/s",
				dataType : 'json',
				contentType : 'application/json',
				data : sdata,
				success : function(data) {
					if (data.children != null) {
						var searchdbSortU = "<h3 class='alert alert-info'> 查询结果: </h3>";
						menuTreeCom($("#dataSortTree_g"),data, false, null,"../dataSort/");
						$("#dataSortTree_g").prepend(searchdbSortU);
					} else {
						$("#dataSortTree_g").html("<h3 class='alert alert-info' >无匹配的搜索结果！</h3>");
					}
				}
			});
		} else {
			$("#search_gsort").focus();
		}
	});
	$("#search_gsort").keyup(function(event) {
		if ($("#search_gsort").val()) {
			if (event.keyCode == 13) {
				$("#search_gsort_btn").trigger("click");
				$(this).blur();
			} else {
				return false;
			}
		}
	});
}

/**
 * dbsort edit 页面用到的js
 * 隐掉编辑分类回显的code和分类input
 */
function init_all_sort_tree() {
	if ($("#type_tree")[0]) {
		$.ajax({
			type : "POST",
			url : '/admin/data/sort/' + $("#baseId").val()+ '/s',
			data : '[{"name":"id","value":""}]',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				treeRadioCom($("#type_tree .treeNew"), data);
				setTimeout("$('.treeSelId').click()", 800);
			}
		});
	}
}

/**
 * 表单验证
 * */
function add_form_validate() {
	$("#db_sort_new_form").validate({
		ignore : "",
		rules : {
			name : "required",
			code : "required",
			parentID : "required"
		},
		messages : {
			name : "请填写分类名称",
			code : "请填写分类编号",
			parentID : "请选择父级分类"
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

// 取消数据分类中的A链接的默认跳转
function data_sort_tree_link() {
	$("#dataSortTree_p a").live("click", function() {
		return false;
	});
}
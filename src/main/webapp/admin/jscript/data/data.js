/**
 * 数据服务通用脚本
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	load_library_data();
	show_form_field();
	init_sort_tree();
	if ($("#contentArea").length > 0) {
		UE.getEditor('contentArea');
	}
});

// 定义通用公共参数
var thisPath = appPath + "/admin/system/library/";
var searchIdStr = $("#searchIdStr").val();
var mSearch = $("#mSearch").val();
var iType = $("#iType").val();

function load_library_data() {
	var objTitle = [ {
		"mData" : "title",
		"fnRender" : function(obj) {
			var sumImg = "";
			var attach = "";
			if (obj.aData.img) {
				sumImg = '<div class="sum_img_div"><img class="list_sum_img" src="'+ obj.aData.img + '"/></div>';
			}
			if (obj.aData.attach) {
				attach = " <span class='icon icon-blue icon-attachment'></span>";
			}
			var result = '<h3>' 
								+ '	<label class="checkbox inline mt0">'
								+ '		<input type="checkbox" id="inlineCheckbox'+ obj.aData.id + '" name="idStr' + obj.aData.id+ '" value="' + obj.aData.id + '_' + obj.aData.tableId+ '" style="opacity: 0;" >' 
								+ '	</label>';
			if (obj.aData.status > 0 && obj.aData.status != 3) {
				result += obj.aData.title;
			} else {
				result += '<a class="data_title edit_pop_link" href="'+ thisPath + 'data/edit/' + obj.aData.tableId + '/'+ obj.aData.id + '" target="_blank">' + obj.aData.title+ '</a>';
			}
			result += '	<a  class="padmbt btn floatr none data_title edit_pop_link" href="'+ thisPath+ 'data/info/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '">'
						+ '		<i class="icon-eye-open" title = "文章预览"></i>'
					    + '	</a>'
					    + '</h3><p class="summary clearfix" >'+ sumImg+ obj.aData.summary + attach + '</p>';
			return result;
		}
	} ];
	dataTablesCom($('#dataTable'), "data/s?iType=" + iType + "&mSearch="+ mSearch + "&searchIdStr=" + searchIdStr, objTitle, null,callback_data, false, false, true);
};

// 回调函数
function callback_data(oTableDataDb) {
	docReady();
	trHoverEdit();
	listDelete(thisPath + "data/delete", oTableDataDb);
	editPopWithDT(oTableDataDb);
};

// 加载数据标签
function init_sort_tree() {
	var url = appPath + "/admin/data/sort/" + $("#baseId").val() + "/tree";
	if ($("#sort_tree")[0]) {
		$.ajax({
			type : "POST",
			url : url,
			data : '[{"name":"id","value":""}]',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				treeRadioCom($("#sort_tree .treeNew"), data.children, true);
				setTimeout("$('.treeSelId').click()", 800);
			}
		});
	}
}

// 判断匹配出现的表单
function show_form_field() {
	if ($("#Article_new_form").length > 0) {
		$("#Article_new_form").hide();
		var dataField = $("#fieldsStr").val().split(",");
		for (var i = 0; i < $("#Article_new_form input").length; i++) {
			var flag = true;
			for (var j = 0; j < dataField.length; j++) {
				if ($("#Article_new_form input:eq(" + i + ")").attr("id") == dataField[j]) {
					flag = false;
					break;
				}
				if ($("#Article_new_form select:eq(" + i + ")").attr("id") == dataField[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				if ($("#Article_new_form input:eq(" + i + ")").hasClass("treeSel")
						|| $("#Article_new_form input:eq(" + i + ")").hasClass("treeRadio")
						|| $("#Article_new_form input:eq(" + i + ")").hasClass("ios_toggle")) {
					continue;
				}
				if ($("#Article_new_form input:eq(" + i + ")").attr("id") == "Imgs") {
					$("#Article_new_form input:eq(" + i + ")").parents(".futu_imgs").addClass("beremove");
				} else if ($("#Article_new_form input:eq(" + i + ")").attr("id") == "Data_Status") {
					$("#Article_new_form input:eq(" + i + ")").parents(".control-group").addClass("beremove");
				} else if ($("#Article_new_form select:eq(" + i + ")").attr("id") == "Secret_Level") {
					$("#Article_new_form select:eq(" + i + ")").parents(".control-group").addClass("beremove");
				} else {
					$("#Article_new_form input:eq(" + i + ")").parents(".control-group").addClass("beremove");
				}
			}
		}
		var summaryFlag = true;
		for (var j = 0; j < dataField.length; j++) {
			if (dataField[j] == "Summary") {
				summaryFlag = false;
				break;
			}
		}
		if (summaryFlag) {
			$("#Summary").parents(".control-group").addClass("beremove");
		}
		$(".beremove").remove();
		$("#Article_new_form").show();
		$("#Article_new_form").submit(function() {
			$(this).ajaxSubmit({
				success : function() {
					parent.$.colorbox.close();
				}
			});
			return false;
		});
	}
}

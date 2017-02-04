/**
 * 系统初始化菜单处理类
 * @author shishb
 * @version 1.0
 * */
$(document).ready(function() {
	addValiMethod();
	backhide();
	var ff = setTimeout("footerFix()", 600);
	iphoneStyle();
	goTop();
	selAllBind();
	intTreeTable();
	setPop();
	back_Button();
});

// 阻止浏览器的默认行为
function stopDefault(e) {
	if (e && e.preventDefault)
		e.preventDefault();
	else
		window.event.returnValue = false;
	return false;
}

// 保持footer始终在底部
function footerFix() {
	if ($("#foot").length > 0) {
		var footerOff = $("#foot")[0].offsetTop;
		var bodyH = $("body").height();
		var clientH = document.documentElement.clientHeight;
		if (clientH > bodyH) {
			var chaH = clientH - bodyH;
			var conH = $("#content").height();
			var minh = "min-height:" + conH + chaH;
			$("#content").css("min-height", conH + chaH);
		}
	}
}

// 返回顶部
function goTop() {
	$("#back-to-top").hide();
	$(function() {
		$(window).scroll(function() {
			if ($(window).scrollTop() > 400) {
				$("#back-to-top").fadeIn(400);
			} else {
				$("#back-to-top").fadeOut(400);
			}
		});
		$("#back-to-top").click(function() {
			$('body,html').animate({
				scrollTop : 0
			}, 400);
			return false;
		});
	});
}

// 表格传参
function retrieveData(sSource, aoData, fnCallback) {
	$.ajax({
		"type" : "post",
		"contentType" : "application/json",
		"url" : sSource,
		"dataType" : "json",
		"data" : JSON.stringify(aoData),
		"success" : function(resp) {
			if (resp.aaData) {
				fnCallback(resp);
			}
		},
		"complete" : function(XHR, TS) {
			XHR = null;
		}
	});
}

// 表格hover 编辑按钮
function trHoverEdit(obj) {
	$(".trHoverEdit tr").live("mouseenter", function() {
		var eidt_bt = $(this).find("td:first").find("i").parent();
		eidt_bt.fadeIn(200);
	});
	$(".trHoverEdit tr").live("mouseleave", function() {
		var eidt_bt = $(this).find("td:first").find("i").parent();
		eidt_bt.fadeOut(200);
	});
	if (obj) {
		obj();
	} else {
		editGoto($(".trHoverEdit tr button.editbtn"));
	}
};

// 表格hover 编辑按钮
function trHoverBtn(btnClass, iClass, Operation) {
	$(".trHoverEdit tr").live("mouseenter", function() {
		var eidt_bt = $(this).find("td:first").find("i." + iClass).parent();
		eidt_bt.fadeIn(200);
	});
	$(".trHoverEdit tr").live("mouseleave", function() {
		var eidt_bt = $(this).find("td:first").find("i." + iClass).parent();
		eidt_bt.fadeOut(200);
	});
	hoverBtnGoto($(".trHoverEdit tr button." + btnClass), Operation);
};

/* 编辑表格条目跳转通用 */
function hoverBtnGoto(obj, Operation) {
	var thishref = document.location.href;
	obj.live("click", function() {
		window.location.href = thishref + "/" + Operation + "/"
				+ $(this).parent().find("input[type='checkbox']").val();
	});
}

// 表格hover人工干预按钮
function trHoverModi() {
	$(".trHoverModi tr").live("mouseenter", function() {
		var modi_bt = $(this).find("td:first").find("i.icon-wrench").parent();
		modi_bt.fadeIn(200);
	});
	$(".trHoverModi tr").live("mouseleave", function() {
		var modi_bt = $(this).find("td:first").find("i.icon-wrench").parent();
		modi_bt.fadeOut(200);
	});
};

/* 稿件送审 */
function listSend(sUrl, oTable) {
	$('#sendModal').modal('hide');
	$("#check_from_dsu").unbind();
	$("#check_from_dsu").click(
			function() {
				var dt = $(this).parent().nextAll(".dataTables_wrapper");
				if (dt.length < 1) {
					dt = $(this).parent().parent().nextAll(".dataTables_wrapper");
				}
				if (dt.find("table input[type='checkbox']").length > 0) {
					var count = 0;
					var idsVal = new Array();
					dt.find("table tbody input[type='checkbox']").each(
							function() {
								if ($(this).attr("checked")
										&& $(this).val() != null
										&& $(this).val().length > 0) {
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
						$('#sendModal').modal('show');
						$("#sendModal").find('.btn-primary').click(
								function() {
									$.ajax({
										type : 'post',
										contentType : "application/json",
										url : sUrl,
										data : JSON.stringify(spData),
										success : function(resp) {
											oTable.fnDraw();
											var delayRd = setTimeout(
													"isCheckboxStyle();", 300);
											$('#sendModal').modal('hide');
										},
										error : function(data) {
											$('#sendModal').modal('hide');
											noty({
												"text" : "操作出错！",
												"layout" : "top",
												"type" : "information"
											});
										}
									});
								});
					}
					return false;
				}
			});
}

/* 列表通用删除按钮 */
function listDelete(sUrl, oTable) {
	$(".delete_list").unbind();
	$(".delete_list").click(function() {
		var dt = $(this).parent().nextAll(".dataTables_wrapper");
		if (dt.length < 1) {
			dt = $(this).parent().parent().nextAll(".dataTables_wrapper");
		}
		if (dt.find("table input[type='checkbox']").length > 0) {
			var count = 0;
			var idsVal = new Array();
			dt.find("table tbody input[type='checkbox']").each(function() {
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
				$('#delModal').modal('show');
				$("#delModal").find('.btn-primary').click(function() {
					$.ajax({
						type : 'post',
						contentType : "application/json",
						url : sUrl,
						data : JSON.stringify(spData),
						success : function(resp) {
							oTable.fnDraw();
							var delayRd = setTimeout("isCheckboxStyle();",300);
							$('#delModal').modal('hide');
							if ($(".nav_tabs_for_datatables").length > 0) {
							}
						},
						error : function(data) {
							noty({"text" : "删除出错","layout" : "top","type" : "information"});
						}
					});
				});
			}
			return false;
		}
	});
}

// 表格 判断复选框样式初始化
function isCheckboxStyle() {
	if ($("input[type='checkbox']").parent("span").length != 0) {
		return false;
	} else {
		$("input:checkbox, input:radio, input:file").not('[data-no-uniform="true"]').uniform();
	}
}

/* 编辑表格条目跳转 */
function editGoto(obj) {
	var thishref = document.location.href;
	obj.live("click", function() {
		window.location.href = thishref + "/"+ $(this).parent().find("input[type='checkbox']").val()+ "/edit";
	});
}

/* 隐藏回显时候禁止 */
function backhide() {
	if ($("input[name='id'][id='id']").val()) {
		$(".backhide").hide();
	}
}

/*苹果按钮*/
function iphoneStyle() {
	$('.ios_toggle').iphoneStyle({
		checkedLabel : '启用',
		uncheckedLabel : '禁用',
		onChange : function() {
			var Sval = $(".ios_toggle_hidden").val();
			if (Sval == "Normal") {
				$(".ios_toggle_hidden").val("Stop");
			} else if (Sval == "Stop") {
				$(".ios_toggle_hidden").val("Normal");
			}
		}
	});
}

/* 通用进度条 */
function ProgressBarCom(oBar, sUrl, sTip) {
	var oShowProgress = function() {
		var thisPro = oBar;
		var thisProBar = oBar.find(".bar");
		$.ajax({
			type : "GET",
			url : sUrl,
			cache : false,
			dataType : "json",
			success : function(data) {
				var barpres = data + "%";
				if (data < 100) {
					thisPro.show();
					thisProBar.css("width", barpres);
				} else if (data == 100) {
					thisProBar.css("width", "100%");
					thisPro.hide();
					clearInterval(intInterval);
					noty({"text" : sTip,"layout" : "center","type" : "alert","animateOpen" : {"opacity" : "show"}});
				}
			}
		});
	};
	var intInterval = setInterval(oShowProgress, 5000);
}

//特殊字符验证
function addValiMethod() {
	jQuery.validator.addMethod("specialCharValidate", function(value, element) {
		var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',　\\[\\]<>/? \\.；：%……+￥（）【】‘”“'。，、？]");
		return this.optional(element) || !pattern.test(value);
	}, jQuery.format(jQuery.validator.messages["specialCharValidate"]));

}

/*
 * datatables表格的抽象方法
 * tbId 要呈现表格的目标元素的id,字符串【必填】
 * url 除根域名以外的路径：/admin/... 【必填】
 * headTitle 设置表格标题字段 数组 【必填】
 * var headTitle =[ { "mData" : "title" }, { "mData" : "name" } ]
 * otherField：添加其他字段 【无则填null】 var otherField =[{ "name" : "iType", "value" :
 * iType }, { "name" : "searchIdStr", "value" : baseId }]
 * callback 回调函数 表格渲染完成的回调函数，oTableDataCom可以传递表格对象作为参数 【无则填null】 function
 * datatalbesReadyData(oTableDataCom) { docReady(); listDelete(appPath +
 * "/admin/data/delete", oTableDataCom);// 删除数据路径 }; isMutiTime
 * 是否支持同页面多次刷新加载，即在每次刷新载入数据前，销毁一次表格，再重建。是的话填true，留空或false则不支持重建。
 * noSearch ： 是否隐藏搜索条 true为隐藏
 * isDisAutoWidth： 是否禁用自动调整表格宽度， true为禁用，false/省缺为不禁
 */
function dataTablesCom(tbId, url, headTitle, otherField, callback, isMutiTime,noSearch, isDisAutoWidth) {
	var sDomStr = "";
	if (noSearch) {
		sDomStr = "<'row-fluid no-clear'r>t<'row-fluid'<'span12'i><'span12 center'p>>"
	} else {
		sDomStr = "<'row-fluid no-clear'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>"
	}
	var oTableDataCom = tbId.dataTable({
		"sDom" : sDomStr,
		"sPaginationType" : "full_numbers",
		"bProcessing" : true,
		"bServerSide" : true,
		"bSort" : false,
		"bAutoWidth" : !isDisAutoWidth,
		"bDestroy" : isMutiTime,
		"sAjaxSource" : appPath + url,
		"fnServerData" : retrieveData,
		"fnServerParams" : function(aoData) {
			if (otherField && otherField != null) {
				for (var i = 0; i < otherField.length; i++) {
					aoData.push(otherField[i])
				}
			}
		},
		"iDisplayLength" : 20,
		"oLanguage" : {
			"sUrl" : appPath + "/admin/js/javascript/de_CN.js"
		},
		"aoColumns" : headTitle,
		"aoColumnDefs" : [ {
			// "sWidth":"560px",
			"sWidth" : "400px",
			"aTargets" : [ 0 ]
		} ],
		"fnDrawCallback" : function() {
			if (callback != null) {
				return callback(oTableDataCom)
			} else {
				return docReady();
			}
		}
	});
}

/*
 * 通用弹出层
 * dtObj：表格对象 
 * linkObj：触发弹出层按钮 
 */
function editPopWithDT(dtObj, popBtns) {
	if (popBtns) {
		for (var i = 0; i < popBtns.length; i++) {
			var href = popBtns[i].url;
			popBtns[i].obj.attr("href", href).colorbox({
				iframe : true,
				width : "90%",
				height : "100%",
				onClosed : function() {
					dtObj.fnDraw();
				}
			});
		}
	}
	$(".edit_pop_link").unbind();
	$(".edit_pop_link").colorbox({
		iframe : true,
		width : "90%",
		height : "100%",
		onClosed : function() {
			dtObj.fnDraw();
		}
	});
}

// 设置弹出框
function setPop() {
	if ($(".pop_link").length > 0) {
		$(".pop_link").each(function() {
			var iframe = $(this).attr("href");
			$(this).colorbox({
				iframe : true,
				width : "90%",
				height : "100%"
			});
		});
	}
}

// 绑定全选复选框
function selAllBind() {
	$(".dataTables_wrapper .selAll").live("click",function() {
		var checkboxs = $(this).parents("table").find("tbody td input[type='checkbox']");
		var unCeckedsLen = checkboxs.not("[checked='checked']").length;
		if ($(this).attr("checked") && unCeckedsLen > 0) {
			checkboxs.attr("checked", "checked").parent("span").addClass("checked");
		} else {
			checkboxs.attr("checked", false).parent("span").removeClass("checked");
		}
	})
	$(".dataTables_wrapper tbody td input[type='checkbox']").live("click",function() {});
	$(".dataTables_wrapper tbody tr").live("click",function(event) {
		if (!($(event.target)[0].tagName == "A"|| $(event.target).hasClass("edit_pop_link")|| $(event.target)[0].tagName == "button" || $(event.target).hasClass("btn"))) {
			if ($(this).find("input[type='checkbox']").length > 0) {
				var sel_bt = $(this).find("input[type='checkbox']");
				if (!(sel_bt.parent().hasClass("checked"))) {
					sel_bt.attr("checked", "checked").parent().addClass("checked");
				} else {
					sel_bt.attr("checked", false).parent().removeClass("checked");
				}
				var unCeckedsLen = $(this).parents("table").find("tbody td input[type='checkbox']").not("[checked='checked']").length;
				var selAllBtn = $(this).parents("table").find("th input.selAll");
				if (selAllBtn.length > 0) {
					if (unCeckedsLen > 0) {
						selAllBtn.attr("checked", false).parent("span").removeClass("checked");
					} else {
						selAllBtn.attr("checked", "checked").parent("span").addClass("checked");
					}
				}
			}
		}
	});
}

// 设置树状表格
function intTreeTable() {
	if ((".tree_table").length > 0) {
		var cboxs = $(".tree_table tbody td input[type='checkbox']");
		$(".tree_table").treetable({
			expandable : true,
			onInitialized : function() {},
			onNodeInitialized : function() {	}
		});
		$(".tree_table tbody").on("mousedown", "tr", function() {
			$(".selected").not(this).removeClass("selected");
			$(this).addClass("selected");
		});
		cboxs.click(function() {
			var thisTr = $(this).parents("tr");
			var thisId = thisTr.attr("data-tt-id");
			var index = thisTr.find("td").index($(this).parents("td"))
			var inputArry = new Array();
			treeTableTran($(this), inputArry)
			if ($(this).attr("checked")) {
				$(inputArry).each(function() {
					$(this).attr("checked", "checked").parent("span").addClass("checked");
				})
			} else {
				$(inputArry).each(function() {
					$(this).attr("checked", false).parent("span").removeClass("checked");
				})
			}
			treeTableParents($(this))
		})
	}
}

function treeTableParents(obj) {
	var thisTr = obj.parents("tr");
	var thisPId = thisTr.attr("data-tt-parent-id");
	var index = thisTr.find("td").index(obj.parents("td"));
	var parent = thisTr.siblings("[data-tt-id='" + thisPId + "']").find("td:eq(" + index + ") input[type='checkbox']");
	var unCheckLen = thisTr.parent().children("[data-tt-parent-id='" + thisPId + "']").find("td:eq(" + index + ") input[type='checkbox']").not(":checked").length;
	unCheckLen > 0 ? parent.attr("checked", false).parent("span").removeClass("checked") : parent.attr("checked", "checked").parent("span").addClass("checked");
	if (parent.parents("tr").attr("data-tt-parent-id")) {
		treeTableParents(parent)
	}
}

function treeTableTran(obj, nodes) {
	if (obj.length > 0) {
		var thisTr = obj.parents("tr");
		var thisId = thisTr.attr("data-tt-id");
		var index = thisTr.find("td").index(obj.parents("td"));
		var children_tr = thisTr.siblings("[data-tt-parent-id='" + thisId+ "']");
		var children_input = children_tr.find("td:eq(" + index+ ") input[type='checkbox']");
		if (children_input.length > 0) {
			for (var i = 0; i < children_input.length; i++) {
				nodes.push(children_input.eq(i)[0]);
				if (children_input.eq(i).parents("tr").siblings("[data-tt-parent-id='"+ children_input.eq(i).parents("tr").attr("data-tt-id") + "']") != null&& children_input.eq(i).parents("tr").siblings("[data-tt-parent-id='"+ children_input.eq(i).parents("tr").attr("data-tt-id") + "']").length > 0) {
					treeTableTran(children_input.eq(i), nodes);
				}
			}
		}
	}
	return nodes;
}

// 确认对话框
function comConfirmModel(fn, para, strT, strC) {
	$('#comOperModal .modal-header h3').text(strT);
	$('#comOperModal .modal-body p').text(strC);
	$('#comOperModal').modal('show');
	$("#comOperModal").find('.btn-primary').unbind();
	$("#comOperModal").find('.btn-primary').click(function() {
		if (fn != null) {fn(para);}
		$('#comOperModal').modal('hide');
	});
}

// 通用的取消按钮
function back_Button() {
	$(".backBtn").click(function() {
		window.history.go(-1);
	});
}

$("#delModal").find('.btn-primary').click(function() {
	$.ajax({
		type : 'post',
		contentType : "application/json",
		url : sUrl,
		data : JSON.stringify(spData),
		success : function(resp) {
			oTable.fnDraw();
			var delayRd = setTimeout("isCheckboxStyle();", 300);
			$('#delModal').modal('hide');
			if ($(".nav_tabs_for_datatables").length > 0) {}
		},
		error : function(data) {
			noty({
				"text" : "删除出错",
				"layout" : "top",
				"type" : "information"
			});
		}
	});
});

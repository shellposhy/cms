/**
 * 系统初始化树形菜单处理类
 * @author shishb
 * @version 1.0
 * */
$(document).ready(function() {
	mmTreeShow();
});


function mmTreeShow() {
	if ($("#mmjsonActionTree").length < 1|| $("#mmjsonActionTree").text == "null"|| $("#mmjsonActionTree").text == "") {
		return false;
	}
	var zNodesO = JSON.parse($("#mmjsonActionTree").text());
	var mmzNodesa = new Array();
	jsonToNodesMm(zNodesO, mmzNodesa)
	var demoContent = {
		zTree_Menu : null,
		curExpandNode : null,
		curDemo : null,
		_init : function() {
			var a = {
				view : {showIcon : !0,showLine : !1,selectedMulti : !1,dblClickExpand : !1,addDiyDom : this.addDiyDom},
				data : {simpleData : {enable : !0,rootPId : ""}},
				callback : {beforeClick : this.beforeClick,beforeExpand : this.beforeExpand,onExpand : this.onExpand}
			};
			demoContent.zTree_Menu = $.fn.zTree.init($("#cmstree"),$.fn.zTree._z.tools.clone(a), mmzNodesa);
			demoContent.intSelect()
		},
		intSelect : function() {
			var navValue = $(".ind_f_tree").attr("value");
			a = demoContent.zTree_Menu.getNodeByParam("id", navValue, null);
			if (a) {
				demoContent.zTree_Menu.selectNode(a);
				if (a.level == 0) {
					demoContent.curExpandNode = a;
				} else {
					demoContent.curExpandNode = a.getParentNode();
				}
			}
			$("#cmstree").hover(function() {
				if (!$("#cmstree").hasClass("showIcon")) {
					$("#cmstree").addClass("showIcon");
				}
			}, function() {
				$("#cmstree").removeClass("showIcon");
			});
			$("#hideMenuTree").click(function() {
				$(".cmsTreeBackground, .content_main").toggleClass("menuHide");
			});
		},
		addDiyDom : function(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"), icoObj = $("#"+ treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);
			if (treeNode.level > 1) {
				var spaceStr = "<span style='display: inline-block;width:"+ (spaceWidth * treeNode.level) + "px'></span>";
				switchObj.before(spaceStr);
			}
		},
		beforeExpand : function(treeId, treeNode) {
			var pNode = demoContent.curExpandNode ? demoContent.curExpandNode.getParentNode() : null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode(): null;
			for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
				if (treeNode !== treeNodeP.children[i]) {
					demoContent.zTree_Menu.expandNode(treeNodeP.children[i],false);
				}
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				demoContent.singlePath(treeNode);
			}
		},
		singlePath : function(newNode) {
			if (newNode === demoContent.curExpandNode)
				return;
			if (demoContent.curExpandNode
					&& demoContent.curExpandNode.open == true) {
				if (newNode.parentTId === demoContent.curExpandNode.parentTId) {
					demoContent.zTree_Menu.expandNode(demoContent.curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === demoContent.curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents != null) {
						var oldNode = demoContent.curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length > 0) {
							demoContent.zTree_Menu.expandNode(oldParents[Math.abs(oldParents.length- newParents.length) - 1], false);
						} else {
							demoContent.zTree_Menu.expandNode(
									oldParents[oldParents.length - 1], false);
						}
					}
				}
			}
			demoContent.curExpandNode = newNode;
		},
		onExpand : function(event, treeId, treeNode) {
			demoContent.curExpandNode = treeNode;
		},
		beforeClick : function(treeId, treeNode) {
			if (treeNode.level == 0) {
				demoContent.zTree_Menu.expandNode(treeNode, null, null, null,true);
			}
			return false;
		}
	}
	demoContent._init();
}

//from the json data value to tree node data
function jsonToNodesMm(data, nodes) {
	if (data != null) {
		var categories = data.children;
		if (categories != null) {
			for (var i = 0; i < categories.length; i++) {
				nodes.push({id : categories[i].id,pId : data.id,name : categories[i].name,iconSkin : categories[i].iconSkin,url : (categories[i].uri != null ? categories[i].uri : "#").split(",")[0],target : '_self'});
				if (categories[i].children != null&& categories[i].children.length > 0) {
					jsonToNodesMm(categories[i], nodes);
				}
			}
		}
	}
	return nodes;
}

/*
 * 通用树状菜单 带编辑、删除按钮
 * 
 * 页面结构举例: 
 * <div class="span3">
 * 	<ul id="categoryTree" class="ztree green_tree"></ul>
 * </div>
 * <div class="span9">
 * 	<ul id="dbs" class="show_sel_itme clearfix">
 * 		<h3 class='alert alert-info' >请选择左侧分类以查看数据库</h3>
 * 	</ul>
 * </div>
 * 
 * 其中:
 * ztree和green_tree为树状菜单的呈现位置的样式;
 * show_sel_itme为要呈现的树状菜单每个节点下数据出现的目标区域.
 * id依据自己需要命名.
 * 
 * 参数说明: 
 * objTreeId：树状列表要显示的位置的元素对象,必填;
 * objzNodes:树状列表的加载json数据对象,必填;
 * iSCanSelParent: 是否可选中父节点，true则是可选中，false则不可以，选填;
 * fnOnClick：列表的每个节点点击时的回调函数，选填。空则填null;
 * strUrl：节点上的编辑和删除按钮的url的前部分，选填，空则填写""，删除和修改按钮则不出现。
 * addTreeBtn：添加树节点的按钮的元素对象,如$("#add_btn"),选填，空则填写null
 * fnAddTreeBtn:添加树节点的处理函数，可以传递按钮的href属性值、所选节点的id以及是否为父节点,选填，空则填写null
 * addDbBtn:向树的某个节点添加数据/数据库的按钮的元素对象,如$("#add_data_btn").空则填写null
 * fnAddDbBtn：向树的某个节点添加数据/数据库的处理函数，可以传递按钮的href属性值、所选节点的id以及是否为父节点,空则填写null
 * isShowLine:是否显示节点之间的连线，当使用green_tree的时候，填false或者不填，当使用green_tree_line的时候，填true *
 */
function menuTreeCom(objTreeId, objzNodes, iSCanSelParent, fnOnClick, strUrl,addTreeBtn, fnAddTreeBtn, addDbBtn, fnAddDbBtn, isShowLine) {
	if (isShowLine || objTreeId.hasClass("green_tree_line")) {
		var showLine = true;
	} else if (objTreeId.hasClass("green_tree")) {
		var showLine = false;
	} else {
		var showLine = false;
	}
	if ((strUrl == "") || (strUrl == null)) {
		var dbSetting = {
			view : {
				showLine : showLine,
				showIcon : false,
				selectedMulti : false,
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : false
				}
			},
			callback : {
				onClick : CateListOnClick
			}
		};
	} else {
		var dbSetting = {
			edit : {
				enable : true,
				removeTitle : "删除",
				showRenameBtn : false,
				drag : {
					isCopy : false,
					isMove : false
				}
			},
			view : {
				addHoverDom : addHoverDom,
				removeHoverDom : removeHoverDom,
				showLine : showLine,
				showIcon : false,
				selectedMulti : false,
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : false
				}
			},
			callback : {
				onClick : CateListOnClick,
				beforeRemove : zTreeBeforeRemove
			}
		};

		//callback edit function
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag|| $("#reModBtn_" + treeNode.id).length > 0)
				return;
			var addStr = "<span href='"+ strUrl+ treeNode.id+ "/edit"+ "' class='button reMod pop_link cboxElement' id='reModBtn_"+ treeNode.id + "' title='修改''></span>";
			sObj.after(addStr);
			var btn = $("#reModBtn_" + treeNode.id);
		};
		function removeHoverDom(treeId, treeNode) {
			$("#reModBtn_" + treeNode.id).unbind().remove();
		};

		//callback delete function
		function zTreeBeforeRemove(treeId, treeNode) {
			$(".noty_bar").hide().click();
			if (confirm("确定删除?")) {
				var result = false;
				$.ajax({
					url : strUrl + treeNode.id + "/delete",
					type : 'POST',
					cache : false,
					async : false,
					success : function(data) {
						if (data.error) {
							noty({"text" : data.msg,"layout" : "center","type" : "error","closeWith" : [ 'click' ],"animateOpen" : {"opacity" : "show"}});
							return false;
						} else {
							result = true;
						}
					}
				})
				return result;
			} else
				return false;
		}
	}

	function CateListBeforeClick(treeId, node) {
		var zTreeIdStr = objTreeId.attr("id");
		var db_zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		if (node.isParent) {
			db_zTree.expandNode(node);
			var result = true
			if (!iSCanSelParent) {
				result = false;
			}
			return result;
		}
	}

	function CateListOnClick(e, treeId, node) {
		if (fnOnClick != null) {
			fnOnClick(node.id, node.isParent, node.isDir, node);
		}
	}

	// 初始化数据库分类树
	function initCategoryTree(objzNodes) {
		$.fn.zTree.init(objTreeId, dbSetting, objzNodes.children);
		var zTreeIdStr = objTreeId.attr("id");
		var db_zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		if ((addTreeBtn != "") && (fnAddTreeBtn != null)) {
			addTreeBtn.click(function() {
				var thishref = $(this).attr("href");
				if (db_zTree.getSelectedNodes()[0]) {
					var selCatNodeid = db_zTree.getSelectedNodes()[0].id;
					fnAddTreeBtn(thishref, selCatNodeid, true)
					return false;
				} else {
					fnAddTreeBtn(thishref, selCatNodeid, false)
					return false;
				}
			})
		}

		// 向节点添加数据
		if ((addDbBtn != "") && (fnAddDbBtn != null)) {
			addDbBtn.click(function() {
				var thishref = $(this).attr("href");
				if (db_zTree.getSelectedNodes()[0]) {
					var selCatNodeid = db_zTree.getSelectedNodes()[0].id;
					var isParent = db_zTree.getSelectedNodes()[0].isParent;
					fnAddDbBtn(thishref, selCatNodeid, true, isParent)
					return false;
				} else {
					fnAddDbBtn(thishref, selCatNodeid, false)
					return false;
				}
			})
		}
	}
	initCategoryTree(objzNodes);
}

/*
 * 从json结构转换成tree node节点数据
 */
function jsonToNodes(data, nodes) {
	if (data != null && data != 0) {
		var categories = data.children;
		if (categories != null) {
			for (var i = 0; i < categories.length; i++) {
				if (categories[i].nocheck) {
					nodes.push({
						id : categories[i].id,
						pId : data.id,
						name : categories[i].name,
						nocheck : categories[i].nocheck
					});
				} else {
					nodes.push({
						id : categories[i].id,
						pId : data.id,
						name : categories[i].name
					});
				}
				if (categories[i].children != null
						&& categories[i].children.length > 0) {
					jsonToNodes(categories[i], nodes);
				}
			}
		}
	}
	return nodes;
}

// 通用树状菜单结束
function isArray(obj) {
	return (typeof obj == 'object') && obj.constructor == Array;
}

/*
 * 通用下拉选择列表-多选
 * 
 * 例子:
 *  <div class="controls">
 *  	<span class="btn ml10 allAuthor disabled">完全权限</span>
 * 	<span class="btn ml10 notAllAuthor ">详细定制</span>
 * 	<input class="treeSel "  type="text" readonly value="" />
 * 	<form:hidden path="treeSelId" class="treeSelId" name="treeSelId" value="" /> 
 * 	<a class="menuBtn btn"  href="#">选择</a>
 * </div>
 */
function treeSleCom(objTreeId, objzNodes, fnHide) {
	var settingTreeNew = {
		check : {
			enable : true,
			chkboxType : {"Y" : "ps","N" : "ps"}
		},
		view : {dblClickExpand : false},
		data : {simpleData : {enable : true}},
		callback : {beforeClick : beforeClickTreeNew,onCheck : onCheckTreeNew}
	};

	function beforeClickTreeNew(treeId, treeNode) {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		zTree.expandNode(treeNode);
		return false;
	}
	function onCheckTreeNew(e, treeId, treeNode) {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		var nodes = zTree.getCheckedNodes(true);
		v = "";
		w = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			var halfCheck = nodes[i].getCheckStatus().half;
			if (halfCheck == false) {
				v += nodes[i].name + ",";
			}
			if (nodes[i].id != "-1" && nodes[i].id != "-2"
					&& nodes[i].id != "-3" && nodes[i].id != "-4") {
				if (i == (nodes.length - 1)) {
					w += nodes[i].id;
				} else {
					w += nodes[i].id + ",";
				}

			}
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var newTreeObj = objTreeId.parent().parent().find("input.treeSel");
		var newTreeObjHid = objTreeId.parent().parent().find("input.treeSelId");
		newTreeObj.attr("value", v);
		newTreeObjHid.attr("value", w);
		if (fnHide != null) {
			fnHide(w);
		}
	}

	// 下拉选择列表初始化
	if (isArray(objzNodes) == true) {
		$.fn.zTree.init(objTreeId, settingTreeNew, objzNodes);
	} else {
		var nodesArray = new Array();
		nodesArray = jsonToNodes(objzNodes, nodesArray);
		$.fn.zTree.init(objTreeId, settingTreeNew, nodesArray);
	}
	objTreeId.parent().parent().find("input.treeSel").click(function() {
		showMenuGroupNew($(this));
	});
	objTreeId.parent().parent().find("a.menuBtn").click(function() {
		showMenuGroupNew(objTreeId.parent().parent().find("input.treeSel"));
	});

	// 全选或全不选
	objTreeId.parent().find(".sel_all").click(function() {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		zTree.checkAllNodes(true);
		onCheckTreeNew();
		return false;
	});
	objTreeId.parent().find(".sel_none").click(function() {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		zTree.checkAllNodes(false);
		onCheckTreeNew();
		return false;
	});

	//加载已经选的后台功能树
	var treeSelIds = objTreeId.parent().siblings().find(".treeSelId").val();
	if (treeSelIds != "") {
		objTreeId.parent().parent().find(".treeSelId").click(function() {
				var zTreeIdStr = objTreeId.attr("id");
				var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
				var treeSelIdsArry = treeSelIds.split(",");
				for (var i = 0, l = treeSelIdsArry.length; i < l; i++) {
					var oldnodes = zTree.getNodesByParam("id",treeSelIdsArry[i], null);
					zTree.checkNode(oldnodes[0], true, false, true);
				}
			});
	}
}

/*
 * 显示下拉菜单 
 */
function showMenuGroupNew(treeSel) {
	var newTreeObj = treeSel;
	var newTreeOffset = treeSel.offset();
	var menuContent = treeSel.parent().parent().find(".menuContent");
	if (treeSel.parent().parent().css("position") == "relative") {
		menuContent.css({
			left : newTreeObj.css("left"),
			top : parseInt(newTreeObj.css("top"))+ newTreeObj.outerHeight() + "px"
		}).slideDown("fast");
	} else {
		menuContent.css({
			left : newTreeOffset.left + "px",
			top : newTreeOffset.top + newTreeObj.outerHeight() + "px"
		}).slideDown("fast");
	}
	menuContent.find(".selOk").click(function() {
		hideMenuGroupNew();
		return false;
	});
	$("body").bind("mousedown", onBodyDownGroupNew);
}

/*
 * 隐藏下拉菜单
 */
function hideMenuGroupNew() {
	$(".menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownGroupNew);
}

/*
 *绑定事件 
 */
function onBodyDownGroupNew(event) {
	if (!($(event.target).hasClass("menuBtn")|| $(event.target).hasClass("treeSel")|| $(event.target).hasClass("treeRadio")|| $(event.target).hasClass("menuContent")|| $(event.target).hasClass("sel_all")|| $(event.target).hasClass("sel_none") || $(event.target).parents(".menuContent").length > 0)) {
		hideMenuGroupNew();
	}
}

/*
 * 通用下拉选择列表 单选
 * 
 * 例子:
 *<div class="controls">
 *		<input class="treeRadio" type="text" readonly  value="" />
 *		<form:hidden path="parentID" class="treeSelId" name="treeSelId" value="" />
 *</div>
 */
function treeRadioCom(objTreeId, objzNodes, isOnlyChild) {
	var dbSettingTreeNew = {
		check : {},
		view : {dblClickExpand : false,selectedMulti : false},
		data : {simpleData : {enable : true}},
		callback : {
			beforeClick : beforeClickTreeNew,
			onClick : onClick
		}
	};
	function beforeClickTreeNew(treeId, treeNode) {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		if (isOnlyChild == true) {
			if (treeNode.isParent == true) {
				zTree.expandNode(treeNode);
				return false;
			}
		}
		return;
	}
	function onClick(e, treeId, treeNode) {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		var nodes = zTree.getSelectedNodes();
		v = "";
		w = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v = nodes[i].name;
			w = nodes[i].id;
		}
		var newTreeObj = objTreeId.parent().parent().find("input.treeRadio");
		var newTreeObjHid = objTreeId.parent().parent().find("input.treeSelId");
		newTreeObj.attr("value", v);
		newTreeObjHid.attr("value", w);
		newTreeObjHid.change();
	}
	function onNodeCreated(e, treeId, treeNode) {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		var nodes = zTree.getSelectedNodes();
		v = "";
		w = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v = nodes[i].name;
			w = nodes[i].id;
		}
		var newTreeObj = objTreeId.parent().parent().find("input.treeRadio");
		var newTreeObjHid = objTreeId.parent().parent().find("input.treeSelId");
		newTreeObj.attr("value", v);
		newTreeObjHid.attr("value", w);
	}
	// 下拉选择列表初始化
	$.fn.zTree.init(objTreeId, dbSettingTreeNew, objzNodes);
	objTreeId.parent().parent().find("input.treeRadio").click(function() {
		showMenuGroupNew($(this));
	});
	objTreeId.parent().parent().find("a.menuBtn").click(function() {
		showMenuGroupNew(objTreeId.parent().parent().find("input.tr+eeRadio"));
	});
	// 依据value加载已经选的后台功能树
	var treeSelIds = objTreeId.parent().siblings().find(".treeSelId").val();
	if (treeSelIds && treeSelIds != "") {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		var newTreeObj = objTreeId.parent().parent().find("input.treeRadio");
		var oldnodes = zTree.getNodesByParam("id", treeSelIds, null);
		zTree.selectNode(oldnodes[0], false);
		if(oldnodes[0]!='undefined'&&oldnodes[0]!=null){
			var beSelName = oldnodes[0].name;
		}
		newTreeObj.attr("value", beSelName);
	}
}

/**
 * 分级平铺模式展示 
 * @param objTileId 为树状平铺列表目标对象ul 
 * @param data为json数据
 */
function comTileList(objTileId, data) {
	var leveMulti = 0;
	objTileId.html(comTileListT(data), leveMulti)
	comTileListHover(objTileId);
}
function comTileListT(Obj, lm) {
	var data = Obj.children;
	var rootName = Obj.name;
	var dataLen = data.length;
	if (data != null) {
		var leveMultiTmp = 0;
		var leveMultiTmpMin = 0;
		for (var i = 0; i < dataLen; i++) {
			if (data[i].children != null && data[i].children.length > 12) {
				leveMultiTmp++;
			}
			if (data[i].children != null && data[i].children.length >= 3) {
				leveMultiTmpMin++;
			}
		}
		var tmp = "";
		for (var i = 0; i < dataLen; i++) {
			var name = data[i].name;
			var length = data[i].length;
			var objToDomTmp = "";
			if (data[i].children != null && data[i].children.length > 0) {
				var theClass = "";
				if (((dataLen <= 6) && (leveMultiTmp > 0))|| ((dataLen <= 6) && (leveMultiTmpMin < 1))) {
					theClass = 'class="def_show"';
				} else {
					theClass = 'class="def_hide"';
				}
				objToDomTmp = '<ul ' + theClass + '>'+ comTileListT(data[i], leveMultiTmp) + '</ul>'
			}
			if (((dataLen <= 6) && (leveMultiTmp > 0))|| ((dataLen <= 6) && (leveMultiTmpMin < 1))) {
				tmp += '<li class="wide"><label class="clearfix"><a href="#">'+ name + '</a></label>' + objToDomTmp + '</li>'
			} else {
				if (objToDomTmp == "") {
					tmp += '<li class="mini"><a href="#">' + name + '</a></li>'
				} else {
					tmp += '<li class="mini"><a href="#">' + name+ '<i class="hideIcon"></i></a>' + objToDomTmp+ '</li>'
				}
			}
		}
	}
	return tmp;
}

// 样式变化以hover导航菜单
function comTileListHover(objTileId) {
	objTileId.find("li").live("mouseenter",function() {
		var isChildHide = $(this).children(".def_hide").length;
		var isParentShow = $(this).parent().hasClass("def_show");
		var hCha = $(this).offset().top- $(this).parent().offset().top;
		var width = $(this).width() - 2;
		var leftCha = $(this).offset().left- $(this).parent().offset().left;
		if (isParentShow) {}
			if (isChildHide > 0) {
				$(this).siblings().removeClass("active");
				$(this).addClass("active").children().addClass("curt").children(".hideIcon").addClass("icurt");
				$(this).children(".def_hide").show().css("top",28 + hCha + "px").append('<i class="mark" style="width:'+ width + 'px;left:'+ leftCha + 'px"></i>');
			}
	});
	objTileId.find("li").live("mouseleave",function() {
		$(this).removeClass("active").children().removeClass("curt").children(".hideIcon").removeClass("icurt");
		$(this).children(".def_hide").hide().children(".mark").remove();
	});
}

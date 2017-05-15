/**
 * 通用下拉选择列表
 * 
 * @author shishb
 * @version 1.0
 */
function treeSleCom(objTreeId, objzNodes, fnHide) {
	var settingTreeNew = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeClickTreeNew,
			onCheck : onCheckTreeNew
		}
	};

	/**
	 * 绑定事件
	 * 
	 * @param treeId 
	 * @param treeNode
	 * */
	function beforeClickTreeNew(treeId, treeNode) {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		zTree.expandNode(treeNode);
		return false;
	}

	/**
	 * 绑定事件
	 * 
	 * @param e
	 * @param treeId 
	 * @param treeNode
	 * */
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
					&& !nodes[i].isParent) {
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
	var nodesArray = new Array();
	nodesArray = jsonToNodes(objzNodes, nodesArray);
	$.fn.zTree.init(objTreeId, settingTreeNew, nodesArray);
	objTreeId.parent().parent().find("input.treeSel").click(function() {
		showMenuGroupNew($(this));
	});
	objTreeId.parent().parent().find("a.menuBtn").click(function() {
		showMenuGroupNew(objTreeId.parent().parent().find("input.treeSel"));
	});
	// 全选 /全不选
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

	// 依据value加载已经选的后台功能树
	var treeSelIds = objTreeId.parent().siblings().find(".treeSelId").val();
	if (treeSelIds != "") {
		objTreeId.parent().parent().find(".treeSelId").click(
				function() {
					var zTreeIdStr = objTreeId.attr("id");
					var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
					var treeSelIdsArry = treeSelIds.split(",");
					for (var i = 0, l = treeSelIdsArry.length; i < l; i++) {
						var oldnodes = zTree.getNodesByParam("id",
								treeSelIdsArry[i], null);
						zTree.checkNode(oldnodes[0], true, false, true);
					}
				});
	}
}

/**
 * 通用下拉选择列表 单选
 * 
 * @param objTreeId:$("#type_tree .treeNew") 
 * @param objzNodes:data //节点数据
 * @param isOnlyChild:true/false 用于选择是否只能 选择子节点
 *例子:
 * <div class="control-group backhide" class="tree_sel_box" id="type_tree">
 * 	<label class="control-label" for="u_group_memo">分类</label>
 * 	<div class="controls">
 * 		<input class="treeRadio" type="text" readonly value="" />
 * 		<label class="error"><form:errors path="parentID" cssClass="error" /></label>
 * 		<form:hidden path="parentID" class="treeSelId" name="parentID" value="" />
 *		</div>
 *		<div class="menuContent">
 *			 <ul id="treeSel_1" class="ztree treeNew"></ul>
 * 		<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i> 确定</a>
 * 	</div>
 * </div>
 * (1)：hidden input中的value用于回显既选项，如果希望显示既选项，则赋值其节点id，逗号分隔。
 * (2)：如果页面有多个选择控件，id="treeSel_1",id="treeSel_2"
 */
function treeRadioCom(objTreeId, objzNodes, isOnlyChild) {
	// 设置新菜单列表
	var dbSettingTreeNew = {
		check : {
		// enable: true,
		// chkStyle: "radio",
		// radioType: "all"
		},
		view : {
			dblClickExpand : false,
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
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
		showMenuGroupNew(objTreeId.parent().parent().find("input.treeRadio"));
	});

	// 依据value加载已经选的后台功能树
	var treeSelIds = objTreeId.parent().siblings().find(".treeSelId").val();
	if (treeSelIds == "") {

	} else {
		var zTreeIdStr = objTreeId.attr("id");
		var zTree = $.fn.zTree.getZTreeObj(zTreeIdStr);
		var newTreeObj = objTreeId.parent().parent().find("input.treeRadio")
		var oldnodes = zTree.getNodesByParam("id", treeSelIds, null);
		zTree.selectNode(oldnodes[0], false);
		var beSelName = oldnodes[0].name;
		newTreeObj.attr("value", beSelName);
	}
}

/* 下拉菜单选择显示控制 */
function showMenuGroupNew(treeSel) {
	var newTreeObj = treeSel;
	var newTreeOffset = treeSel.offset();
	var menuContent = treeSel.parent().parent().find(".menuContent");
	hideMenuGroupNew();
	menuContent.css({
		// left : newTreeOffset.left + "px",
		// top : newTreeOffset.top + newTreeObj.outerHeight() + "px"
		left : newTreeObj.css("left"),
		top : parseInt(newTreeObj.css("top")) + newTreeObj.outerHeight() + "px"
	}).slideDown("fast");
	menuContent.find(".selOk").click(function() {
		hideMenuGroupNew();
		return false;
	});
	$("body").bind("mousedown", onBodyDownGroupNew);
}
function hideMenuGroupNew() {
	$(".menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownGroupNew);
}
function onBodyDownGroupNew(event) {
	if (!($(event.target).hasClass("menuBtn")
			|| $(event.target).hasClass("treeSel")
			|| $(event.target).hasClass("treeRadio")
			|| $(event.target).hasClass("menuContent")
			|| $(event.target).hasClass("sel_all")
			|| $(event.target).hasClass("sel_none") || $(event.target).parents(
			".menuContent").length > 0)) {
		hideMenuGroupNew();
	}
}

/*
 * 从json结构转换成tree node节点数据
 */
function jsonToNodes(data, nodes) {
	if (data != null && data != 0) {
		var categories = data.children;
		if (categories != null) {
			for (var i = 0; i < categories.length; i++) {
				// if (data.id == 0) {
				// var isOpen = true;
				// } else {
				// var isOpen = false;
				// }
				if (categories[i].nocheck) {
					nodes.push({
						id : categories[i].id,
						pId : data.id,
						name : categories[i].name,
						nocheck : categories[i].nocheck
					// open : isOpen
					});
				} else {
					nodes.push({
						id : categories[i].id,
						pId : data.id,
						name : categories[i].name
					// open : isOpen
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
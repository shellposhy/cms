/**
 * 页面模板配置管理通用脚本
 * 
 * @author shishb
 * @version 1.0
 */
function initColumn() {
	$.ajax({
		url : appPath + "/admin/column/tree",
		success : function(data) {
			treeRadioCom($("#itemContentSrc .treeNew"), data.children, true);
			setTimeout("$('itemContentSrc.treeSel').click()", 800);
		}
	});
}

$("#filter_witch").change(function() {
	if($("#filter_witch").attr("checked")){
		$("#filter_tr").show();
		$("#switch_value").val("Normal");
	}else{
		$("#filter_tr").hide();
		$("#filterCondition").val("");
		$("#switch_value").val("Stop");
	}
});

//使得iframe全部高度内容出现并绑定页面初始化事件
function iframeFix(obj) { 
	var hbody = $(iFramePageConf.document).find("#container").height();
	if(null==hbody||hbody==''||typeof(hbody) == "undefined"){
		//如果默认height没有设置则默认值为1000
		hbody=1000;
	}
	obj.height = hbody;
	var bodyObj = $(iFramePageConf.document).find("body");
	// 绑定iframe中的编辑配置按钮
	PageConfBuind(bodyObj);
}

// 显示可配置区域的"配置"按钮
function PageConfBuind(bodyObj) {
	bodyObj.find("[ecode]").hover(function() {
		var tWidth = $(this).outerWidth(), theight = $(this).outerHeight() - 1;
		if ($(this).find(".editable_btn").length < 1) {
			$(this).css({"position" : "relative"}).append('<div class="editable_warp" style="width:'+ tWidth+ 'px;height:'+ theight+ 'px;background:#fff;opacity:0.8;left:0;top:0;position: absolute;z-index:100"></div><a class="editable_btn" style="display:inline-block;padding:3px 10px; border:1px solid #ccc;border-radius:4px; position:absolute;right:10px;top:5px;z-index:101;background:#f6f6f6;line-height: 18px;font-size: 12px;font-weight: normal;color: #303030;width: auto;" href="#"><i class="icon-edit"></i> 配置</a>');
		} else {
			$(this).css({"position" : "relative"}).find(".editable_btn,.editable_warp").show();
		}
	},function() {
		if ($(this).find(".editable_btn").length > 0) {
			$(this).find(".editable_btn,.editable_warp").hide();
		}
	});

	// 绑定编辑按钮弹出配置浮出框
	$('#areaConfModal').on('show', function() {});
	$('#areaConfModal').on('hidden', function() {
		// 弹出层隐藏时候清空选择框
		$("#itemContentDb input").each(function() {
			$(this).val("");
		});
	});
	bodyObj.find("[ecode]").find(".editable_btn").live("click",function(e) {
		stopDefault(e);
		$('#areaConfModal').modal('show');
		$("#curItemId").val($(this).parents("[ecode]").attr("ecode"));
		// 保存配置表单验证
		itemSaveFormV();
		initContentHtml($("#curItemId").val());
		// 弹出层出现时候加载选择框来源数据
		initData();
	});

	$("#btn_save").click(function() {
		$("#viewItemForm").submit();
	});

}

// 加载内容来源树
function initData() {
	var url = appPath + "/admin/view/content/" + $("#pageId").val() + "/"+ $("#curItemId").val();
	$.ajax({
		url : url,
		cache:false,
		success : function(data) {
			if(null != data.content){
				$("#name").val(data.content.name);
				$(".treeSelId").val(data.content.content);
				$("#filterCondition").val(data.content.filterCondition);
				$("#nameLinkType").val(data.content.nameLinkType);
				$("#nameLink").val(data.content.nameLink);
			}else{
				$("#name").val("");
				$(".treeSelId").val(0);
				$("#filterCondition").val("");
				$("#nameLinkType").val("NormalListLink");
				$("#nameLink").val("");
			}
			intiLinkType();
			filterSwitch();
			treeRadioCom($("#itemContentSrc .treeNew"), data.root.children,true);
			setTimeout("$('.treeSelId').click()", 800);
		}
	});
}


//用户编辑 状态开关
function filterSwitch() {
	if ($("#filterCondition").val() != "") {
		$("#filter_witch").attr("checked", true);
		$("#filter_witch").parent().attr("class", "checked");
	}
	$("#filter_witch").change();
}

function intiLinkType(){
	if("UserLink" == $("#nameLinkType").val()){
		$("#nameLinkType_opt_0").attr("checked");
		$("#nameLinkType_opt_0").parent().attr("class", "checked");
		$("#nameLinkType_opt_2").removeAttr("checked");
		$("#nameLinkType_opt_2").parent().removeAttr("class");
		linkTypeChange();
	}
}

function linkTypeChange(){
	if($(".nameLinkType_opt:checked").val()== "UserLink"){
		$("#link_tr").show();
		$("#nameLinkType").val("UserLink");
	}else{
		$("#link_tr").hide();
		$("#nameLinkType").val($(".nameLinkType_opt:checked").val());
		$("#nameLink").val("");
	}
}

// 加载内容来源树
function initContentHtml(code) {
	var itemCode = $("#curItemId").val();
	var pageId = $("#pageId").val();
	$.ajax({
		url : appPath + "/admin/view/model/contentHtml/" + pageId + "?itemCode="+ itemCode,
		success : function(data) {
			$('#contentHtml').val(data);
		}
	});
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
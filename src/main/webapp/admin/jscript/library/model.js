/**
 * 数据库模板通用脚本
 * @author shishb
 * @version 1.0
 */

$(document).ready(function() {
	column_dt();
	init_load_field();
	model_form_validate();
});

//通用表格数据
var oTableDataPage;
function column_dt() {
	oTableDataPage = $("#column_tab").dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "full_numbers",
		"bProcessing" : true,
		"bServerSide" : true,
		"bDestroy" : true,
		"bRetrieve" : true,
		"bSort" : false,
		"sAjaxSource" :"model/s",
		"fnServerData" : retrieveData,
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
		"aoColumns" : [{
			"mData" : "name",
			"fnRender" : function(obj) {
				var title = obj.aData.title;
				name = '<span>'+ obj.aData.name + '</span>';
				var editBtn = "";
				if(obj.aData.forSys!=true){
					editBtn = '<button title="编辑" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i></button>'
				}
				return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id
						+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" ></label>'+ name+ editBtn;
			}
			}, {
				"mData" : "type"
			}, {
				"mData" : "describe"
			} ],
		"fnDrawCallback" : callback_model
	});
}

//回调函数
function callback_model() {
	docReady();
	trHoverEdit(showItemEdit);
	trHoverModi();
	listDelete("model/delete", oTableDataPage); 
};

// 编辑
function showItemEdit() {
	$(".trHoverEdit tr .editbtn").live("click", function() {
		var id = $(this).parent().find("input[type='checkbox']").val();
		window.location.href = "model/" + id + "/edit";
	});
}

// 绑定到选择字段组合 -高级配置
function init_load_field() {
	if ($("#moreFields").length > 0) {
		// 设置值
		$("#moreFieldsSelect").change(function() {
			$("#moreFields").val($(this).val());
		});
		// 显示默认值
		var trSIdArry = ($("#moreFields").val()).split(",");
		var options = $("#moreFieldsSelect option");
		options.each(function() {
			for ( var j = 0; j < trSIdArry.length; j++) {
				if ($(this).val() == trSIdArry[j]) {
					$(this).attr("selected", "true");
				}
			}
		});
	}
}

// 表单验证
function model_form_validate() {
	if($("#cl_new_form")){
		$("#cl_new_form").validate(
				{
					rules : {
						name : {
							required : true
						},
						code : {
							required : true
						},
						moreFieldsSelect : {
							required : function() {
								if ($("#moreFields").val() == null
										|| $("#moreFields").val() == "") {
									return false;
								} else {
									return true;
								}
							}
						}
					},
					messages : {
						name : {
							required : "栏目名称不能为空！"
						},
						code : {
							required : "栏目编码不能为空！"
						},
						moreFieldsSelect : {
							required : "字段组号不能为空！"
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
}

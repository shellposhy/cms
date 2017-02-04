/**
 * 系统参数通用脚本
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	param_list();
});

// 初始化化表内容
function param_list() {
	var aoColumnsArry = [
			{
				"mData" : "name",
				"fnRender" : function(obj) {
					return '<label class="checkbox none"><input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id
							+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ '</label><label class="inline" style="padding:4px 0">'+ obj.aData.name
							+ '</label><button title="点击进行配置" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i></button>';
				}
			}, {
				"mData" : "paramType",
			}, {
				"mData" : "code"
			}, {
				"mData" : "value"
			} ];
	dataTablesCom($('#paramList'), "/admin/param/s",aoColumnsArry, null, callback_param, true, false, true);

}

//表格回调函数
function callback_param(otd) {
	docReady();
	trHoverEdit();
	editPopWithDT(otd);
}
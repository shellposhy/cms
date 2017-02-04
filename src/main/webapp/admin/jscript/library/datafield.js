/**
 * 数据库字段通用脚本处理
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	load_field_data();
});

// 定义公共参数
var thisPath = appPath + "/admin/data/";

// 加载表格数据
function load_field_data() {
	var objTitle = [ {
		"mData" : "id",
		'fnRender': function(obj){
			return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'
			+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>'
			+ '<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
		}
	},  {"mData" : "code"}, {"mData" : "dataType"}, {"mData" : "nosg"}, {"mData" : "leng"}, {"mData" : "prec"}, {"mData" : "mand"}, {"mData" : "uniq"
	}, {"mData" : "multiValue"}, {"mData" : "indexType"}, {"mData" : "type"}, {"mData" : "accessType"}, {"mData" : "forOrder"}];
	dataTablesCom($('#dataField'), "field/s", objTitle, null, callback_data,false, false, true);
}

// 回调函数
function callback_data(oTableDataDb) {
	docReady();
	//trHoverEdit();
	listDelete(thisPath + "field/delete", oTableDataDb);
	editPopWithDT(oTableDataDb);
};
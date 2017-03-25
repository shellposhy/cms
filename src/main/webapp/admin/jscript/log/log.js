$(document).ready(function() {
	accessLogForMng();
	datepickerForLog();
});

function aoDateParams() {
	var dateStr = $(".datepicker").val();
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var monthStr = "0" + month;
	var dayStr = "0" + day;
	var nowDateStr = myDate.getFullYear() + "-"+ monthStr.substring(monthStr.length - 2, monthStr.length) + "-"+ dayStr.substring(dayStr.length - 2, dayStr.length);
	if (/([\d]{4})\-([\d]{2})\-([\d]{2})/.test(dateStr)) {
		return dateStr
	} else {
		return nowDateStr
	}
}
var oTablelog;
function accessLogForMng() {
	oTablelog = $('#accessLogMng').dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "bootstrap",
		"bProcessing" : true,
		"bServerSide" : true,
		" bAutoWidth" : false,
		"bSort" : false,
		"sAjaxSource" : "../" + $("#logType").val() + "/s",
		"fnServerData" : retrieveData,
		"fnServerParams" : function(aoData) {aoData.push({"name" : "day","value" : aoDateParams()});},
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : "../../../../admin/js/javascript/de_CN.js"},
		"aoColumns" : [
		        {"mData" : "userName"},
				{"mData" : "url","fnRender" : function(obj) {return '<div class="log_url" title="'+ obj.aData.url + '">'+ obj.aData.url + '</div>'}}, 
				{"mData" : "logAction"}, 
				{"mData" : "targetName"},
				{"mData" : "ip"},
				{"mData" : "dateStr"}]
	});
};


function selectType(){
	var type=document.getElementById("type");
	var value=type.options[type.selectedIndex].text;
}

// 日期选择回调设置
function datepickerForLog() {
	$(".datepicker").datepicker({
		onSelect : function(dateText, inst) {
			oTablelog.fnDraw();
		}
	});
	$(".datepicker").datepicker("setDate", new Date())
}
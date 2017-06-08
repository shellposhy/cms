/**
 * 任务列表通用脚本
 * 
 * @author shishb
 */
$(document).ready(function() {
	task_list();
});

// 初始化dataTable
var oTableDataPage;
function task_list() {
	if ($("#taskList").length > 0) {
		var objTitle = [
				{
					"mData" : "id",
					'fnRender' : function(obj) {
						return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'
								+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label>'
								+ '<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
					}
				},
				{
					"mData" : "taskType"
				},
				{
					"mData" : "progress",
					'fnRender' : function(obj) {
						var progress = obj.aData.progress;
						var progressCss = 'progress-success';
						if (progress >= 50 && progress < 100) {
							progressCss = 'progress-warning';
						} else if (progress < 50) {
							progressCss = 'progress-danger';
						}
						return "<div class='progress progress-striped "+progressCss+" active'><div class='bar' style='width: "+ obj.aData.progress + "%'></div></div>";
					}
				}, {
					"mData" : "taskStatus"
				}, {
					"mData" : "createTime"
				}, {
					"mData" : "updateTime"
				} ];
		dataTablesCom($('#taskList'), "task/s", objTitle, null, callback_data,false, false, true);
	}
}

// dataTable回调函数
function callback_data(oTableDataDb) {
	docReady();
	editPopWithDT(oTableDataDb);
};
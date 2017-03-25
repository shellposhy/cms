$(document).ready(function() {
	datatime_event();
	count_all_pv();
	hot_hour_pv();
	user_data_count();
	project_finish_list();
});

//统计系统访问量
function count_all_pv(){
	var data = [ {name : "timePeroid",value : $("#timePeroid").val()}, {name : "type",value : "0"}];
	$.ajax({
		type : "POST",
		dataType : "JSON",
		url : appPath + "/admin/report/chart",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(data) {
			if (typeof (resp) != "string") {
				comColumnChart(data, $("#sysPvCount"), $("#timePeroid").val());
			} else {
				$(".error_div").show().html(data)
			}
		}
	});
}

//统计日活跃访问量
function hot_hour_pv(){
	var data = [ {name : "timePeroid",value : $("#timePeroid").val()}, {name : "type",value : "1"}];
	$.ajax({
		type : "POST",
		dataType : "JSON",
		url : appPath + "/admin/report/chart",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(data) {
			if (typeof (data) != "string") {
				comLineChart(data, $("#hotHourPv"));
			} else {
				$(".error_div").show().html(data)
			}
		}
	});
}

//用户数据统计
function user_data_count(){
	var data = [ {name : "timePeroid",value : $("#timePeroid").val()}, {name : "type",value : "2"}];
	$.ajax({
		type : "POST",
		dataType : "JSON",
		url : appPath + "/admin/report/chart",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(resp) {
			if (typeof (resp) != "string") {
				comPieChart(resp, $("#userDataCount"));
			} else {
				$(".error_div").show().html(resp)
			}
		}
	});
}

//完成项目列表
function project_finish_list(){
	var data = [ {name : "timePeroid",value : 11}];
	$.ajax({
		type : "POST",
		dataType : "JSON",
		url : appPath + "/admin/report/list",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(data) {
				if (data.length > 0) {
					var colors = [ "red", "green", "blue", "yellow", "red", "green","blue", "yellow", "green", "blue", "yellow" ]
					var kwLists = "";
					for ( var i = 0; i < data.length; i++) {
						var index = i + 1;
						kwLists += '<li><a href="#"><span class="' + colors[i] + '">'+ index + '</span><strong>' + data[i].name+ '</strong></a></li>'
					}
					$("#projectFinishList").find(".dashboard-list").html(kwLists);
				} else {
					$("#projectFinishList").find(".dashboard-list").html("<h3 class='rep_title'>暂无相关数据</h3>")
				}
		}
	});
}

// 选择时间
function datatime_event() {
	$("[data-toggle='buttons-radio'] .btn").live("click", function() {
		var val = $(this).attr("value");
		$(this).siblings("input[type='hidden']").val(val);
		if ($("#para").val() || (!$("#para").length)) {
			count_all_pv();
			hot_hour_pv();
			user_data_count();
			project_finish_list();
		}
	})
}

//时间对象的格式化
Date.prototype.format = function(format) {
	var o = {"M+" : this.getMonth() + 1,"d+" : this.getDate(),"h+" : this.getHours(),"m+" : this.getMinutes(),"s+" : this.getSeconds(),"q+" : Math.floor((this.getMonth() + 3) / 3),"S" : this.getMilliseconds()}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}


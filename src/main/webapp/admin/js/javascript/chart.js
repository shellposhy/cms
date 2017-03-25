/**
 * 通用js图表生产工具类
 * @author shishb
 * @version 1.0
 */

/**
 * 图表消息显示
 * @param x
 * @param y
 * @param contents
 * */
function showTooltip(x, y, contents) {
	$('<div id="tooltip">' + contents + '</div>').css({
		position : 'absolute',
		display : 'none',
		top : y + 5,
		left : x + 5,
		border : '1px solid #46b8da',
		padding : '2px',
		'background-color' : '#5bc0de',
		color : '#fff',
		opacity : 0.80
	}).appendTo("body").fadeIn(200);
}

/**
 * 饼图通用工具类
 * @param datas
 * @param object
 * @returns 
 * */
function comPieChart(datas, object) {
	if (object.length) {
		if (datas[0].data) {
			var data = datas[0].data;
			var plot = $.plot(object, data, {
				series : {
					pie : {show : true,innerRadius : 0.3,label : {show:true}}
				},
				grid : {hoverable : true,clickable : true},
				legend : {show : true},
				colors : [ "#1485C9", "#ea3d3d", "#539F2E", "#facf21" ]
			});
			var repTitle = datas[0].title;
			var repInfo = datas[0].info;
			object.prev().find(".rep_title").html(repTitle);
			object.prev().find(".rep_info").html(repInfo);
		} else {
			object.empty().prev().find(".rep_title").html("暂无相关数据").siblings(".rep_info").empty();
		}
	}
}

/**
 * 线状通用工具类
 * @param datas
 * @param object
 * @returns 
 * */
function comLineChart(datas, object) {
	if (object.length) {
		if (datas[0].data) {
			var data = datas[0].data[0].data;
			var xaxisOpt = datas[0].data[0].xaxisOpt;
			var plot = $.plot(object, [ data ], {
				series : {
					lines : {show : true},
					points : {show : true}
				},
				grid : {
					hoverable : true,
					clickable : true,
					backgroundColor : {colors : [ "#fff", "#eee" ]}
				},
				xaxis : {ticks : xaxisOpt},
				yaxis : {min : 0},
				colors : [ "#c9302c", "#539F2E" ]
			});
			var previousPoint = null;
			object.bind("plothover",function(event, pos, item) {
				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;
						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(0), y = item.datapoint[1].toFixed(0);
						var d = new Date();
						d.setTime(x)
						showTooltip(item.pageX, item.pageY, x+ "时 " + y + "次");
					}
				} else {
					$("#tooltip").remove();
					previousPoint = null;
				}
			});
			var repTitle = datas[0].title;
			var repInfo = datas[0].info;
			object.prev().find(".rep_title").html(repTitle);
			object.prev().find(".rep_info").html(repInfo);
		} else {
			object.empty().prev().find(".rep_title").html("暂无相关数据").siblings(".rep_info").empty();
		}
	}
}

/**
 * 直方图通用工具类
 * @param datas
 * @param object
 * @param days
 * @returns 
 * */
function comColumnChart(datas, object, days) {
	if (object.length) {
		if (days == "1") {
			object.parentsUntil(".box").parent().hide();
		} else {
			object.parentsUntil(".box").parent().show();
			if (datas[0].data) {
				var data = datas[0].data[0].data;
				var xaxisOpt = datas[0].data[0].xaxisOpt;
				var yaxisOpt = datas[0].data[0].yaxisOpt;
				var stack = 0, bars = true, lines = false, steps = false;
				var plot = $.plot(object, [ data ], {
					series : {
						stack : stack,
						lines : {show : lines,fill : true,steps : steps},
						bars : {show : bars,align : "left",barWidth : 24 * 60 * 60 * 600}
					},
					xaxis : {
						mode : "time",
						timeformat : "%m/%d",
						tickSize : [ 1, "day" ]
					},
					grid : {
						hoverable : true,
						clickable : true,
						backgroundColor : {colors : [ "#fff", "#eee" ]}
					},
					colors : [ "#5cb85c","#31b0d5","#ec971f","#c9302c"]
				});
				var previousPoint = null;
				object.bind("plothover",function(event, pos, item) {
					if (item) {
						if (previousPoint != item.dataIndex) {
							previousPoint = item.dataIndex;
							$("#tooltip").remove();
							var x = item.datapoint[0].toFixed(0), y = item.datapoint[1].toFixed(0);
							var d = new Date();
							d.setTime(x)
							showTooltip(item.pageX, item.pageY,d.format("yyyy年MM月dd日")+ " " + y + "次");
						}
					} else {
						$("#tooltip").remove();
						previousPoint = null;
					}
				});
				var repTitle = datas[0].title;
				var repInfo = datas[0].info;
				object.prev().find(".rep_title").html(repTitle);
				object.prev().find(".rep_info").html(repInfo);
			} else {
				object.empty().prev().find(".rep_title").html("暂无相关数据").siblings(".rep_info").empty();
			}
		}
	}
}
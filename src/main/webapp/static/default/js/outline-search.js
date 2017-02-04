//前端文章检索列表js
$(document).ready(function() {
	init_result_list();// 初始化
	show_summary();// 是否显示摘要
	change_pageNum();// 改变页面每页显示文章数
	daySwicth(); // 日期段切换
	sortSwicth(); // 排序切换
	page_inti();// 初始化结果页面值
});

// 文章查询初始化参数
var summaryinti;// 是否显示摘要
var pageNum;// 每页显示文章数
var curPageNum;// 分页时当前页数
var pageId;// 数据库或数据标签编号
var pageType;// 当前网页类型
var queryStr;// 用户输入的查询串
var queryType;// 用户查询的范围:标题(0)+正文(1)+标题正文(2)
var queryDays;// 查询结果的范围:1天+1周+1月+1年
var querySort;// 查询结果排序
var queryModel;// 查询模式：高级查询(as)普通查询(ns)

// 读取cookie值
pageNum = $.cookie("peopleDataPageMaxSize");
summaryinti = $.cookie("peopleDataPageIsSummary");

// 初始化页面参数
function page_inti() {
	// 查询类型
	$("input[name='ty'][value='" + $("#queryType").val() + "']").attr(
			'checked', true);
	// 页面大小
	if (pageNum == null || pageNum == 0) {
		$("#OutNum").val(20);
	} else {
		$("#OutNum").val(pageNum);
	}
	// 是否显示摘要
	if (summaryinti == null) {
		$("#isSummry").attr('checked', false);
	} else {
		if (summaryinti == 1) {
			$("#isSummry").attr('checked', true);
		} else {
			$("#isSummry").attr('checked', false);
		}
	}
}

// 初始化文章列表
function init_result_list() {
	// 设置查询模式
	queryModel = $("#queryModel").val();
	// 不显示摘要
	if (summaryinti == null) {
		summaryinti = 2;
	}
	// 每页显示20条数据
	if (pageNum == null || pageNum == 0) {
		pageNum = 20;
	}
	curPageNum = 1;// 当前第一页
	querySort = $("#sortUl .visiting").val();// 排序方式
	queryDays = $("#dateTimeUl .visiting").val();// 查询天数
	queryType = $("#queryType").val();// 查询范围
	if (queryModel == "ns") { // 普通查询
		pageId = $("#pageId").val();// 数据库或数据标签编号
		pageType = $("#pageType").val();// 当前网页类型:库页面或标签页或分类页
		queryStr = $("#queryStr").val();// 用户输入的查询串
		page(summaryinti, pageNum, curPageNum, queryStr, queryType, pageType,
				pageId, queryDays, querySort, queryModel);
	} else if (queryModel == "as") {// 高级查询
		var searchIdStr = $("#searchIdStr").val();// 数据库id串
		var dateTimeStart = $("#dateTimeStart").val();// 查询开始时间
		var dateTimeEnd = $("#dateTimeEnd").val();// 查询结束时间
		var queryTitle = $("#queryTitle").val();// 查询标题
		var queryContent = $("#queryContent").val();// 查询内容
		// var titleAndContent = queryTitle + "," + queryContent;
		page_as(summaryinti, pageNum, curPageNum, queryTitle, queryContent,
				dateTimeEnd, dateTimeStart, searchIdStr, queryDays, querySort,
				queryModel);
	}
}

// 改变排序方式
function sortSwicth() {
	$("#sortUl .sortUl_li").bind("click", function() {
		$(this).siblings().removeClass("visiting");
		$(this).addClass("visiting");
		change_sort();
	});
}

function change_sort() {
	// 设置查询模式
	queryModel = $("#queryModel").val();
	// 不显示摘要
	if (summaryinti == null) {
		summaryinti = 2;
	}
	// 每页显示20条数据
	if (pageNum == null || pageNum == 0) {
		pageNum = 20;
	}
	curPageNum = 1;// 当前第一页
	querySort = $("#sortUl .visiting").val();// 排序方式
	queryDays = $("#dateTimeUl .visiting").val();// 查询天数
	queryType = $("#queryType").val();// 查询范围
	if (queryModel == "ns") { // 普通查询
		pageId = $("#pageId").val();// 数据库或数据标签编号
		pageType = $("#pageType").val();// 当前网页类型:库页面或标签页或分类页
		queryStr = $("#queryStr").val();// 用户输入的查询串
		page(summaryinti, pageNum, curPageNum, queryStr, queryType, pageType,
				pageId, queryDays, querySort, queryModel);
	} else if (queryModel == "as") {// 高级查询
		var searchIdStr = $("#searchIdStr").val();// 数据库id串
		var dateTimeStart = $("#dateTimeStart").val();// 查询开始时间
		var dateTimeEnd = $("#dateTimeEnd").val();// 查询结束时间
		var queryTitle = $("#queryTitle").val();// 查询标题
		var queryContent = $("#queryContent").val();// 查询内容
		page_as(summaryinti, pageNum, curPageNum, queryTitle, queryContent,
				dateTimeEnd, dateTimeStart, searchIdStr, queryDays, querySort,
				queryModel);
	}
}

// 改变查询文档时间
function daySwicth() {
	$("#dateTimeUl li").bind("click", function() {
		$(this).siblings().removeClass("visiting");
		$(this).addClass("visiting");
		change_docTime();
	});
}

function change_docTime() {
	// 设置查询模式
	queryModel = $("#queryModel").val();
	// 不显示摘要
	if (summaryinti == null) {
		summaryinti = 2;
	}
	// 每页显示20条数据
	if (pageNum == null || pageNum == 0) {
		pageNum = 20;
	}
	curPageNum = 1;// 当前第一页
	querySort = $("#sortUl .visiting").val();// 排序方式
	queryDays = $("#dateTimeUl .visiting").val();// 查询天数
	queryType = $("#queryType").val();// 查询范围
	if (queryModel == "ns") { // 普通查询
		pageId = $("#pageId").val();// 数据库或数据标签编号
		pageType = $("#pageType").val();// 当前网页类型:库页面或标签页或分类页
		queryStr = $("#queryStr").val();// 用户输入的查询串
		page(summaryinti, pageNum, curPageNum, queryStr, queryType, pageType,
				pageId, queryDays, querySort, queryModel);
	} else if (queryModel == "as") {// 高级查询
		var searchIdStr = $("#searchIdStr").val();// 数据库id串
		var dateTimeStart = $("#dateTimeStart").val();// 查询开始时间
		var dateTimeEnd = $("#dateTimeEnd").val();// 查询结束时间
		var queryTitle = $("#queryTitle").val();// 查询标题
		var queryContent = $("#queryContent").val();// 查询内容
		page_as(summaryinti, pageNum, curPageNum, queryTitle, queryContent,
				dateTimeEnd, dateTimeStart, searchIdStr, queryDays, querySort,
				queryModel);
	}
}

// 是否显示摘要
function show_summary() {
	// 设置摘要
	$("#sumUI li")
			.bind(
					"click",
					function() {
						$(this).siblings().removeClass("visiting");
						$(this).addClass("visiting");
						$.cookie("peopleDataPageIsSummary", $(this).val());
						summaryinti = $(this).val();
						//alert(summaryinti);
						// 设置查询模式
						queryModel = $("#queryModel").val();
						// 页面大小
						// 每页显示20条数据
						if (pageNum == null) {
							pageNum = 20;
						}
						curPageNum = 1;// 当前第一页
						querySort = $("#sortUl .visiting").val();// 排序方式
						queryDays = $("#dateTimeUl .visiting").val();// 查询天数
						queryType = $("#queryType").val();// 查询范围
						if (queryModel == "ns") { // 普通查询
							pageId = $("#pageId").val();// 数据库或数据标签编号
							pageType = $("#pageType").val();// 当前网页类型:库页面或标签页或分类页
							queryStr = $("#queryStr").val();// 用户输入的查询串
							page(summaryinti, pageNum, curPageNum, queryStr,
									queryType, pageType, pageId, queryDays,
									querySort, queryModel);
						} else if (queryModel == "as") {// 高级查询
							var searchIdStr = $("#searchIdStr").val();// 数据库id串
							var dateTimeStart = $("#dateTimeStart").val();// 查询开始时间
							var dateTimeEnd = $("#dateTimeEnd").val();// 查询结束时间
							var queryTitle = $("#queryTitle").val();// 查询标题
							var queryContent = $("#queryContent").val();// 查询内容
							page_as(summaryinti, pageNum, curPageNum,
									queryTitle, queryContent, dateTimeEnd,
									dateTimeStart, searchIdStr, queryDays,
									querySort, queryModel);
						}
					});
}

// 调整每页显示文章数
function change_pageNum() {
	$("#OutNum")
			.change(
					function() {
						// 是否显示摘要
						if (summaryinti == null) {
							summaryinti = 2;
						}
						// 页面大小
						// 每页显示20条数据
						if (pageNum == $("#OutNum").val()) {
						} else {
							// 改变时重新写cookie
							$.cookie("peopleDataPageMaxSize", $("#OutNum")
									.val());
							pageNum = $("#OutNum").val();
						}
						curPageNum = 1;// 当前第一页
						querySort = $("#sortUl .visiting").val();// 排序方式
						queryDays = $("#dateTimeUl .visiting").val();// 查询天数
						queryType = $("#queryType").val();// 查询范围
						if (queryModel == "ns") { // 普通查询
							pageId = $("#pageId").val();// 数据库或数据标签编号
							pageType = $("#pageType").val();// 当前网页类型:库页面或标签页或分类页
							queryStr = $("#queryStr").val();// 用户输入的查询串
							page(summaryinti, pageNum, curPageNum, queryStr,
									queryType, pageType, pageId, queryDays,
									querySort, queryModel);
						} else if (queryModel == "as") {// 高级查询
							var searchIdStr = $("#searchIdStr").val();// 数据库id串
							var dateTimeStart = $("#dateTimeStart").val();// 查询开始时间
							var dateTimeEnd = $("#dateTimeEnd").val();// 查询结束时间
							var queryTitle = $("#queryTitle").val();// 查询标题
							var queryContent = $("#queryContent").val();// 查询内容
							page_as(summaryinti, pageNum, curPageNum,
									queryTitle, queryContent, dateTimeEnd,
									dateTimeStart, searchIdStr, queryDays,
									querySort, queryModel);
						}
					});
}

// 选择页数
function change_page(index) {
	// 是否显示摘要
	if (summaryinti == null) {
		summaryinti = 2;
	}
	// 页面大小
	// 每页显示20条数据
	if (pageNum == null) {
		pageNum = 20;
	}
	curPageNum = index;// 当前第一页
	querySort = $("#sortUl .visiting").val();// 排序方式
	queryDays = $("#dateTimeUl .visiting").val();// 查询天数
	queryType = $("#queryType").val();// 查询范围
	if (queryModel == "ns") { // 普通查询
		pageId = $("#pageId").val();// 数据库或数据标签编号
		pageType = $("#pageType").val();// 当前网页类型:库页面或标签页或分类页
		queryStr = $("#queryStr").val();// 用户输入的查询串
		page(summaryinti, pageNum, curPageNum, queryStr, queryType, pageType,
				pageId, queryDays, querySort, queryModel);
	} else if (queryModel == "as") {// 高级查询
		var searchIdStr = $("#searchIdStr").val();// 数据库id串
		var dateTimeStart = $("#dateTimeStart").val();// 查询开始时间
		var dateTimeEnd = $("#dateTimeEnd").val();// 查询结束时间
		var queryTitle = $("#queryTitle").val();// 查询标题
		var queryContent = $("#queryContent").val();// 查询内容
		page_as(summaryinti, pageNum, curPageNum, queryTitle, queryContent,
				dateTimeEnd, dateTimeStart, searchIdStr, queryDays, querySort,
				queryModel);
	}
}

// 对检索结果进行分页和页面展示处理
function page(isSum, pageNum, curPageNo, queryStr, queryType, pageType, pageId,
		queryDays, querySort, queryModel) {
	// 情况上次检索结果集
	$("#content").html('<h2 class="favo_loading"></h2>');
	// 数据库名
	var resultStarts = (curPageNo - 1) * pageNum;
	// 拼接json串
	var jsonParas = [ {
		"name" : "iDisplayStart",
		"value" : resultStarts
	}, {
		"name" : "mSearch",
		"value" : queryStr
	}, {
		"name" : "iType",
		"value" : pageType
	}, {
		"name" : "searchIdStr",
		"value" : pageId
	}, {
		"name" : "type",
		"value" : queryType
	}, {
		"name" : "para",
		"value" : pageNum
	}, {
		"name" : "timePeroid",
		"value" : queryDays
	}, {
		"name" : "sortField",
		"value" : querySort
	}, {
		"name" : "queryType",
		"value" : queryModel
	} ];
	// alert(JSON.stringify(jsonParas));
	$
			.ajax({
				type : "POST",
				url : "../query/s",
				data : JSON.stringify(jsonParas),
				dataType : 'json',
				contentType : 'application/json',
				success : function(data) {

					// 文章列表
					var acticleList = data.dataVoList;
					// 总页数
					var pageTotal;
					// 数据库名
					var dbNameStr = data.dataNameStr;
					// alert(dbNameStr);
					// 总记录数
					var acticleSize = data.size;
					// 最近一天
					var lastDays = data.lastDayCount;
					// 最近一周
					var lastWeeks = data.lastWeekCount;
					// 最近一月
					var lastMonths = data.lastMonthCount;
					// 最近一年
					var lastYears = data.lastYearCount;
					// 文章列表
					var docList = data.docList;
					// 文章内容
					var content = "";
					var appPaths = $("#appPaths").val();
					$("#lastDay").html(lastDays);
					$("#lastWeek").html(lastWeeks);
					$("#lastMonth").html(lastMonths);
					$("#lastYear").html(lastYears);
					$("#dbNameStr").html(dbNameStr);
					if (acticleList == null || acticleList == "") { // 未检索到数据
						content += "<div class='sreach_div'>"
								+ "<h2 class='table_info'>（本次检索出条记录 共有0页，当前是第0页）</h2>"
								+ "<div class='sreach_li'>"
								+ "<h3><a href='#'>未检索到相关数据或索引不存在!</a></h3>"
								+ "</div></div>";
						$("#content").html(content);
					} else {// 检索到有数据
						if (acticleSize <= pageNum) { // 查询的总记录数小于页面大小，不需要做分页
							content += "<div class='sreach_div'>"
									+ "<h2 class='table_info'>（本次检索出条记录 共有1页，当前是第1页）</h2>";
							for ( var i = 0; i < acticleSize; i++) {
								var dataId = acticleList[i].id;
								var tableId = acticleList[i].tableId;
								var pathCode = acticleList[i].pathCode;
								// 循环开始
								content += "<div class='sreach_li'>"
										+ "<h3 class='searchResultStyle'>	<a href='"
										+ appPaths + "/page" + pathCode + ""
										+ tableId + '_' + dataId + "?docList="
										+ docList + "' target='_blank'>"
										+ acticleList[i].title + "</a></h3>"
										+ "	<div class='listinfo'>"
										+ acticleList[i].docTime + "</div>";
								if (isSum == 1) {// 是否显示摘要
									content += "<div class='incon clearfix'>";
									if (acticleList[i].img == null
											|| acticleList[i].img == "") {
									} else {
										content += "<a href='"
												+ appPaths
												+ "/page"
												+ pathCode
												+ ""
												+ tableId
												+ '_'
												+ dataId
												+ "?docList="
												+ docList
												+ "' target='_blank'><img src='"
												+ acticleList[i].img
												+ "' /> </a>";
									}
									content += "<div class='incon_text'><p>"
											+ acticleList[i].summary
											+ "</p>	</div></div>";
								}
								content += "</div>";
								// 循环结束
							}
							content += "</div>";
							// 分页开始
							content += "<div class='page_nav'>"
									+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>"
									+ " <a href='javascript:change_page(1)' class='disabled'>上一页&nbsp;</a>"
									+ "<span>1</span>"
									+ "<a href='javascript:change_page(1)'>&nbsp;下一页</a> "
									+ "<a href='javascript:change_page(1)'>尾页</a>"
									+ "</div>";
							// 分页结束
							$("#content").html(content);
						} else {

							// 总页数
							var tempPageTotal = acticleSize % pageNum;
							if (tempPageTotal == 0) {
								pageTotal = parseInt(acticleSize / pageNum);
							} else {
								pageTotal = parseInt(acticleSize / pageNum) + 1;
							}
							// 页面显示文章
							content += "<div class='sreach_div'>"
									+ "<h2 class='table_info'> （本次检索出条记录 共有"
									+ pageTotal + "页，当前是第" + curPageNo
									+ "页）</h2>";
							for ( var i = 0; i < acticleList.length; i++) {
								var dataId = acticleList[i].id;
								var tableId = acticleList[i].tableId;
								var pathCode = acticleList[i].pathCode;
								// 循环开始
								content += "<div class='sreach_li'>"
										+ "<h3 class='searchResultStyle'>	<a href='"
										+ appPaths + "/page" + pathCode + ""
										+ tableId + '_' + dataId + "?docList="
										+ docList + "' target='_blank'>"
										+ acticleList[i].title + "</a></h3>"
										+ "	<div class='listinfo'>"
										+ acticleList[i].docTime + "</div>";
								if (isSum == 1) {// 是否显示摘要
									content += "<div class='incon clearfix'>";
									if (acticleList[i].img == null
											|| acticleList[i].img == "") {
									} else {
										content += "<a href='"
												+ appPaths
												+ "/page"
												+ pathCode
												+ ""
												+ tableId
												+ '_'
												+ dataId
												+ "?docList="
												+ docList
												+ "' target='_blank'><img src='"
												+ acticleList[i].img
												+ "' /> </a>";
									}
									content += "<div class='incon_text'><p>"
											+ acticleList[i].summary
											+ "</p></div></div>";
								}
								content += "</div>";
								// 循环结束
							}
							content += "</div>";
							// 分页处理
							if (pageTotal <= 10) {// 分页小于10的情况
								content += "<div class='page_nav'>"
										+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>";
								if (curPageNo == 1) {
									content += " <a href='javascript:change_page(1)' class='disabled'>上一页</a>";
								} else {
									var pre = curPageNo - 1;
									content += " <a href='javascript:change_page("
											+ pre
											+ ")' class='disabled'>上一页</a>";
								}
								for ( var i = 1; i <= pageTotal; i++) {
									if (curPageNo == i) {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;<span>"
												+ i + "</span>&nbsp;</a>";
									} else {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;"
												+ i + "&nbsp;</a>";
									}
								}
								if (curPageNo == pageTotal) {
									content += " <a href='javascript:change_page("
											+ pageTotal + ")'>&nbsp;下一页</a>";
								} else {
									var next = curPageNo + 1;
									content += "<a href='javascript:change_page("
											+ next + ")'>&nbsp;下一页</a>";
								}
								content += "<a href='javascript:change_page("
										+ pageTotal + ")'>&nbsp;尾页</a>"
										+ "</div>";
								// 写入页面
							} else {// 页面总数大于10页的情况下
								// 页数10的情况下
								// 指示器
								var tempNo = parseInt(curPageNo / 10);
								// 循环起点
								var loopStart;
								if (tempNo == 0) {
									loopStart = 1;
								} else {
									loopStart = tempNo * 10 - 1;
								}
								// 临时循环终点
								var tempLoopEnd;
								if (tempNo == 0) {
									tempLoopEnd = 10;
								} else {
									tempLoopEnd = loopStart + 11;
								}
								// 循环终点
								var loopEnd;
								if (tempLoopEnd > pageTotal) {
									loopEnd = pageTotal;
								} else {
									loopEnd = tempLoopEnd;
								}
								// 分页
								content += "<div class='page_nav'>"
										+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>";
								if (curPageNo == 1) {
									content += " <a href='javascript:change_page(1)' class='disabled'>上一页</a>";
								} else {
									var pre = curPageNo - 1;
									content += " <a href='javascript:change_page("
											+ pre
											+ ")' class='disabled'>上一页</a>";
								}
								for ( var i = loopStart; i <= loopEnd; i++) {
									if (curPageNo == i) {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;<span>"
												+ i + "</span>&nbsp;</a>";
									} else {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;"
												+ i + "&nbsp;</a>";
									}
								}
								if (curPageNo == pageTotal) {
									content += " <a href='javascript:change_page("
											+ pageTotal + ")'>&nbsp;下一页</a>";
								} else {
									var next = curPageNo + 1;
									content += "<a href='javascript:change_page("
											+ next + ")'>&nbsp;下一页</a>";
								}
								content += "<a href='javascript:change_page("
										+ pageTotal + ")'>&nbsp;尾页</a>"
										+ "</div>";
							}
							// 写入页面
							$("#content").html(content);
						}
					}
					saveArticleForList();
				},
				errors : function(data) {
				}
			});
}
// 对检索结果进行分页和页面展示处理(高级查询)
function page_as(isSum, pageNum, curPageNo, queryTitle, queryContent,
		dateTimeEnd, dateTimeStart, searchIdStr, queryDays, querySort,
		queryModel) {
	// 情况上次检索结果集
	$("#content").html('<h2 class="favo_loading"></h2>');
	// 数据库名
	var resultStarts = (curPageNo - 1) * pageNum;
	// 拼接json串
	var jsonParas = [ {
		"name" : "iDisplayStart",
		"value" : resultStarts
	}, {
		"name" : "title",
		"value" : queryTitle
	}, {
		"name" : "content",
		"value" : queryContent
	}, {
		"name" : "dateTimeFrom",
		"value" : dateTimeStart
	}, {
		"name" : "searchIdStr",
		"value" : searchIdStr
	}, {
		"name" : "dateTimeTo",
		"value" : dateTimeEnd
	}, {
		"name" : "pageSize",
		"value" : pageNum
	}, {
		"name" : "timePeroid",
		"value" : queryDays
	}, {
		"name" : "sortField",
		"value" : querySort
	}, {
		"name" : "queryType",
		"value" : queryModel
	} ];
	// alert(JSON.stringify(jsonParas));
	$
			.ajax({
				type : "POST",
				url : "../query/s",
				data : JSON.stringify(jsonParas),
				dataType : 'json',
				contentType : 'application/json',
				success : function(data) {

					// 文章列表
					var acticleList = data.dataVoList;
					// 总页数
					var pageTotal;
					// 数据库名
					var dbNameStr = data.dataNameStr;
					// alert(dbNameStr);
					// 总记录数
					var acticleSize = data.size;
					// 最近一天
					var lastDays = data.lastDayCount;
					// 最近一周
					var lastWeeks = data.lastWeekCount;
					// 最近一月
					var lastMonths = data.lastMonthCount;
					// 最近一年
					var lastYears = data.lastYearCount;
					// 文章列表
					var docList = data.docList;
					// 文章内容
					var content = "";
					var appPaths = $("#appPaths").val();
					$("#lastDay").html(lastDays);
					$("#lastWeek").html(lastWeeks);
					$("#lastMonth").html(lastMonths);
					$("#lastYear").html(lastYears);
					$("#dbNameStr").html(dbNameStr);
					if (acticleList == null || acticleList == "") { // 未检索到数据
						content += "<div class='sreach_div'>"
								+ "<h2 class='table_info'>（本次检索出条记录 共有0页，当前是第0页）</h2>"
								+ "<div class='sreach_li'>"
								+ "<h3><a href='#'>未检索到相关数据或索引不存在!</a></h3>"
								+ "</div></div>";
						$("#content").html(content);
					} else {// 检索到有数据
						if (acticleSize <= pageNum) { // 查询的总记录数小于页面大小，不需要做分页
							content += "<div class='sreach_div'>"
									+ "<h2 class='table_info'>（本次检索出条记录 共有1页，当前是第1页）</h2>";
							for ( var i = 0; i < acticleSize; i++) {
								var dataId = acticleList[i].id;
								var tableId = acticleList[i].tableId;
								var pathCode = acticleList[i].pathCode;
								// 循环开始
								content += "<div class='sreach_li'>"
										+ "<h3 class='searchResultStyle'>	<a href='"
										+ appPaths + "/page" + pathCode + ""
										+ tableId + '_' + dataId + "?docList="
										+ docList + "' target='_blank'>"
										+ acticleList[i].title + "</a></h3>"
										+ "	<div class='listinfo'>"
										+ acticleList[i].docTime + "</div>";
								if (isSum == 1) {// 是否显示摘要
									content += "<div class='incon clearfix'>";
									if (acticleList[i].img == null
											|| acticleList[i].img == "") {
									} else {
										content += "<a href='"
												+ appPaths
												+ "/page"
												+ pathCode
												+ ""
												+ tableId
												+ '_'
												+ dataId
												+ "?docList="
												+ docList
												+ "' target='_blank'><img src='"
												+ acticleList[i].img
												+ "' /> </a>";
									}
									content += "<div class='incon_text'><p>"
											+ acticleList[i].summary
											+ "</p>	</div></div>";
								}
								content += "</div>";
								// 循环结束
							}
							content += "</div>";
							// 分页开始
							content += "<div class='page_nav'>"
									+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>"
									+ " <a href='javascript:change_page(1)' class='disabled'>上一页&nbsp;</a>"
									+ "<span>1</span>"
									+ "<a href='javascript:change_page(1)'>&nbsp;下一页</a> "
									+ "<a href='javascript:change_page(1)'>尾页</a>"
									+ "</div>";
							// 分页结束
							$("#content").html(content);
						} else {

							// 总页数
							var tempPageTotal = acticleSize % pageNum;
							if (tempPageTotal == 0) {
								pageTotal = parseInt(acticleSize / pageNum);
							} else {
								pageTotal = parseInt(acticleSize / pageNum) + 1;
							}
							// 页面显示文章
							content += "<div class='sreach_div'>"
									+ "<h2 class='table_info'> （本次检索出条记录 共有"
									+ pageTotal + "页，当前是第" + curPageNo
									+ "页）</h2>";
							for ( var i = 0; i < acticleList.length; i++) {
								var dataId = acticleList[i].id;
								var tableId = acticleList[i].tableId;
								var pathCode = acticleList[i].pathCode;
								// 循环开始
								content += "<div class='sreach_li'>"
										+ "<h3 class='searchResultStyle'>	<a href='"
										+ appPaths + "/page" + pathCode + ""
										+ tableId + '_' + dataId + "?docList="
										+ docList + "' target='_blank'>"
										+ acticleList[i].title + "</a></h3>"
										+ "	<div class='listinfo'>"
										+ acticleList[i].docTime + "</div>";
								if (isSum == 1) {// 是否显示摘要
									content += "<div class='incon clearfix'>";
									if (acticleList[i].img == null
											|| acticleList[i].img == "") {
									} else {
										content += "<a href='"
												+ appPaths
												+ "/page"
												+ pathCode
												+ ""
												+ tableId
												+ '_'
												+ dataId
												+ "?docList="
												+ docList
												+ "' target='_blank'><img src='"
												+ acticleList[i].img
												+ "' /> </a>";
									}
									content += "<div class='incon_text'><p>"
											+ acticleList[i].summary
											+ "</p></div></div>";
								}
								content += "</div>";
								// 循环结束
							}
							content += "</div>";
							// 分页处理
							if (pageTotal <= 10) {// 分页小于10的情况
								content += "<div class='page_nav'>"
										+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>";
								if (curPageNo == 1) {
									content += " <a href='javascript:change_page(1)' class='disabled'>上一页</a>";
								} else {
									var pre = curPageNo - 1;
									content += " <a href='javascript:change_page("
											+ pre
											+ ")' class='disabled'>上一页</a>";
								}
								for ( var i = 1; i <= pageTotal; i++) {
									if (curPageNo == i) {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;<span>"
												+ i + "</span>&nbsp;</a>";
									} else {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;"
												+ i + "&nbsp;</a>";
									}
								}
								if (curPageNo == pageTotal) {
									content += " <a href='javascript:change_page("
											+ pageTotal + ")'>&nbsp;下一页</a>";
								} else {
									var next = curPageNo + 1;
									content += "<a href='javascript:change_page("
											+ next + ")'>&nbsp;下一页</a>";
								}
								content += "<a href='javascript:change_page("
										+ pageTotal + ")'>&nbsp;尾页</a>"
										+ "</div>";
								// 写入页面
							} else {// 页面总数大于10页的情况下
								// 页数10的情况下
								// 指示器
								var tempNo = parseInt(curPageNo / 10);
								// 循环起点
								var loopStart;
								if (tempNo == 0) {
									loopStart = 1;
								} else {
									loopStart = tempNo * 10 - 1;
								}
								// 临时循环终点
								var tempLoopEnd;
								if (tempNo == 0) {
									tempLoopEnd = 10;
								} else {
									tempLoopEnd = loopStart + 11;
								}
								// 循环终点
								var loopEnd;
								if (tempLoopEnd > pageTotal) {
									loopEnd = pageTotal;
								} else {
									loopEnd = tempLoopEnd;
								}
								// 分页
								content += "<div class='page_nav'>"
										+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>";
								if (curPageNo == 1) {
									content += " <a href='javascript:change_page(1)' class='disabled'>上一页</a>";
								} else {
									var pre = curPageNo - 1;
									content += " <a href='javascript:change_page("
											+ pre
											+ ")' class='disabled'>上一页</a>";
								}
								for ( var i = loopStart; i <= loopEnd; i++) {
									if (curPageNo == i) {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;<span>"
												+ i + "</span>&nbsp;</a>";
									} else {
										content += "<a href='javascript:change_page("
												+ i
												+ ")' class='disabled'>&nbsp;"
												+ i + "&nbsp;</a>";
									}
								}
								if (curPageNo == pageTotal) {
									content += " <a href='javascript:change_page("
											+ pageTotal + ")'>&nbsp;下一页</a>";
								} else {
									var next = curPageNo + 1;
									content += "<a href='javascript:change_page("
											+ next + ")'>&nbsp;下一页</a>";
								}
								content += "<a href='javascript:change_page("
										+ pageTotal + ")'>&nbsp;尾页</a>"
										+ "</div>";
							}
							// 写入页面
							$("#content").html(content);
						}
					}
					saveArticleForList();
				},
				errors : function(data) {
				}
			});
}

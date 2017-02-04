//前端文章列表js
$(document).ready(function() {
	inti_list();
});

var pageId;// 数据库或数据标签编号
var pageType;// 当前网页类型
var firstResult;// 分页查询文章开始标志

// 初始化查询页面
function inti_list() {
	pageId = $("#pageListId").val();
	pageType = $("#pageListType").val();
	// var pageNo = 1;
	page_list(1, pageId, pageType, 1);
}

// 分页选择页码
function change_page(index) {
	pageId = $("#pageListId").val();
	pageType = $("#pageListType").val();
	page_list(index, pageId, pageType, index);
}

// 加载文章
// index：当前页码
function page_list(index, pageId, pageType, curPageNo) {
	// alert(curPageNo);
	firstResult = (index - 1) * 20;// 分页开始编号
	var pageNum = 20;
	// 拼接json串
	var jsonParas = [ {
		"name" : "iDisplayStart",
		"value" : firstResult
	}, {
		"name" : "iType",
		"value" : pageType
	}, {
		"name" : "searchIdStr",
		"value" : pageId
	} ];
	// ajax调用
	$
			.ajax({
				type : "POST",
				url :  appPath + "page/list/s",
				data : JSON.stringify(jsonParas),
				dataType : 'json',
				contentType : 'application/json',
				success : function(data) {
					$("#content").empty();
					$("#dbNameStr").empty(dbNameStr);
					// alert(data);
					// 文章列表
					var acticleList = data.dataVoList;
					// 总页数
					var pageTotal;
					// 数据库名
					var dbNameStr = data.dataNameStr;
					// alert(dbNameStr);
					// 总记录数
					var acticleSize = data.size;
					// 文章列表
					var docList = data.docList;
					// 文章内容
					var content = "";
					var appPaths = $("#appPaths").val();
					$("#dbNameStr").append(dbNameStr);
					if (acticleList == null || acticleList == "") { // 未检索到数据
						content += "<div class='navText'>"
								+ "<a href='#'>首页</a> &gt; <a href='#'>"
								+ dbNameStr + "</a> " + "</div>";
						content += "<div class='leftList'>"
								+ "<ul class='clearfix'>"
								+ "<li>"
								+ "<h5><a href='#' target='_blank'>未检索到数据或索引为空</a></h5><h6></h6>"
								+ "</li></ul></div>";
						$("#content").append(content);
					} else {// 检索到有数据
						if (acticleSize <= pageNum) { // 查询的总记录数小于页面大小，不需要做分页
							content += "<div class='navText'>"
									+ "<a href='#'>首页</a> &gt; <a href='#'>"
									+ dbNameStr + "</a>" + "</div>";
							content += "<div class='leftList'>";
							for ( var i = 0; i < acticleSize; i++) {
								// 循环开始
								var dataId = acticleList[i].id;
								var tableId = acticleList[i].tableId;
								var pathCode = acticleList[i].pathCode;
								var attach = acticleList[i].attach;
								var attachStr;
								if (attach != null && attach != "") {
									attachStr = "<span class='icon icon-red icon-attachment'></span>";
								} else {
									attachStr = "";
								}
								if (i == 0 || i == 5 || i == 10 || i == 15) {
									content += "<ul class='clearfix'>";
									content += "<li>" + "<h5><a href='"
											+ appPaths + "/page" + pathCode
											+ "" + tableId + '_' + dataId
											+ "?docList=" + docList
											+ "' target='_blank'>"
											+ acticleList[i].title + "</a>"
											+ attachStr + "</h5><h6>"
											+ acticleList[i].docTime + "</h6>"
											+ "</li>";
								} else if (i == 4 || i == 9 || i == 14
										|| i == 19 || i == acticleSize - 1) {
									content += "<li>" + "<h5><a href='"
											+ appPaths + "/page" + pathCode
											+ "" + tableId + '_' + dataId
											+ "?docList=" + docList
											+ "' target='_blank'>"
											+ acticleList[i].title + "</a>"
											+ attachStr + "</h5><h6>"
											+ acticleList[i].docTime + "</h6>"
											+ "</li>";
									content += "</ul>";
								} else {
									content += "<li>" + "<h5><a href='"
											+ appPaths + "/page" + pathCode
											+ "" + tableId + '_' + dataId
											+ "?docList=" + docList
											+ "' target='_blank'>"
											+ acticleList[i].title + "</a>"
											+ attachStr + "</h5><h6>"
											+ acticleList[i].docTime + "</h6>"
											+ "</li>";
								}
								// 循环结束
							}
							content += "</div>";
							// 分页开始
							content += "<div class='page_nav'>"
									+ "<a href='javascript:change_page(1)' class='disabled'>首页</a>"
									+ " <a href='javascript:change_page(1)' class='disabled'>&nbsp;上一页</a>"
									+ "<span>&nbsp;1</span>"
									+ "<a href='javascript:change_page(1)'>&nbsp;下一页</a> "
									+ "<a href='javascript:change_page(1)'>&nbsp;尾页</a>"
									+ "</div>";
							// 分页结束
							$("#content").append(content);
						} else {
							// 总页数
							var tempPageTotal = acticleSize % pageNum;
							if (tempPageTotal == 0) {
								pageTotal = parseInt(acticleSize / pageNum);
							} else {
								pageTotal = parseInt(acticleSize / pageNum) + 1;
							}
							// 页面显示文章
							content += "<div class='navText'>"
									+ "<a href='#'>首页</a> &gt; <a href='#'>"
									+ dbNameStr + "</a>" + "</div>";
							content += "<div class='leftList'>";
							for ( var i = 0; i < acticleList.length; i++) {
								var dataId = acticleList[i].id;
								var tableId = acticleList[i].tableId;
								var pathCode = acticleList[i].pathCode;
								var attach = acticleList[i].attach;
								var attachStr;
								if (attach != null && attach != "") {
									attachStr = "<span class='icon icon-red icon-attachment'></span>";
								} else {
									attachStr = "";
								}
								// 循环开始
								if (i == 0 || i == 5 || i == 10 || i == 15) {
									content += "<ul class='clearfix'>";
									content += "<li>" + "<h5><a href='"
											+ appPaths + "/page" + pathCode
											+ "" + tableId + '_' + dataId
											+ "?docList=" + docList
											+ "' target='_blank'>"
											+ acticleList[i].title + "</a>"
											+ attachStr + "</h5><h6>"
											+ acticleList[i].docTime + "</h6>"
											+ "</li>";
								} else if (i == 4 || i == 9 || i == 14
										|| i == 19
										|| i == acticleList.length - 1) {
									content += "<li>" + "<h5><a href='"
											+ appPaths + "/page" + pathCode
											+ "" + tableId + '_' + dataId
											+ "?docList=" + docList
											+ "' target='_blank'>"
											+ acticleList[i].title + "</a>"
											+ attachStr + "</h5><h6>"
											+ acticleList[i].docTime + "</h6>"
											+ "</li>";
									content += "</ul>";
								} else {
									content += "<li>" + "<h5><a href='"
											+ appPaths + "/page" + pathCode
											+ "" + tableId + '_' + dataId
											+ "?docList=" + docList
											+ "' target='_blank'>"
											+ acticleList[i].title + "</a>"
											+ attachStr + "</h5><h6>"
											+ acticleList[i].docTime + "</h6>"
											+ "</li>";
								}
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
							$("#content").append(content);
						}
					}
				},
				errors : function(data) {
				}
			});
}
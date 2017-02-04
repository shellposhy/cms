$(document).ready(function(){
	gotoHome();//返回首页 用法:为链接标签添加class：goto_home
	goTop();// 返回页面顶部
	stopgoTop();//防止空链接跳到顶部
	addValiMethod();//增加表单验证规则
	loginDiv();// 登录框交互效果
	intUserloginBox();// 用户状态检查
	intSomeThing();//各种初始化
	slideSet();// 轮播图
	tabSwitch();// tab切换
	//simpleCalendar();// 日历
	pic_zoom_big();//图片放大
	channelNavOut();//二级频道菜单导航解析
	channelNav();//二级频道菜单导航样式交互
	fontZoomInt();//字号加减
	serchLiFix();// 搜索结果列表页样式处理
	detailPageFooterFix();//报刊库细览页暂时处理一下因数据问题引起的页脚错位。
});


/* 返回首页 */
function gotoHome(){
	$(".goto_home").click(function(){
		if(appPath!=null &&  $.cookie("user_home_page")!=null){
 		window.location.href = appPath + $.cookie("user_home_page");
 	}else{
 		return false;
	}
	})
}

/* 返回顶部 */
function goTop(){$("#back-to-top").hide();
$(function(){$(window).scroll(function(){if($(window).scrollTop() >400){$("#back-to-top").fadeIn(400);}else{$("#back-to-top").fadeOut(400);}});$("#back-to-top").click(function(){$('body,html').animate({scrollTop:0},400);return false;});});
}

// 阻止浏览器的默认行为
function stopDefault(e) {
	// 阻止默认浏览器动作(W3C)
	if (e && e.preventDefault)
		e.preventDefault();
	// IE中阻止函数器默认动作的方式
	else
		window.event.returnValue = false;
	return false;
}

//防止空连接页面跳到顶部
function stopgoTop() {
	$('a[href="#"][data-top!=true],a.disable').click(function(e){
		stopDefault(e);
	});
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

// 增加表单验证规则
function addValiMethod(){
	// 特殊字符验证
	jQuery.validator.addMethod("specialCharValidate", function(value, element) { 
	var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',　\\[\\]<>/? \\.；：%……+￥（）【】‘”“'。，、？]"); 
	return this.optional(element)||!pattern.test(value) ; 
	}, jQuery.format(jQuery.validator.messages["specialCharValidate"])); 
	
	// 字节长度验证
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return this.optional(element) || (length >= param[0] && length <= param[1]);
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));
}


/* 设置通用网站根目录 */
	if("undefined" == typeof appPath){
		 var appPath ="";
	};
	
// 初始化用户快捷登录
function intUserloginBox(){
	//userStatus();
	// 设置登录框ajax提交表单
	$(".userlogin_form_div form").submit(function(){
		$(this).ajaxSubmit({  
			url:appPath+"j_spring_security_check",
		    type : "POST",
		    success : function(msg){
		    	//userStatus();
		    }
			});
		return false;
	});
	$("#logout").live("click",function(){
		window.location.href = appPath+"j_spring_security_logout?app=epaper";
		return false;
	});
}

/* 登录框交互 */
function loginDiv(){
	$("#login_user").live("mouseenter",function(){
			$("#userlogin").stop(true,true);
			$("#userlogin").fadeIn(300);
	});
	$("#login_user").live("mouseleave",function(){
			$("#userlogin").stop(true,true);
			$("#userlogin").fadeOut(300);
	});
}

// 检查用户登录状态并显示
function userStatus(){
		$.ajax({
		type: "GET",
		url: appPath+"loginStatus",
		dataType:"json",
		success: function(data) {
			if(data.status==1){			
				$("#visitor").hide();
				$("#welcome").show();
				$("#UserName").html(data.username);
				$("#logout").show();	
				$("#login_user,.login_user").hide();
				if ($("#pageMark").val() == "login") {
					location.replace(appPath+"index.html");
				}
			}else{
				$("#welcome").hide();
				$("#logout").hide();
				$("#login_user").show();
				$("#visitor").show();
				$.ajax({
					type: "GET",
					url: appPath+"j_spring_security_check?type=IP&j_password=IPUSERPASSWORD",
					dataType:"json",
					contentType:"application/json",
					error:function(){},
					success: function(data2){
						if(data2.status==1){
							$("#login_user").hide();
							$("#visitor").hide();
							$("#welcome").show();
							$("#UserName").html(data2.username);
							$("#logout").show();	
							if ($("#pageMark").val() == "login") {
								location.replace(appPath+"index.html");
							}
						}
					}
				});
			}
		}
	});
}

// 判断用户登录状态
function testLogin() {
	var isLogin;
	$.ajax({
		url : appPath + "loginStatus",
		dataType : "json",
		async : false,
		success : function(data, textStatus) {
			if (data.status == 1) {
				isLogin = true;
			} else {
				isLogin = false;
			}
		}
	});
	return isLogin;
}

//设置排序号
function intSomeThing(){ 
	
//排序号插件初始化
$.spin.imageBasePath = appPath+'static/default/css/images/spin1/';
$('.spin').spin();

//排序号插件初始化
$("#close").click(function(){
	window.close();
});
$("#print").click(function(){
				$("#to_jqprint").jqprint();
			});
}



// 焦点图
function slideSet(){
	if($("#KinSlideshow").length >0){
		$("#KinSlideshow").KinSlideshow({
		moveStyle:"right",
		mouseEvent:"mouseover",
		isHasTitleBar:true,
		titleBar:{titleBar_height:30,titleBar_bgColor:"#333",titleBar_alpha:0.5},
		titleFont:{TitleFont_size:14,TitleFont_color:"#FFFFFF",TitleFont_weight:"normal",TitleFont_family:"Microsoft Yahei,SimHei"},
		btn:{btn_bgColor:"#FFFFFF",btn_bgHoverColor:"#CC0000",btn_fontColor:"#000000",btn_fontHoverColor:"#FFFFFF",btn_borderColor:"#cccccc",btn_borderHoverColor:"#FF0000",btn_borderWidth:1}
		});
	}	
}

// tab切换
function tabSwitch(){
	$(".tab_title").each(function(){
		var groups = $(this).parent().parent().find(".tab_con_div > ul,.tab_con > ul,.tab_con_item");
		$(this).find("a").each(function(i){
			$(this).mouseenter(function(){
				$(this).addClass("current").siblings().removeClass("current");
				if(groups.length>0){
				groups.eq(i).show().siblings().hide();
				}
			});
		});
		
	});
}

// 首页日历
function simpleCalendar(){
	if($(".calendar").length > 0){
	$.ajax({
  		url: "js/simpleCalendar.js",
 		dataType: "script",
  		type: "GET",
  		success: function(data){
  		$(".div_today-ym").html(pd_sdate_y+"年"+pd_sdate_m+"月");
		$(".div_today-d").html(pd_sdate_d+"<span>日</span>");
		$(".div_today-w").html(pd_sdate_w);
		$(".div_today-nl").html("农历"+chineseYear+"【"+birthpet+"】"+"年<br />"+chineseMonthAndDay);
		$(".div_today-after:last-child").html("距离"+event+"还有"+interTheDays+"天")
  }
});
}
}

// 首页数字报显示
function updateRmrbInfo(dateStr,pageNum,count,link){
		$("#rmrbInfoDate").html(dateStr);
		$("#rmrbInfoLink").attr("href",appPath+link);
		$("#rmrbInfoPageNum").html(pageNum);
		$("#rmrbInfoCount").html(count);
	}
// 获取星期几 通用方法
	function getWeek(dateStr){
		var newDt = new Date(dateStr.replace(/([\d]{4})([\d]{2})([\d]{2})/gi,"$1/$2/$3"));
		var newWeek=newDt.getDay();
		var newRmrbYear=newDt.getFullYear();
		var newRmrbM=newDt.getMonth()+1;
		var newRmrbDay=newDt.getDate();
		var newRmrbWeek="星期五";
		if(newWeek<=0){
			newRmrbWeek="星期日";
		}else if(newWeek==1){
			newRmrbWeek="星期一";
		}else if(newWeek==2){
			newRmrbWeek="星期二";
		}else if(newWeek==3){
			newRmrbWeek="星期三";
		}else  if(newWeek==4){
			newRmrbWeek="星期四";
		}else if(newWeek==5){
			newRmrbWeek="星期五";
		}else if(newWeek==6){
			newRmrbWeek="星期六";
		}
		return newRmrbWeek;
	}


/* 二级频道菜单导航解析 */
function channelNavOut(){
	if("undefined" != typeof(tree)){
		if($("#channel_menu").length>0){
			var leveMulti = 0;
			$("#channel_menu").html(objToDom(tree),leveMulti)
		}
	}
}

function objToDom(Obj,lm){
		var data = Obj.children;
		var rootName = Obj.name;
		var dataLen = data.length;
		if(lm){
		var lmT = lm;
		}else{
		var lmT = 0;
		}
		  if(data != null){
		  var leveMultiTmp = 0;
		  var leveMultiTmpMin = 0;
		   for(var i = 0; i < dataLen; i++) { // 检查子节点是否有一组超过5
						if(data[i].children != null && data[i].children.length > 5){
							leveMultiTmp++;
						}
						if(data[i].children != null && data[i].children.length >= 4){
							leveMultiTmpMin++;
						}
					}
			var tmp = "";
			for (var i = 0; i < dataLen; i++) {
				var name = data[i].name;
				var length = data[i].length;
				var href = data[i].href;
				var objToDomTmp = "";
					if(data[i].children != null && data[i].children.length > 0){
						var theClass = "";
						// if(((dataLen <= 5) && (leveMultiTmp > 0))||((dataLen
						// <= 5) && (leveMultiTmpMin < 1)&& (rootName
						// !="root"))){
						if((dataLen <= 5) && (leveMultiTmp > 0)&& (rootName !="root")){
							theClass = 'class="narrow def_show"';
							}else{
								if (data[i].children.length > 5){
									theClass = 'class="narrow def_hide"';
								}else{
									theClass = 'class="def_hide"';
								}
							}
						objToDomTmp = '<ul '+ theClass +'>'+ objToDom(data[i],leveMultiTmp) +'</ul>'
					}
					if(rootName=="root"){ // or 二级平铺模式下
						tmp += '<li class="wide"><label class="leve_1 clearfix"><a href="'+href+'">'+name+'</a></label>'+ objToDomTmp +'</li>'// 第一级情况
					}else if((dataLen <= 5) && (leveMultiTmp > 0)&& (rootName !="root")){
						tmp += '<li class="wide"><label class="leve_2 clearfix"><a href="'+href+'">'+name+'</a></label>'+ objToDomTmp +'</li>'
					}else{
						if(objToDomTmp == ""){
							tmp += '<li><a class="mini" href="'+href+'">'+name+'</a></li>'
						}else{
							tmp += '<li><a class="mini" href="'+href+'">'+name+'<i class="hideIcon"></i></a>'+ objToDomTmp +'</li>'
						}
					}
			}
		}
		if(rootName=="root"){
			return '<ul class="leve_1_ul">'+tmp+'</ul>';
		}else{
			return tmp;
		}
}


// 样式变化以hover导航菜单
function channelNav(){
	$("#channel_menu ul li").live("mouseenter",function(){
		var isChildHide = $(this).children(".def_hide").length;
		var isParentShow = $(this).parent().hasClass("def_show");
		var width_li = $(this).width();
		if(isParentShow){
			$(this).parentsUntil("li").parent().siblings().css("z-index",0);
			$(this).parentsUntil("li").parent().css("z-index",1);
		}
		
		if(isChildHide > 0){
			$(this).siblings().removeClass("active");
			if($(this).children("ul").children("li").children(".def_show").length > 0){
				$(this).children("ul").addClass("narrow")
			}
				$(this).addClass("active").children().addClass("curt").children("i").addClass("icurt");
				if($(this).parent().hasClass("leve_1_ul")==true){
					$(this).children(".def_hide").show();
					// 避免平级样式
					var leve_2_2 = $(this).find(".leve_2").next(".def_show").children(".wide").children(".leve_2");
					leve_2_2.css("margin-left",5)
				}else{
					$(this).children(".def_hide").show().css("left",width_li-1);
				}
		}
	});
	$("#channel_menu ul li").live("mouseleave",function(){
				$(this).removeClass("active").children().removeClass("curt").children("i").removeClass("icurt");
				$(this).children(".def_hide").hide();
	});
	
	// 钉住元素
	$.fn.smartFloat = function() {
		var position = function(element) { 
		var top = element.position().top, pos = element.css("position"); 
		$(window).scroll(function() { 
		var scrolls = $(this).scrollTop(); 
		if (scrolls > top+120) { 
		if (window.XMLHttpRequest) { 
		element.css({ 
		position: "fixed", 
		top: 0
		}); 
		} else { 
		element.css({ 
		top: scrolls 
		}); 
		} 
		}else { 
		element.css({ 
		position: "absolute", 
		top: top 
		}); 
		if($("#channel_nav_fix_right").length >0){
			if(element.height() > $("#channel_nav_fix_right").height()){
			$("#channel_nav_fix_right").height(element.height())
			}
		}
		}
		}); 
		}; 
		return $(this).each(function() { 
		position($(this)); 
		}); 
		}; 
		
	$(".left_nav").parent().smartFloat(); 
}

// 搜索结果列表页图片放大效果
function pic_zoom_big() {
		$('.list_pic img')
				.live(
						"mouseover",
						function() {
							var src = $(this).attr("src");
							var $tip = "";
							if ($(this).height() > 400) {
								$tip = $('<div id="img_tip"><div class="t_box"><div><img height="400px" src="'
										+ src + '" /></div></div></div>');
							} else {
								$tip = $('<div id="img_tip"><div class="t_box"><div><img src="'
										+ src + '" /></div></div></div>');

							}
							$('body').append($tip);
							$('#img_tip').show('fast');
						});
		$('.list_pic img').live("mouseout", function() {
			$('#img_tip').remove();
		})
		$('.list_pic img').live("mousemove", function(e) {
			var Y = $(this).offset().top;
			var X = $(this).offset().left;
			var w = parseInt($(this).width()) + X;
			var h = Y - 5;
			$('#img_tip').css({
				"top" : h + "px",
				"left" : w + "px"
			});
		});
}

//前端查询查询检查
$(document).ready(function() {
	pre_top_search();
	pre_left_search();
	pre_footer_search();
	pre_rmrb_search();
	pre_center_search();
});

// 查询预处理
function pre_top_search() {
	$("#pageQueryBtnTop").click(function() {
		var queryStr = $("#page_query_top #queryStr").val();
		queryStr=queryStr.trim();
		if (queryStr == null || queryStr == "" || queryStr.length == 0) {
			alert("请输入检索词!");
			$("#page_query_top #queryStr").focus();
			return false;
		} else {
			if (queryStr.length < 2) {
				alert("输入的检索词过短!");
				$("#page_query_top #queryStr").focus();
				return false;
			}
		}
		$("#page_query_top").submit();
	});
}

//查询预处理
function pre_left_search() {
	$("#pageQueryBtn").click(function() {
		var queryStr = $("#page_query #queryStr").val();
		queryStr=queryStr.trim();
		if (queryStr == null || queryStr == "" || queryStr.length == 0) {
			alert("请输入检索词!");
			$("#page_query #queryStr").focus();
			return false;
		} else {
			if (queryStr.length < 2) {
				alert("输入的检索词过短!");
				$("#page_query #queryStr").focus();
				return false;
			}
		}
		$("#page_query").submit();
	});
}

// 查询预处理
function pre_footer_search() {
	$("#pageQueryBtnFooter").click(function() {
		var queryStr = $("#page_query_footer #queryStr").val();
		queryStr=queryStr.trim();
		if (queryStr == null || queryStr == "" || queryStr.length == 0) {
			alert("请输入检索词!");
			$("#page_query_footer #queryStr").focus();
			return false;
		} else {
			if (queryStr.length < 2) {
				alert("输入的检索词过短!");
				$("#page_query_footer #queryStr").focus();
				return false;
			}
		}
		$("#page_query_footer").submit();
	});
}

// 查询预处理
function pre_rmrb_search() {
	$("#pageQueryBtnRmrb").click(function() {
		var queryStr = $("#page_query_rmrb #queryStr").val();
		queryStr=queryStr.trim();
		if (queryStr == null || queryStr == "" || queryStr.length == 0) {
			alert("请输入检索词!");
			$("#page_query_rmrb #queryStr").focus();
			return false;
		} else {
			if (queryStr.length < 2) {
				alert("输入的检索词过短!");
				$("#page_query_rmrb #queryStr").focus();
				return false;
			}
		}
		$("#page_query_rmrb").submit();
	});
}

//查询预处理
function pre_center_search() {
	$("#pageQueryBtnCenter").click(function() {
		var queryStr = $("#page_query_center #queryStr").val();
		queryStr=queryStr.trim();
		if (queryStr == null || queryStr == "" || queryStr.length == 0) {
			alert("请输入检索词!");
			$("#page_query_center #queryStr").focus();
			return false;
		} else {
			if (queryStr.length < 2) {
				alert("输入的检索词过短!");
				$("#page_query_center #queryStr").focus();
				return false;
			}
		}
		$("#page_query_center").submit();
	});
}


/* 字号调整 */

// 初始化字号
var initPagefontsize = 14;

function fontZoomInt(){
	$("#size_add").click(function(){
			FontZoomMax();
		});
	$("#size_reset").click(function(){
			FontZoomRe();
		});
	$("#size_reduce").click(function(){
			FontZoomMin();
		});
	}

// 缩小字号
function FontZoomMin() {
	if (initPagefontsize > 10) {
		$("#FontZoom, #FontZoom p, #FontZoom td").css({
			"font-size" : --initPagefontsize
		});
	}
}

// 增大字号
function FontZoomMax() {	
		if (initPagefontsize < 20) {
			$("#FontZoom, #FontZoom p, #FontZoom td").css({
				"font-size" : ++initPagefontsize
			});
		}
}

// 还原字号
function FontZoomRe() {
	if (initPagefontsize != 14) {
		initPagefontsize = 14;
		$("#FontZoom, #FontZoom p, #FontZoom td").css({
			"font-size" : initPagefontsize
		});
	}
}

// 搜索结果列表页样式处理
function serchLiFix() {
	$(".sreach_li").each(function() {
		if ($(this).find("img").length > 0) {
			$(this).find(".incon_text").height("60px");
		}
	})
}

//报刊库细览页暂时处理一下因数据问题引起的页脚错位。
function detailPageFooterFix(){
	if($("#con").next("#footer").length <1){
		var footer = $("#footer").clone();
		$("#footer").remove();
		$("#container").append(footer);
	}
}
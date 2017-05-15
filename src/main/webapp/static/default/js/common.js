$(document).ready(function(){
	init_city_weather();
	general_go_top();
	stop_direct_no_link();
	general_validate();
	init_basic();
	slide_home();
	tab_home_switch();
	pic_zoom_big();
	font_zoom();
});

function init_city_weather(){
	if($("#weatherInfo").length>0){
		 var cityUrl = 'http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js';
	     $.getScript(cityUrl, function (script, textStatus, jqXHR) {
	       var citytq = remote_ip_info.city;
	       var url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&city=" + citytq + "&day=0&dfc=3";
	       $.ajax({
	         url: url,
	         dataType: "script",
	         scriptCharset: "gbk",
	         success: function (data) {
	           var _w = window.SWther.w[citytq][0];
	           var _f = _w.f1 + "_0.png";
	           if (new Date().getHours() > 17) {
	             _f = _w.f2 + "_1.png";
	           }
	           var tq = citytq +_w.s1+ _w.t1 + "℃～" + _w.t2 + "℃ " + _w.d1 + _w.p1 + "级";
	           $("#weatherInfo").html(tq);
	         }
	       });
	     });
     }
}

function general_go_top(){
	$("#back-to-top").hide();
	$(function(){
		$(window).scroll(function(){
			if($(window).scrollTop() >400){
				$("#back-to-top").fadeIn(400);
			}else{
				$("#back-to-top").fadeOut(400);
			}
		});
		$("#back-to-top").click(function(){
			$('body,html').animate({scrollTop:0},400);return false;}
		);
	});
}

function stop_direct_no_link() {
	$('a[href="#"][data-top!=true],a.disable').click(function(e){
		if (e && e.preventDefault){
			e.preventDefault();
		}else{
			window.event.returnValue = false;
		}
		return false;
	});
}

Date.prototype.Format = function (fmt) {
    var date_object = {"M+": this.getMonth() + 1,"d+": this.getDate(), "h+": this.getHours(), "m+": this.getMinutes(),"s+": this.getSeconds(), "q+": Math.floor((this.getMonth() + 3) / 3),"S": this.getMilliseconds()};
    if(/(y+)/.test(fmt)){
    	fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    } 
    for (var k in date_object)
    	if (new RegExp("(" + k + ")").test(fmt)) 
    		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (date_object[k]) : (("00" + date_object[k]).substr(("" + date_object[k]).length)));
    	return fmt;
}

function general_validate(){
	jQuery.validator.addMethod("specialCharValidate", function(value, element) {
		var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',　\\[\\]<>/? \\.；：%……+￥（）【】‘”“'。，、？]"); 
		return this.optional(element)||!pattern.test(value) ; 
	},jQuery.format(jQuery.validator.messages["非法字符"])); 
	jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
	    var length = value.length;
	    for (var i = 0; i < value.length; i++) {
	        if (value.charCodeAt(i) > 127) {
	            length++;
	        }
	    }
	    return this.optional(element) || (length >= param[0] && length <= param[1]);
	},$.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));
}

function init_basic(){
	if("undefined" == typeof appPath){var appPath ="";};
	$.spin.imageBasePath = appPath+'static/default/css/images/spin1/';
	$('.spin').spin();
	$("#close").click(function(){window.close();});
	$("#print").click(function(){$("#to_jqprint").jqprint();});
}

function slide_home(){
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

function tab_home_switch(){
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

function get_week(dateStr){
	var newDt = new Date(dateStr.replace(/([\d]{4})([\d]{2})([\d]{2})/gi,"$1/$2/$3"));
	var newWeek=newDt.getDay();var newRmrbYear=newDt.getFullYear();var newRmrbM=newDt.getMonth()+1;var newRmrbDay=newDt.getDate();var newRmrbWeek="星期五";
	if(newWeek<=0){newRmrbWeek="星期日";}else if(newWeek==1){newRmrbWeek="星期一";}else if(newWeek==2){newRmrbWeek="星期二";}else if(newWeek==3){newRmrbWeek="星期三";}else  if(newWeek==4){newRmrbWeek="星期四";}else if(newWeek==5){newRmrbWeek="星期五";}else if(newWeek==6){newRmrbWeek="星期六";}
	return newRmrbWeek;
}

function pic_zoom_big() {
	$('.list_pic img').live("mouseover",function() {
			var src = $(this).attr("src");
			var $tip = "";
			if ($(this).height() > 400) {
				$tip = $('<div id="img_tip"><div class="t_box"><div><img height="400px" src="'+ src + '" /></div></div></div>');
			} else {
				$tip = $('<div id="img_tip"><div class="t_box"><div><img src="'+ src + '" /></div></div></div>');
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

function font_zoom(){
	var initPagefontsize = 14;
	$("#size_add").click(function(){
		if (initPagefontsize < 20) {
			$("#FontZoom, #FontZoom p, #FontZoom td").css({
				"font-size" : ++initPagefontsize
			});
		}
	});
	$("#size_reset").click(function(){
		if (initPagefontsize != 14) {
			initPagefontsize = 14;
			$("#FontZoom, #FontZoom p, #FontZoom td").css({
				"font-size" : initPagefontsize
			});
		}
	});
	$("#size_reduce").click(function(){
		if (initPagefontsize > 10) {
			$("#FontZoom, #FontZoom p, #FontZoom td").css({
				"font-size" : --initPagefontsize
			});
		}
	});
}
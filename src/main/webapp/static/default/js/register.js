$(function(){
	$('#reg_select').iSelect(); 
})

var parm = {
	danweiS : false,
	bumenzhiwuS : false,
	emailS : false,
	addressS : false,
	nameS : false,
	conttelS : false,
	contmobS : false,
	indusS : false,
	postcodeS : false,
	contfoxS : false,
	captchaS : false
}


//单位
function checkDanwei(){
	var danwei = jQuery.trim(jQuery("#danwei").val());
	if(danwei == ''){
		parm.danweiS = false;
		jQuery("#danwei").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写单位名称</span>");
		return false;
	}
	parm.danweiS = true;
	return true;
};

//部门职务
function checkZhiwu(){
	var zhiwu = jQuery.trim(jQuery("#bumenzhiwu").val());
	if(zhiwu == ''){
		parm.bumenzhiwuS = false;
		jQuery("#bumenzhiwu").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写您的部门和职务</span>");
		return false;
	}
	parm.bumenzhiwuS = true;
};

//行业
function checkIndus(){
	var indus = jQuery.trim(jQuery("#indus").text());
	if(indus == '请选择'){
		parm.indusS = false;
		jQuery("#indus").removeClass("input_focus").addClass('form-error').parent().parent().parent().parent().append("<span class='errormsg' style='position:absolute; left:460px; width:80px;'>请选择行业</span>");
		return false;
	}
	parm.indusS = true;
};

//邮箱
function checkEmail(){
	var email = jQuery.trim(jQuery("#email").val());
	if(email == ''){
		parm.emailS = false;
		jQuery("#email").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写邮箱</span>");
		return false;
	}
	if(!/^[\w\.\-]+@([\w\-]+\.)+[a-z]{2,4}$/ig.test(email)){
		parm.emailS = false;
		jQuery("#email").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>邮箱格式不正确</span>");
		return false;
	};	
	parm.emailS = true;
};


//地址
function checkAddress(){
	var address = jQuery.trim(jQuery("#address").val());
	if(address == ''){
		parm.addressS = false;
		jQuery("#address").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写单位地址</span>");
		return false;
	}
	parm.addressS = true;
	return true;
};

//姓名
function checkName(){
	var name = jQuery.trim(jQuery("#name").val());
	if(name == ''){
		parm.nameS = false;
		jQuery("#name").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写您的姓名</span>");
		return false;
	}
	if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(name)){
		jQuery("#name").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请正确填写您的姓名</span>");
		parm.nameS = false;
		return false;
	}
	parm.nameS = true;
	return true;
};
//联系电话
function checkConttel(){
	var conttel = jQuery.trim(jQuery("#conttel").val());
	if(conttel == ''){
		parm.conttelS = false;
		jQuery("#conttel").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写联系电话</span>");
		return false;
	}
	if(!/^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/.test(conttel)){
		jQuery("#conttel").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请正确填写联系电话</span>");
		parm.conttelS = false;
		return false;
	}
	parm.conttelS = true;
	return true;
};
//联系手机
function checkContmob(){
	var contmob = jQuery.trim(jQuery("#contmob").val());
	if(contmob == ''){
		parm.contmobS = false;
		jQuery("#contmob").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请填写您的手机号</span>");
		return false;
	}
	if(!/^[0-9]{11}$/.test(contmob)){
		jQuery("#contmob").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请正确填写手机号码</span>");
		parm.contmobS = false;
		return false;
	}
	parm.contmobS = true;
	return true;
};
//邮编
function checkPostcode(){
	var postcode = jQuery.trim(jQuery("#postcode").val());
	if(postcode == ''){
		parm.postcodeS = false;
		jQuery("#postcode").parent().append("");
		return false;
	}
	if(!/^[1-9][0-9]{5}$/.test(postcode)){
		jQuery("#postcode").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请正确填写邮编</span>");
		parm.postcodeS = false;
		return false;
	}
	parm.contmobS = true;
	return true;
};
//传真
function checkContfox(){
	var contfox = jQuery.trim(jQuery("#contfox").val());
	if(contfox == ''){
		parm.contfoxS = false;
		jQuery("#contfox").parent().append("");
		return;
	}
	if(!/^[+]{0,1}(\d){1,3}[ ]?([-]?(\d){1,12})+$/.test(contfox)){
		jQuery("#contfox").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请正确填写传真号码</span>");
		parm.contfoxS = false;
		return;
	}
};
function checkCode(){
	var captcha = jQuery.trim(jQuery("#captcha2").val());
	if(captcha.length != 4){
		parm.captchaS = false;
		jQuery("#captcha2").removeClass("input_focus").addClass('form-error').parent().append("<span class='errormsg'>请正确填写注册码</span>");
		return false;
	}
	parm.captchaS = true;
	return true;
}


function onRegSubmit(){	
	if(checkDanwei()==false){
		jQuery("#danwei").focus();
		return false;
	}else if(checkName()==false){
		jQuery("#name").focus();
		return false;
	}else if(checkIndus()==false){
		jQuery("#indus").focus();
		return false;
	}else if(checkZhiwu()==false){
		jQuery("#bumenzhiwu").focus();
		return false;
	}else if(checkConttel()==false){
		jQuery("#conttel").focus();
		return false;
	}else if(checkContmob()==false){
		jQuery("#contmob").focus();
		return false;
	}else if(checkEmail()==false){
		jQuery("#email").focus();
		return false;
	}else if(checkAddress()==false){
		jQuery("#address").focus();
		return false;
	}	else if(checkCode()==false){
		jQuery("#captcha2").focus();
		return false;
	}
return true;

}
function reloadImg(id){
	//
	var path="/checkCode.jsp?rn="+Math.random();
	jQuery("#captchaImg").attr("src",path);
	jQuery("#captchaImg2").attr("src",path);
}










jQuery(document).ready(function(){
	
	jQuery("#danwei").blur(function(){
		checkDanwei();
	}).focus(function(){
		jQuery("#danwei").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#bumenzhiwu").blur(function(){
		checkZhiwu();
	}).focus(function(){
		jQuery("#bumenzhiwu").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#email").blur(function(){
		checkEmail();
	}).focus(function(){
		jQuery("#email").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#address").blur(function(){
		checkAddress();
	}).focus(function(){
		jQuery("#address").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#name").blur(function(){
		checkName();
	}).focus(function(){
		jQuery("#name").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#conttel").blur(function(){
		checkConttel();
	}).focus(function(){
		jQuery("#conttel").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#contmob").blur(function(){
		checkContmob();
	}).focus(function(){
		jQuery("#contmob").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
	jQuery("#indus").parent().find('.tags').mouseleave(function(){
		checkIndus();
	}).mouseenter(function(){
		jQuery("#indus").parent().parent().parent().parent().find('span.errormsg').remove();    
	});
	jQuery("#postcode").blur(function(){
		checkPostcode();
	}).focus(function(){
		jQuery("#postcode").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});
    jQuery("#contfox").blur(function(){
		checkContfox();
	}).focus(function(){
		jQuery("#contfox").removeClass('form-error').addClass('input_focus').parent().find('span.errormsg').remove();    
	});

});

	
// --列头全选框被单击---
function ChkAllClick(sonName, cbAllId){
    var arrSon = document.getElementsByName(sonName);
 var cbAll = document.getElementById(cbAllId);
 var tempState=cbAll.checked;
 for(i=0;i<arrSon.length;i++) {
  if(arrSon[i].checked!=tempState)
           arrSon[i].click();
 }
}
// --子项复选框被单击---
function ChkSonClick(sonName, cbAllId) {
 var arrSon = document.getElementsByName(sonName);
 var cbAll = document.getElementById(cbAllId);
 for(var i=0; i<arrSon.length; i++) {
     if(!arrSon[i].checked) {
     cbAll.checked = false;
     return;
     }
 }
 cbAll.checked = true;
}
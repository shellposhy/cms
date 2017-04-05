<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link href="${appPath}/admin/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${appPath}/admin/css/charisma-app.css" rel="stylesheet" />
<link href="${appPath}/admin/css/bootstrap-cerulean.css" rel="stylesheet" />
<link rel="stylesheet" href="${appPath}/admin/css/base.css" type="text/css" />
<script type="text/javascript" src="${appPath}/admin/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${appPath}/admin/js/jquery.validate.min.js"></script>
<script type="text/javascript">
	var appPath = "${appPath}"
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="row-fluid">
				<div class="span12 center top-block login-header" style="background: #fff">
					<h1 style="color: #c00" class="mt30">${appName}</h1>
				</div>
			</div>
			<div class="row-fluid">
				<div class="well span5 center login-box mt60">
					<div class="mt30">
						<form id="login_form" action="${appPath}/admin/security/check" method="post" class="mt10">
							<input type="hidden" id="from" name="from" value="${from }" />
							<fieldset>
								<div class="input-prepend" title="username" style="margin-bottom: 20px;">
									<span class="add-on floatl"><i class="icon-user"></i></span>
									<input autofocus class="input-large floatl" name="username" id="username" type="text" placeholder="用户名" />
								</div>
								<div class="clearfix"></div>
								<div class="input-prepend" title="password" style="margin-bottom: 20px;">
									<span class="add-on floatl"><i class="icon-lock"></i></span>
									<input class="input-large floatl" name="password" id="password" type="password" placeholder="密码" />
								</div>
								<div class="clearfix"></div>
								<p class="center span3">
									<button type="submit" class="btn btn-success">登录</button>
								</p>
							</fieldset>
						</form>
					</div>
				</div>
				<div class="row-fluid" style="position: fixed; bottom: 0px;">
					<div class="span12 center">
						<p>Copyright © 2017, 南山智慧谷, All Rights Reserved</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#login_form").validate({
				rules : {
					username : {required : true},
					password : {required : true}
				},
				messages : {
					username : {required : "账户名不能为空"},
					password : {required : "密码不能为空"}
				}
			});
		});
	</script>
</body>
</html>
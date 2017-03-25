<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link href="${appPath}/admin/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${appPath}/admin/css/charisma-app.css" rel="stylesheet" />
<link href="${appPath}/admin/css/bootstrap-cerulean.css" rel="stylesheet"  />
<link rel="stylesheet" href="${appPath}/admin/css/base.css" type="text/css" />
<script type="text/javascript" src="${appPath}/admin/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	var appPath = "${appPath}"
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="row-fluid">
				<div class="span12 center top-block login-header">
					<h2 style="color: #c00">${appName}</h2>
				</div>
			</div>
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-success ${param.error == true ? '' : ''}">
						请输入用户名和密码 ${requestScope.errMsg} 
					</div>
					<form id="login_form" action="${appPath}/admin/security/check" method="post" >
						<input type="hidden" id="from" name="from" value="${from }"/>
						<fieldset>
							<div class="input-prepend" title="Username" >
								<span class="add-on floatl"><i class="icon-user"></i></span>								
								<input autofocus class="input-large floatl"  name="username" id="username" type="text" placeholder="用户名" />
							</div>
							<div class="clearfix"></div>
							<div class="input-prepend" title="Password">
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
		</div>
	</div>
</body>
</html>
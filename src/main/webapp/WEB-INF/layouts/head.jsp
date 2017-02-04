<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="navbar">
  <div class="navbar-inner " >
    <a class="brand" href="#">数据信息管理系统</a> 
    <ul class="nav" id="topnav" >
    	<li class="dropdown">
         <a class="dropdown-toggle" id="current" role="button" data-toggle="dropdown" data-target="#" href="/${appPath}/admin/user">
		   您好，${currentUser.name}
		    <b class="caret"></b>
		  </a>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="current">
		  		<li><a href="#" target="_self" id = 'logout_sys'><i class="icon icon-blue icon-locked"></i>	退出管理系统</a>	</li>
				<li><a href="${appPath}/index.html"	target="_blank"><i class="icon icon-blue icon-home"></i> 查看前台页面</a></li>
		  </ul>
	   </li>
    
      <li><a href="${appPath}/admin/index">首页</a></li>
      <li class="dropdown"><a  class="dropdown-toggle" id='myZoneLable'  href="#" role="button" data-toggle="dropdown" data-target="#">我的地盘
       <b class="caret"></b>
      </a>
      	<ul class="dropdown-menu" role="menu" aria-labelledby="myZoneLable">
      	<li><a href="${appPath}/admin/utask">我的任务</a></li>
		<li><a href="${appPath}/admin/uinfo">我的信息</a></li></ul>
      </li>
      <li><a href="${appPath}/admin/column">内容</a></li>
      <li><a href="${appPath}/admin/subject">专题</a></li>
       <li class="dropdown">
         <a class="dropdown-toggle" id="viewLabel" role="button" data-toggle="dropdown" data-target="#" href="${appPath}/admin/view">
		  页面/模板
		    <b class="caret"></b>
		  </a>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="viewLabel">
		  		<li><a href="${appPath}/admin/viewPage">页面</a></li>
		  		<li><a href="${appPath}/admin/viewModel">模板</a></li>
		  </ul>
	   </li>
      <li class="dropdown">
      	<a class="dropdown-toggle" id="peopledbLabel" role="button" data-toggle="dropdown" data-target="#"  href="#">
      		人民数据 
      			<b class="caret"></b>
      		</a>
      	 <ul class="dropdown-menu" role="menu" aria-labelledby="peopledbLabel">
		  		<li><a href="${appPath}/admin/peopledb">数据库</a></li>
		  		<li><a href="${appPath}/admin/peopledb/data/update">数据更新</a></li>
		  </ul>
      	</li>
      <li><a href="${appPath}/admin/userdb">自建库</a></li>
      <li><a href="#">整合库</a></li>
      <li class="dropdown">
         <a class="dropdown-toggle" id="mediaLabel" role="button" data-toggle="dropdown" data-target="#" href="/${appPath}/admin/media">
		   媒资库
		    <b class="caret"></b>
		  </a>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="mediaLabel">
		  		<li><a href="${appPath}/admin/imglib">图片库</a></li>
		  		<li><a href="${appPath}/admin/videolib">视频库</a></li>
		  		<li><a href="${appPath}/admin/file">附件库</a></li>
		  </ul>
	   </li>
	   <li class="dropdown">
         <a class="dropdown-toggle" id="logsLabel" role="button" data-toggle="dropdown" data-target="#" href="/${appPath}/admin/log">
		   统计分析
		    <b class="caret"></b>
		  </a>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="logsLabel">
		  		<li><a href="${appPath}/admin/log/work">工作量统计</a></li>
		  		<li><a href="${appPath}/admin/log/statistical">访问量分析</a></li>
		  		<li><a href="${appPath}/admin/log/0/list">系统日志信息</a></li>
		  </ul>
	   </li>
       <li class="dropdown">
         <a class="dropdown-toggle" id="userLabel" role="button" data-toggle="dropdown" data-target="#" href="/${appPath}/admin/user">
		   用户/角色
		    <b class="caret"></b>
		  </a>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="userLabel">
		  		<li><a href="${appPath}/admin/user">用户</a></li>
		  		<li><a href="${appPath}/admin/userGroup">角色</a></li>
		  </ul>
	   </li>
      <li  class="dropdown"><a  class="dropdown-toggle" id="systemLabel" role="button" data-toggle="dropdown" data-target="#"    href="#">
      		系统
      		<b class="caret"></b>
      		</a>
      		 <ul class="dropdown-menu" role="menu" aria-labelledby="systemLabel">
		  		<li><a href="${appPath}/admin/sysParameter">参数管理</a></li>
		  		<li><a href="${appPath}/admin/library/model">字段模型管理</a></li>
		  		<li><a href="${appPath}/admin/sweet">关键字管理</a></li>
		  </ul>
      		
      </li>
      
    </ul>
  </div>
  
  <script type="text/javascript">
		var url = window.location.pathname;
		$('#topnav a').filter(function(index) {
		    return url.indexOf($(this).attr("href")) >= 0;
		}).parent().addClass('active');
		
		//退出系统友好提示
		var appPath="/pdcms";
		var logoutUrl = appPath+"/j_spring_security_logout?type=ADMIN";
		$('#logout_sys').click(function(){
			$('#dropdown-menu').hide();
			$('#logout_modal').modal('show');
			$('#logout_modal').find('.btn-primary').click(function(){
				window.location.href=logoutUrl;
			});
			
			$("#logout_modal").find('#logout_cancle').click(function(){
				$('#logout_modal').hide();
			});
		});
		
  </script>
</div>
<div class="modal hide fade form-horizontal" id="logout_modal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>操作确认</h3>
	</div>
	<div class="modal-body">
		<p>您确定要退出系统吗？</p>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" id="logout_ok">确定</a><a href="#" class="btn" data-dismiss="modal"  id="logout_cancle">取消</a> 
	</div>
	
</div>
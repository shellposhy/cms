<%@ page language="java" pageEncoding="UTF-8" %>
<div class="modal hide fade form-horizontal" id="delModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>确定删除</h3>
	</div>
	<div class="modal-body">
		<p>您确定要删除这些数据吗？</p>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary">确定</a>
		<a href="#" class="btn backBtn" data-dismiss="modal">取消</a> 
	</div>
</div>

<!-- model start -->
<div class="modal hide fade form-horizontal" id="comOperModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>确认操作</h3>
	</div>
	<div class="modal-body">
		<p>您确定要执行该操作吗？</p>
	</div>
	<div class="modal-footer">
		 <a href="#" class="btn btn-primary">确定</a>
			<a href="#" class="btn backBtn" data-dismiss="modal">取消</a>
	</div>
</div>
<!-- model end -->

<!-- footer -->
<div id="foot">
	<p class="pull-left">
		&copy; <a href="${appPath}/index.html" target="_blank">${appName }</a> 2017
	</p>
	<p class="pull-right">
		技术支持: <a href="#">南山智慧谷</a>
	</p>
	<a id="back-to-top" href="#"></a>
</div>
<!-- / footer -->
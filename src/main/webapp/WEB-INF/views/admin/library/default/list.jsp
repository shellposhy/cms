<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
	<body>
		<div id="content" class="span12 tree_separated">
			<div class="breadcrumb_warp">
				<ul class="breadcrumb ind_f_tree" value="61">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#">系统数据库</a><span	class="divider">/</span><a href="#" id="colname"></a></li>
				</ul>
				<input type="hidden"  id="libId" />
			</div>
			<div class="box-content">
				<div class="padlrn span12 action_buttons">
					<a class="btn btn-small" href="#" onclick="add_directory(0);" id="add_directory" target="_self"><i class="icon-plus"></i> 添加目录</a>
					<div class="input-append floatr">
						<input id="search_u_db" size="16" type="text" />
						<button id="search_u_db_btn" class="btn" type="button">搜索</button>
					</div>
				</div>
				<div class="content_wrap mt30 clearfix">
					<div class="left_tree">
						<ul id=directoryTree class="ztree green_tree_line"></ul>
					</div>
					<div class="right_con">
						<ul id="libraries" class="show_sel_itme mt10 clearfix">
							<h3 class='alert alert-info'>请选择左侧目录以查看数据库</h3>
						</ul>
						<div id="data_content" hidden="hidden">
							<div class="btn-toolbar floatl" id="addbtn">
								<div class="btn-group">
									<a class="btn btn-small edit_pop_link" id="add_to_dsu"  href="#" target="_self"><i class="icon-plus"></i> 添加</a>
								 	<a class="btn btn-small delete_list"  id="del_from_dsu" href="#"><i class="icon-trash"></i> 删除</a> 
								 	<a class="btn btn-small" id="into_as_search" href="#"><i class="icon-chevron-up"></i>高级查询</a>
								 	<a class="btn btn-small" id="data_copy" href="#"><i class="icon-folder-open"></i>数据复制</a>
								 	<!-- <a class="btn btn-small" id="data_all_move" href="#"><i class="icon-retweet"></i>数据移库</a> -->
								</div>
							</div>
							<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit " id="colDatas">
								<thead><tr><th>标题</th></tr></thead>
								<tbody></tbody>
								<tfoot><tr><th>标题</th></tr></tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- model start-->
		<div class="modal hide fade form-horizontal" id="copyDataModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>操作确认</h3>
			</div>
			<div class="modal-body">
				<div class="alert alert-danger" role="alert">
			       您确定要<strong>&nbsp;[批量复制]&nbsp;</strong>这些数据吗？
			    </div>
				<div id="copyBaseList" class="mt20"></div>
				<div class="alert alert-success mt20">
					<strong>说明：</strong>数据在不同数据库复制时，库与库之间类型保持一致（新闻库数据不能复制到视频库或图片库中）。
				</div>
			</div>
			<div class="modal-footer">
				 <a href="#" class="btn btn-primary">确定</a>
				 <a href="#" class="btn" data-dismiss="modal">取消</a>
			</div>
		</div>
		<!-- //model end -->
		<script type="text/javascript" 	src="${appPath}/admin/jscript/library/library.js"></script>
	</body>
</html>
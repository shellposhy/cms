/**
 * 页面初始化
 */
$(document).ready(function(){
	init_directory_edit();//初始化目录编辑页
});

//初始化目录编辑页
function init_directory_edit() {
	if($("#type_tree")[0]){
		$.ajax({
		url :  appPath+"/admin/system/library/directory/emptyTree",
		success : function(data) {
			treeRadioCom($("#type_tree .treeNew"),data);
			setTimeout("$('.treeSelId').click()",800);
		}
	});	
	}
}
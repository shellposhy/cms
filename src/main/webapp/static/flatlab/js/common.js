/**
 * 系统通用脚本处理类
 * 
 * @author shishb
 * @version 1.0
 */
$(document).ready(function() {
	base_direct();
});

// page direct
function base_direct() {
	$(".page-item").bind('click', function() {
		var val = $(this).attr("attr-href");
		if (val != '#') {
			window.location.href = val;
		}
	})
}

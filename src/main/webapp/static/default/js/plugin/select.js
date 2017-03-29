;
(function($) {
	$.fn.extend({
		iSelect : function(options) {
			var iset = {
				name : $('.selectitem'), // 容器
				select : $('.selectitem>dl'), // dl列表
				dropCss : 'drop', // 收藏状态dt的样式
				shrinkCss : 'shrink', // 展开状态dt的样式
				hoverCss : 'hover', // 鼠标划过dd时的样式
				clearTime : 100, // 允许用户快速划过不触发的时间(ms)
				dropTime : 100, // 展开时间(ms)
				shrinkTime : 100
			}
			options = options || {};
			$.extend(iset, options);
			var mainHeight = iset.name.height();// 容器高度
			var selectHeight = iset.select.height(); // dl实际高度
			var curTxt = iset.select.find('dt').html(); // dt默认html内容
			var self = null;
			var hoverElem = null; // 避免用户快速划过时触发事件
			iset.name.each(function() {$(this).hover(function() {
				self = this;
				hoverElem = setTimeout(function() {
					$(self).stop(true, false).animate({height : selectHeight}, iset.dropTime);
					if ($(self).find('dt').html() == curTxt) { // 判断是否有选择下拉列表,若无则改变dt样式
						$(self).find('dt').attr('class',iset.dropCss);
					}
					// dd的鼠标事件
					$(self).find('dd').mouseover(function() {$(this).addClass(iset.hoverCss).siblings().removeClass(iset.hoverCss);}).mousedown(function() { 
						$(self).find('dt').html($(this).html()).attr('class',$(this).attr('class'));
						$("#industry").val($(this).attr("val"));
						$(self).stop(true, false).animate({height : mainHeight}, iset.shrinkTime);
					}).removeClass(iset.hoverCss);
				}, iset.clearTime);
			},function() {
					// 鼠标移出后触发的事件
				clearTimeout(hoverElem);
				$(self).stop(true, false).animate({height : mainHeight}, iset.shrinkTime);
					if ($(self).find('dt').html() == curTxt) {$(self).find('dt').attr('class', iset.shrinkCss);}
				});
			})
		}
	})
})(jQuery);
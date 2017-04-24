var Script = function() {
	$('.tooltips').tooltip();
	$('.popovers').popover();
	$('.bxslider').show();
	$('.bxslider').bxSlider({
		minSlides : 4,
		maxSlides : 4,
		slideWidth : 276,
		slideMargin : 20
	})
}();
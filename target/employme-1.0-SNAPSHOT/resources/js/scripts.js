$(document).ready(function() {
	var speed = 300;
	$("#employer-reg-block").hide();
	$("#employee-reg-block").hide();
	$("#employee-reg-block").slideDown(speed);

	$('.reg-usertype-radio input:radio', this).each(function() {
		$(this).change(function(event) {
			if ($(this).val() == "employer") {
				$("#employer-reg-block").slideDown(speed);
				$("#employee-reg-block").slideUp(speed);
			} else {
				$("#employee-reg-block").slideDown(speed);
				$("#employer-reg-block").slideUp(speed);
			}
		});
	});
	
	//$("#extended-search-form").hide();

});
$(document).ready(function() {
	$("form").submit(function(event) {
		randomBackground();
		var tid = setInterval(randomBackground, 100);
	});
	background();
});
$(document).ready(function() {

	$("#slideshow").owlCarousel({

		// navigation : true, // Show next and prev buttons
		autoPlay : 2000,
		dots : true,
		singleItem : true,
		autoHeight : true,
		transitionStyle : "goDown"

	// "singleItem:true" is a shortcut for:
	// items : 1,
	// itemsDesktop : false,
	// itemsDesktopSmall : false,
	// itemsTablet: false,
	// itemsMobile : false

	});
});
function getRandomColor() {
	var letters = '0123456789ABCDEF'.split('');
	var color = '#';
	for (var i = 0; i < 6; i++) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}
function background() {
	var col1 = "#123D79";
	var col2 = "#1D8BC6";
	$("body").css(
			"background",
			"-webkit-linear-gradient(-90deg, " + col1 + ", " + col2
					+ ")  no-repeat " + col2);

	$("body").css(
			"background",
			"-o-linear-gradient(-90deg, " + col1 + ", " + col2
					+ ")  no-repeat " + col2);

	$("body").css(
			"background",
			"-moz-linear-gradient(-90deg, " + col1 + ", " + col2
					+ ") no-repeat  " + col2);

	$("body").css(
			"background",
			"linear-gradient(-90deg, " + col1 + ", " + col2 + ")  no-repeat "
					+ col2);
}
function randomBackground() {
	var col1 = getRandomColor();
	var col2 = getRandomColor();
	$("body").css(
			"background",
			"-webkit-linear-gradient(-90deg, " + col1 + ", " + col2
					+ ")  no-repeat " + col2);

	$("body").css(
			"background",
			"-o-linear-gradient(-90deg, " + col1 + ", " + col2
					+ ")  no-repeat " + col2);

	$("body").css(
			"background",
			"-moz-linear-gradient(-90deg, " + col1 + ", " + col2
					+ ") no-repeat  " + col2);

	$("body").css(
			"background",
			"linear-gradient(-90deg, " + col1 + ", " + col2 + ")  no-repeat "
					+ col2);
}
function abortTimer() { // to be called when you want to stop the timer
	clearInterval(tid);
}
$(document).ready(function() {
	$('.datepicker').datepicker({
	    format: 'dd.mm.yyyy' 
	});
});
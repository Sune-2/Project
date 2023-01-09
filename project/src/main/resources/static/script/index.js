$(function() {
	
	var ww = $(window).width();
	$(".cover, .cover>div").css("width",ww+"px");
	
	$(".cover>div").not(":eq(0)").hide();
	
	now = 0;
	imgs = 2;
	
	function fade() {
		now = now == imgs ? 0 : now += 1;
	$(".cover>div").eq(now-1).fadeOut(800);
	$(".cover>div").eq(now).fadeIn(800);
	
	$(".bullet>li").removeClass("hover");
	$(".bullet>li").eq(now).addClass("hover");
	
	}
	
	setInterval(fade, 3000);

	
	$(".bullet>li").click(function() {
		var bulletNumber = $(this).index();
		if(now == bulletNumber) return;
		else {
			$(".cover>div").not(":eq("+now+")").hide();
			$(".cover>div").eq(now).fadeOut(800);
			$(".cover>div").eq(bulletNumber).fadeIn(800); 
			
			$(".bullet>li").removeClass("hover");
			$(".bullet>li").eq(bulletNumber).addClass("hover");
			
			now = bulletNumber;
		}	
	
	});
		
});
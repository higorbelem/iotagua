$(document).ready(function(){

	$('#mySidenav').find("a").each(function(){

		 invisble = $(this).attr('href');
		 $(invisble).hide();

	});

	$('#main').first("div").show();
    $('.modal').modal();

});

$('#mySidenav a').on('click',function(){

	var invisble;

	$('#mySidenav').find("a").each(function(){

		 invisble = $(this).attr('href');
		 $(invisble).hide();

	});

	var visible = $(this).attr('href');
	$(visible).show();

});

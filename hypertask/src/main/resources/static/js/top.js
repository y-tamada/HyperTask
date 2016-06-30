/**
 * 
 */

$(document).ready(function(){
	$("#facebookLink").click(function(){
		$.ajax({
			type: 'post',
			url: $("#contextPath").val() + '/login',
			data: { 'snsType' : "facebook"},
			success: function(data){
				window.location.href = data;
			}
		});
	});
});
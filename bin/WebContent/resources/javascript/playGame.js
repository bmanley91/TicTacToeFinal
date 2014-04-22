$(document).ready(function(){
		$(".openPiece").click(function() {
			$("#xChoice").val($(this).attr("xPos"));
			$("#yChoice").val($(this).attr("yPos"));
			$("#gameChoiceForm").submit();
		});
		
		$("#logoutButton").click(function() {
			$("#logoutForm").submit();
		});
		
	});
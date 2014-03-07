<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.GameBoard" %>
<%@ page import="models.Game" %>
<%@ page import="models.Player" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<%@ include file="/resources/css/main.css" %>
</style>
<title>Game Board</title>
<script type="text/javascript">
    <jsp:include page="/resources/javascript/jquery/jquery-2.1.0.js" />
</script>
<script type="text/javascript">
    <jsp:include page="/resources/javascript/playGame.js" />
</script>
</head>
<body>
	<div id="wrapper">
		<% 
			ServletContext sc = this.getServletContext();
			String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
			
		
		
			
			Player user = (Player)session.getAttribute("user");
			Game game = (Game)session.getAttribute("game");
			
			String msg = (String)request.getAttribute("msg");
			
			out.print("<a href='"+path+"/views/home.jsp'>Home</a>");
			
			if(msg != null)
				out.print("<h1>"+msg+"</h1>");
			
			
			if(game != null) {
				GameBoard board = game.board;
				int playersTurn = game.playersTurn;
				
				out.print(
						"<table id='gameBoard'>" +
							"<tbody>" +
								"<tr>"
						);
				int colCounter = 0;
				for(String i: game.getRow1Imgs()) {
					if(i.isEmpty() && !game.isWinner()) {
						if(colCounter != 1)
							out.print("<td class='openPiece' xPos='row1' yPos='"+colCounter+"'>");
						else
							out.print("<td xPos='row1' yPos='"+colCounter+"' class='middleCol openPiece'>");
					}
					else {
						if(colCounter != 1)
							out.print("<td class='closedPiece' xPos='row1' yPos='"+colCounter+"'>");
						else
							out.print("<td xPos='row1' yPos='"+colCounter+"' class='middleCol closedPiece'>" );
					}
					out.print(
							"<img src='"+i+"' />" +
							"</td>"
							);
					colCounter++;
				}
				out.print(
						"</tr>" +
						"<tr id='middleRow'>"
						);
				colCounter = 0;
				for(String i: game.getRow2Imgs()) {
					if(i.isEmpty() && !game.isWinner()) {
						if(colCounter != 1)
							out.print("<td class='openPiece' xPos='row2' yPos='"+colCounter+"'>");
						else
							out.print("<td xPos='row2' yPos='"+colCounter+"' class='middleCol openPiece'>");
					}
					else {
						if(colCounter != 1)
							out.print("<td class='closedPiece' xPos='row2' yPos='"+colCounter+"'>");
						else
							out.print("<td xPos='row2' yPos='"+colCounter+"' class='middleCol closedPiece'>");
					}
					out.print(
							"<img src='"+i+"' />" +
							"</td>"
							);
					colCounter++;
				}
				out.print(
						"</tr>" +
						"<tr>"
						);
				colCounter = 0;
				for(String i: game.getRow3Imgs()){
					if(i.isEmpty() && !game.isWinner()) {
						if(colCounter != 1)
							out.print("<td class='openPiece' xPos='row3' yPos='"+colCounter+"'>");
						else
							out.print("<td xPos='row3' yPos='"+colCounter+"' class='middleCol openPiece'>");
					}
					else {
						if(colCounter != 1)
							out.print("<td class='closedPiece' xPos='row2' yPos='"+colCounter+"'>");
						else
							out.print("<td xPos='row2' yPos='"+colCounter+"' class='middleCol closedPiece'>");
					}
					out.print(
							"<img src='"+i+"' />" +
							"</td>"
							);
					colCounter++;
				}
				
				out.print(
								"</tr>" +
							"</tbody>" +
						"<table>" +
						"<div id='legend'>" +
							"<div class='legendKey'>" +
								"<label class='inline'>"+game.getPlayer1().getName()+": <img class='imgKey' src='"+game.getPlayer1Img()+"' />" +
							"</div>" +
							"<div class='legendKey'>" +
								"<label class='inline'>"+game.getPlayer2().getName()+": <img class='imgKey'  src='"+game.getPlayer2Img()+"' />" +
							"</div>" +
						"</div>" +
						

						"<form action='startGame' method='post'>"+
							"<input type='submit' value='New Game'/>" +
							"<input type='hidden' name='name1' value='"+game.getPlayer1().getName()+"'/>" +
							"<input type='hidden' name='name2' value='"+game.getPlayer2().getName()+"'/>" +
						"</form>"
						);
				
				
				if(!game.isWinner())
					out.print(
						"<div class='hidden'>"+
							"<form action='game' method='post' id='gameChoiceForm'>" +
								"<input type='text' name='xChoice' id='xChoice'/>" +
								"<input type='text' name='yChoice' id='yChoice'/>" +
								"<input type='text' name='playersTurn' value='"+playersTurn+"'/>" +
							"</form>" +
						"</div>"
						);
				
				//if(game.winnerId != 0)
					//out.print("<h1 class='center'>"+game.getWinner().name+" Wins!</h1>");
				//else if(game.isTie)
					//out.print("<h1 class='center'>Its a Tie!</h1>");
			}
			else if(user != null){
				out.print("<form action='startGame' method='post'>");
				out.print("<label>Player 1: <label>");
				out.print("<input type='text' name='name1' readonly='true' value='"+user.name+"'/>");
				out.print("<input type='hidden' name='user1' value='"+user+"'/>");
				out.print("<label>Player 2: <label>");
				out.print("<input type='text' name='name2'/>");
				out.print("<input type='submit' value='Start Game'/>");
				out.print("</form>");
			}
			else {
				out.print("You are not logged in. <a href='"+path+"/views/login.jsp'>Home</a>");
			}
			
			//<img src="${pageContext.request.contextPath}/resources/images/redX.png" />
		%>
	</div>
	
</body>
</html>
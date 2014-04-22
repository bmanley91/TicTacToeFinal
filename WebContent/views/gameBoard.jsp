<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.GameBoard" %>
<%@ page import="models.Game" %>
<%@ page import="models.Player" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Game Board</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
<jsp:include page="/views/header.jsp" />
	<div class="innerContent">
		<% 
			ServletContext sc = this.getServletContext();
			String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
			Player user = (Player)session.getAttribute("user");
			Game game = (Game)request.getAttribute("game");
			
			//String isPC = null;
			
			String msg = (String)request.getAttribute("msg");
			if(msg != null)
				out.print("<span class='center'><h1>"+msg+"</h1></span>");
			
			
			if(game != null && user != null) {
				out.print("<h1>"+game.playersTurn+"</h1>");
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
				/*if(game.getPlayer2().isComp){
					isPC = "on";
				} */
				
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
							"<div class='center'><input type='submit' value='New Game'/><br/>" +

							"<label>Computer Opponent</label><select name='compDifficulty'>"+
							"<option value ='"+game.getPlayer2().diff+"'>Same</option>"+
							"<option value='0'>None</option>"+
							"<option value='1'>Easy</option>"+
							"<option value='2'>Med</option>"+
							"<option value='3'>Hard</option></select></div>"+
							
							"<input type='hidden' name='name1' value='"+game.getPlayer1().getName()+"'/>" +
							"<input type='hidden' name='name2' value='"+game.getPlayer2().getName()+"'/>" +
							"<input type='hidden' name= 'compDifficulty' value='"+game.getPlayer2().diff+"'/>" +
						"</form>"
						);
				
				
				if(!game.isWinner())
					out.print(
						"<div class='hidden'>"+
							"<form action='game' method='post' id='gameChoiceForm'>" +
								"<input type='text' name='xChoice' id='xChoice'/>" +
								"<input type='text' name='yChoice' id='yChoice'/>" +
								"<input type='text' name='playersTurn' value='"+playersTurn+"'/>" +
								"<input type='hidden' name='gameId' value='"+game.getId()+"'/>" +
							"</form>" +
						"</div>"
						);
			}
			else if(user != null){
				out.print("<form action='startGame' id='gameForm' method='post'>");
				out.print("<label>Player 1: <label>");
				out.print("<input type='text' name='name1' readonly='true' value='"+user.getName()+"'/>");
				out.print("<input type='hidden' name='user1' value='"+user+"'/>");
				out.print("<label>Player 2: <label>");
				out.print("<input type='text' name='name2'/>");
				out.print("<input type='submit' value='Start Game'/> <br>");
				out.print("<label>Computer Opponent<label>");
				out.print("<select name='compDifficulty'>");
				out.print("<option value='0'>None</option>");
				out.print("<option value='1'>Easy</option>");
				out.print("<option value='2'>Med</option>");
				out.print("<option value='3'>Hard</option>");
				out.print("</select>");
				out.print("<br>");
				
				out.print("</form>");
				
				out.print(
						
						"<form action='challengeFriend' id='challengeForm' method='post'/>" +
						"<input type='hidden' value='2' name='friendId'>" +
						"<input type='submit' value='challenge!'/>" +
						"</form>"
				);
			}
			else {
				out.print("You are not logged in. <a href='"+path+"/views/login.jsp'>Home</a>");
			}
			
			//<img src="${pageContext.request.contextPath}/resources/images/redX.png" />
		%>
	</div>
	
</body>
</html>
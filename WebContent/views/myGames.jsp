<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Games</title>
<jsp:include page="/views/include.jsp" />
<script>
	$(document).ready(function() {
		$(".goToGame").click(function() {
			$("#gameId").val($(this).attr("gameId"));
			$("#continueGameForm").submit();
			
		});
	});

</script>
</head>
<body>
<jsp:include page="/views/header.jsp" />
	<div class="innerContent">
		<%
		ServletContext sc = this.getServletContext();
		String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
		Player user = (Player)session.getAttribute("user");
		
		if(user != null) {
			String msg = (String)request.getAttribute("msg");
			if(msg != null)
				out.print("<span class='center'><h1>"+msg+"</h1></span>");
			List<Game> games = user.getAllGames();
			
			
			if(games.isEmpty())
				out.print("You have no open games, <a href='"+path+"/views/gameBoard.jsp'>click here to start one</a>");
			else {
				for(Game g: games) {
					Player p1 = g.getPlayer1();
					Player p2 = g.getPlayer2();
					
					out.print("<p class='goToGame' gameId='"+g.getId()+"'>");
					if(p1.equals(user))
						out.print("You VS " + p2);
					else
						out.print("You VS " + p1);
					if(g.getCurrentPlayer().equals(user))
						out.print(" - It's your turn!");
					out.print("</p>");
				}
			}
		}
		else
			out.print("You are not logged in. <a href='"+path+"/views/login.jsp'>Home</a>");
		
		%>
		
		<form class='hidden' id='continueGameForm' action='continueGameServlet' method='POST'>
			<input type='text' name='gameId' id='gameId'/>
		</form>		
		</div>
</body>
</html>
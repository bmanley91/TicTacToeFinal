<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.Player" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<jsp:include page="/views/header.jsp" />
	
	<div class='innerContent'>
	<a href='findfriends.jsp'>Find Friends!</a>
	<%
		ServletContext sc = this.getServletContext();
		String path = sc.getContextPath();
		String msg = (String)request.getParameter("msg");
		Player user = (Player)session.getAttribute("user");
		ArrayList<Player> playerList = (ArrayList)request.getAttribute("playerList");
		if(msg != null)
			out.print("<h1>"+msg+"</h1>");
		if(user==null) {
			out.print("You are not logged in. <a href='"+path+"/views/login.jsp'>Login</a>");
		}
		else{
			if(playerList != null)
				for(Player p: playerList){
					out.print("<p>"+p.getName()+ " Wins: "+p.getWins()+"</p>");
				}
			else
				out.print("The list is null");
			
		}
		
			%>
</div>
</body>
</html>
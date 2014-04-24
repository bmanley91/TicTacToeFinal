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
			for(int i=0; i< playerList.size(); i++){
				out.print("<p>"+playerList.get(i).getName()+ " Wins: "+playerList.get(i).getWins()+"</p>");
			}
			
		}
		
			%>
</div>
</body>
</html>
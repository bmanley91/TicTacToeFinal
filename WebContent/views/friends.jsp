<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.Player" %>
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
	<%
	Player player = (Player)session.getAttribute("user");
	ServletContext sc = this.getServletContext();
	String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
		String msg = request.getParameter("msg");
		String loggedInString = (String)session.getAttribute("loggedIn");
		boolean loggedIn = (loggedInString != null && loggedInString.equals("true"));
		if(msg != null)
			out.print("<h1>"+msg+"</h1>");
		if(!loggedIn) {
			out.print("You are not logged in. <a href='"+path+"/views/login.jsp'>Login</a>");
		}
		else{
			out.print("<a href='"+path+"/views/findfriends.jsp'>Search for Friends!</a>");
			for(int i=0; i<player.friends.size() ; i++){
				out.println(player.friends.get(i).username);
				}
			}
			%>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<%
ServletContext sc = this.getServletContext();
String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
		String msg = request.getParameter("msg");
		String loggedInString = (String)session.getAttribute("loggedIn");
		boolean loggedIn = (loggedInString != null && loggedInString.equals("true"));
		if(msg != null)
			out.print("<h1>"+msg+"</h1>");
		if(!loggedIn) {
			out.print("You are not logged in. <a href='/views/login.jsp'>Login</a>");
		}
		else {
			User user = (User)session.getAttribute("user");
			out.print(
					"<h1>"+user.name+", you have played "+user.games.size()+" games</h1>" +
					"<a href='"+path+"/views/gameBoard.jsp'>Start a game</a>"
					);
		}
		 %>		
</body>
</html>
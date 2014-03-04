<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<%
		String msg = request.getParameter("msg");
		String loggedInString = (String)session.getAttribute("loggedIn");
		boolean loggedIn = (loggedInString != null && loggedInString.equals("true"));
		if(msg != null)
			out.print("<h1>"+msg+"</h1>");
		if(!loggedIn) {
			out.print(
					"<form action='login' method='post'>"+
						"<label>Username: </label>" +
						"<input type='test' name='username' />" +
						"<label>Password: </label>" +
						"<input type='test' name='password' />" +
						"<input type='submit' value='Login'/>" +
					"</form>"
					);
			
			
		}
		else {
			User user = (User)session.getAttribute("user");
			out.print(
					"<a href='views/gameBoard.jsp'>Start a game</a>"
					);
		}
			
	
	%>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<jsp:include page="/views/header.jsp" />
	<div class="innerContent">
		<%
			ServletContext sc = this.getServletContext();
			String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
			String msg = (String)request.getAttribute("msg");
			Player player = (Player)session.getAttribute("user");	// Get player from session
			if(msg != null)
				out.print("<h1>"+msg+"</h1>");
			if(player==null) {										// If no player is found in session print login form
				out.print(
						"<form action='login' id='loginForm' method='post'>"+
							"<div>" +
							"<label>Username: </label>" +
							"<input type='text' name='username' />" +
							"<label>Password: </label>" +
							"<input type='password' name='password' />" +
							"</div>" +
							"<div class='center'>" +
							"<input type='submit' value='Login'/>" +
							"</div>" +
						"</form>" +
							"<a href='"+path+"/views/register.jsp'>Register</a>"
						);
				
				
			}
			else {													// If player is found in session do not print login form
				out.print(
						"<a href='views/gameBoard.jsp'>Start a local game</a>" +
						"<a href=''>Find Friends</a>"
						);
			}
				
		
		%>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ page import="models.Player" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<jsp:include page="/views/header.jsp" />
	<div class="innerContent">
		<%
			String msg = (String)request.getAttribute("msg");
			out.print(
						"<form action='reg' id='regForm' method='post'>"+
							"<div>" +
							"<label>Username: </label>" +
							"<input type='text' name='username' />" +
							"<label>Password: </label>" +
							"<input type='password' name='password' />" +
							"</div>" +
							"<label>Retype Password: </label>" +
							"<input type='password' name='retypePassword' />" +
							"</div>" +
							"<div class='center'>" +
							"<input type='submit' value='Register'/>" +
							"</div>" +
						"</form>" +
						"<a href='views/registration.jsp'>Register</a>");
			
		%>
	</div>

</body>
</html>
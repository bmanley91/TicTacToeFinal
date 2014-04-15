<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.Player" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Friends</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<jsp:include page="/views/header.jsp" />
	<div class='innerContent'>
	<%
	out.print(
			"<form action='search' id='friendsearch' method=post>"+
			"<div>"+
			"<label> Username </label>"+
			"<input type='text' name='friend'/>"+
			"</div>"+
			"<div class='center'>"+
			"<input type='submit' value='Search'/>"
			);
	
	%>
	</div>
</body>
</html>
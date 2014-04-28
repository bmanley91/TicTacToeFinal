<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Added Friend</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<jsp:include page="/views/header.jsp" />
	<div class='innerContent'>
	<%
		ServletContext sc = this.getServletContext();
		String path = sc.getContextPath();
		String msg = (String)request.getAttribute("msg");
		out.print("<p>"+msg+"</p>"+
				"<a href='"+path+"/views/findfriends.jsp'>Search Again</a>"
				);
	%>
</body>
</html>
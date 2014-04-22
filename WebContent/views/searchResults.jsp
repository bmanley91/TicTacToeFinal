<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Search Results</title>
<jsp:include page="/views/include.jsp" />
</head>
<body>
	<jsp:include page="/views/header.jsp" />
	<div class='innerContent'>
	<%
	ServletContext sc = this.getServletContext();
	String path = sc.getContextPath();
	ArrayList searchNames = (ArrayList)session.getAttribute("searchNames");
	ArrayList searchWins = (ArrayList)session.getAttribute("searchWins");
	if(searchNames==null){
		out.print("No results found.");
	}
	else{
		for(int i=0; i < searchNames.size(); i++){
			out.print("<p>"+searchNames.get(i)+ " Wins: "+searchWins.get(i)+"</p>");
		}
	}
	out.print("<a href='"+path+"/views/findfriends.jsp'>Search Again</a>");
	%>
</body>
</html>
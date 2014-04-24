<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page import="models.Player" %>
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
	ArrayList<Player> searchResults = (ArrayList<Player>)request.getAttribute("playerSearch");
	if(searchResults.size()==0){
		out.print("<p>No results found.</p>");
	}
	else{
		for(int i=0; i < searchResults.size(); i++){
			out.print("<p>"+searchResults.get(i).getName()+ " Wins: "+searchResults.get(i).getWins()+
					"<form action='addFriend' method='post'>"+
					"<input type='submit' name='button' value='Add Friend' />"+
					"</form>"+
					"</p>");
		}
	}
	out.print("<a href='"+path+"/views/findfriends.jsp'>Search Again</a>");
	%>
</body>
</html>
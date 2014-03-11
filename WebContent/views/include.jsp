<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%
ServletContext sc = this.getServletContext();
String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
%>
<style>
	<jsp:include page="/resources/css/main.css" />
</style>
<script type="text/javascript" src="<c:url value="/resources/javascript/jquery/jquery-2.1.0.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/javascript/playGame.js" />"></script>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
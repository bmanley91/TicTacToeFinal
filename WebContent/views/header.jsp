<%
ServletContext sc = this.getServletContext();
String path = sc.getContextPath();//This code get the path relative to the root web content directory of the project
%>
<nav id="header" class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<ul class="nav navbar-nav">
			<%
				out.print(
						"<li class='active'><a href='"+path+"/views/home.jsp'>Home</a></li>" +
						"<li><a href='"+path+"/views/friends.jsp'>Friends</a></li>" +
						"<li><a href='"+path+"/views/home.jsp'>Games</a></li>" +
						"<li><a id='logoutButton'>Logout</a></li>" 
						
						);
			%>
		</ul>
	</div>
</nav>
<div id="pushHeader"></div>

<div class='hidden'>
	<form action="logout" method="post" id="logoutForm">
	</form>
</div>
package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FindFriendServlet
 */
@WebServlet("/FindFriendServlet")
public class FindFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loggedInString = (String)session.getAttribute("loggedIn");
		boolean loggedIn = (loggedInString != null && loggedInString.equals("true"));
		String msg = null;
		
		if(loggedIn){
			
		}
		else{
			msg = "You are not logged in!";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);	// If not logged in send back to login page
		}
	}

}

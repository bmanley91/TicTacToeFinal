package controllers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Player;
import models.Database;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String username = request.getParameter("username"); 
		String password = request.getParameter("password"); 
		Player player = null;
		String url = "/views/home.jsp"; 
		String msg = null;
		
		
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			msg="Please fill out all fields";
			url = "/views/login.jsp"; 
		}
		else {
			System.out.println("eklse");
			player = Database.findPlayer(username, password);
		}
		if(player != null) {
			session.setAttribute("user", player);
			session.setAttribute("loggedIn", "true");
			msg=player.getName()+", you are logged in!";
		}
		else {
			msg="Incorrect username or password";
			url = "/views/login.jsp"; 
		}
		request.setAttribute("msg", msg);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
			dispatcher.forward(request, response);
		try {
			Database.DB_Close();
		} catch (Throwable e) {
			System.out.println("error closing connection");
			e.printStackTrace();
		}
	}

}

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;




import models.Database;
import models.Player;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String pw = request.getParameter("password");
		String rePw = request.getParameter("retypePassword");
		
		String url = "/views/home.jsp"; 
		String msg = null;
		Long newUserId = null;
		
		if(username == null || username.isEmpty() || pw.isEmpty() || rePw.isEmpty()){
			msg="Please fill out all fields.";
			url= "/views/login.jsp"; 
		}
		else{
			System.out.println("regElse");
			PreparedStatement regPlayer = null;
			ResultSet rs;
			String stmt =
					"INSERT INTO Player (p_username,p_password) VALUES(?,?)";
			try{
				regPlayer = Database.getConnection().prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
				regPlayer.setString(1, username);
				regPlayer.setString(2, pw);
				regPlayer.executeUpdate();
				rs = regPlayer.getGeneratedKeys();
				if (rs != null && rs.next()) {
					newUserId = rs.getLong(1);
				}
			}
			catch (SQLException | ClassNotFoundException s){
				System.out.println("SQL statement is not executed:");
				System.out.println(s);
				if (s instanceof SQLIntegrityConstraintViolationException) {
					System.out.println("error creating user");
					msg = "Username already exists";
					}
				}
			finally{
				if(regPlayer != null){
					try {
		           		regPlayer.close();
						} 
					catch (SQLException e) {
							e.printStackTrace();
						} 
				}
			}
			request.setAttribute("msg", msg);
			System.out.println("**"+newUserId);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url); 
				dispatcher.forward(request, response);
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}



}

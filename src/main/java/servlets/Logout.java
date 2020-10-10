package servlets;


import static servlets.ServletUtils.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
	public class Logout extends HttpServlet {
		private static final long serialVersionUID = 1L;
	   
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();		
			session.invalidate();
			request.logout();
			forwardWithAlert(request, response, "/index.jsp", "<h4>Logged out! Bye</h4>", "success");
		}
	}
		


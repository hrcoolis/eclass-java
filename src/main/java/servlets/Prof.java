package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Professor;
import dao.DAOFactory;

@WebServlet("/professor/*")
public class Prof extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Professor prof = (Professor) session.getAttribute("user");
		
		try {
			DAOFactory d = DAOFactory.getInstance();
			request.setAttribute("courses", d.getCourseDAO().list(prof));
//			request.setAttribute("assignments", d.getAssignmentDAO().list());
			forwardWithAlert(request, response, "/WEB-INF/professor/index.jsp");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/WEB-INF/professor/index.jsp", DB_ERROR);
		}
		
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	 }
}
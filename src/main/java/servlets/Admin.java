package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;

@WebServlet("/admin/*")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DAOFactory d = DAOFactory.getInstance();

	        request.setAttribute("professors", d.getProfessorDAO().list());
	        request.setAttribute("students", d.getStudentDAO().list());
	        request.setAttribute("courses", d.getCourseDAO().listAllByCode());
	        forwardWithAlert(request, response, "/WEB-INF/admin/index.jsp");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/WEB-INF/admin/index.jsp", DB_ERROR);
		}
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	 }

}

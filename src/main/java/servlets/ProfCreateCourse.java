package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Professor;
import beans.Role;
import dao.CourseDAO;
import dao.DAOFactory;

@WebServlet("/professor/create/course")
public class ProfCreateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nc_title = request.getParameter("nc_title");
		String nc_code = request.getParameter("nc_code");
		String nc_description = request.getParameter("nc_description");
		
		if (isEmpty(nc_title, nc_code, nc_description)) {
			forwardWithAlert(request, response, "/professor", PARAM_ERROR);
			return;
		}
		
		HttpSession session = request.getSession();
		Professor prof = (Professor) session.getAttribute("user");
		
		try {
			CourseDAO cDao = DAOFactory.getInstance().getCourseDAO();
			beans.Course course = new beans.Course();
			
			course.setCode(nc_code);
			course.setTitle(nc_title);
			course.setDescription(nc_description);
			course.setProfessor(prof);
			
			cDao.create(course);
			forwardWithAlert(request, response, "/professor", "New course " + course.getTitle() + " created sucessfully!", "success");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/professor", DB_ERROR);
		}
		
		
	}

}

package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import beans.Assignment;
import beans.Professor;
import dao.AssignmentDAO;
import dao.CourseDAO;
import dao.DAOFactory;

@WebServlet("/professor/create/assignment")
@MultipartConfig
public class ProfCreateAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a_title = request.getParameter("a_title");
		String a_cid = request.getParameter("a_cid");
		String a_description = request.getParameter("a_description");
		Float a_max_grade = safeFloat(request.getParameter("a_max_grade"));
		String a_deadline_ = request.getParameter("a_deadline");
		Part a_file = request.getPart("a_file");
		Date a_deadline;
		if (isEmpty(a_title, a_description, a_deadline_, a_cid, a_file.getSubmittedFileName())|| a_max_grade == null ) {
			forwardWithAlert(request, response, "/professor", PARAM_ERROR);
			return;
		}
		
		try {
			a_deadline = new SimpleDateFormat("yyyy-MM-dd").parse(a_deadline_);
		} catch (ParseException e1) {
			forwardWithAlert(request, response, "/professor", "Wrong date format");
			return;
		}
		
		HttpSession session = request.getSession();
		Professor prof = (Professor) session.getAttribute("user");
		
		try {
			DAOFactory d = DAOFactory.getInstance();
			AssignmentDAO aDao = d.getAssignmentDAO();
			beans.Course course = d.getCourseDAO().find(Long.parseLong(a_cid));
			
			if (course == null || !course.getProfessor().equals(prof)) {
				forwardWithAlert(request, response, "/professor", ACCESS_ERROR + "course");
				return;
			}
			
			Assignment assignment = new Assignment();
			assignment.setCourse(course);
			assignment.setTitle(a_title);
			assignment.setDescription(a_description);
			assignment.setMaxGrade(a_max_grade);
			assignment.setDeadline(a_deadline);
			assignment.setFile(a_file);

			aDao.create(assignment);
			forwardWithAlert(request, response, "/course/" + a_cid + "/assignments",
					"New assignment created sucessfully!", "success");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/professor", DB_ERROR);
		}
		
		
	}
}

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
import beans.AssignmentSubmission;
import beans.Professor;
import beans.Student;
import dao.AssignmentDAO;
import dao.AssignmentSubmissionDAO;
import dao.CourseDAO;
import dao.DAOFactory;

@WebServlet("/student/submit/assignment")
@MultipartConfig
public class StudSubmitAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long as_aid = safeLong(request.getParameter("as_aid"));
		String as_comment = request.getParameter("as_comment");
		Part as_file = request.getPart("as_file");
		if (isEmpty(as_comment, as_file.getSubmittedFileName()) || isEmpty(as_aid)) {
			forwardWithAlert(request, response, "/student/assignments", PARAM_ERROR);
			return;
		}
		
		HttpSession session = request.getSession();
		Student stud = (Student) session.getAttribute("user");
		
		try {
			DAOFactory d = DAOFactory.getInstance();
			AssignmentDAO aDao = d.getAssignmentDAO();
			AssignmentSubmissionDAO asDao = d.getAssignmentSubmissionDAO();
			
			Assignment assignment = aDao.find(as_aid);
			
			if (assignment == null) {
				forwardWithAlert(request, response, "/student/assignments", ACCESS_ERROR + "assignment");
				return;
			}
			
			AssignmentSubmission asSub = new AssignmentSubmission();
			asSub.setAssignment(assignment);
			asSub.setStudent(stud);
			asSub.setComment(as_comment);
			asSub.setFile(as_file);
			asDao.create(asSub);
			forwardWithAlert(request, response, "/assignment/" + as_aid,
					"Your assignment submeted sucessfully!", "success");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/student/assignments", DB_ERROR);
		}
		
		
	}
}

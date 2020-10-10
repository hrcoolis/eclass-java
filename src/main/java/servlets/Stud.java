package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import beans.Student;
import dao.DAOFactory;

@WebServlet("/student/*")
public class Stud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Student user = (Student) session.getAttribute("user");
		
		String pathInfo = request.getPathInfo();
		String page = "";
		if (pathInfo != null) {
			List<String> parts = Arrays.asList(pathInfo.split("/"));
			page = safeGetFromList(parts, 1);
		}

		try {
			DAOFactory d = DAOFactory.getInstance();
			
			switch (page) {
			case "announcements":
				request.setAttribute("announcements", d.getAnnouncementDAO().list(user));
				forwardWithAlert(request, response, "/WEB-INF/student/announcements.jsp");
				break;
			case "assignments":
				request.setAttribute("assignments", d.getAssignmentDAO().list(user));
				forwardWithAlert(request, response, "/WEB-INF/student/assignments.jsp");
				break;
			case "allcourses":
				request.setAttribute("AllcoursesList", d.getCourseDAO().listAllByTitle());
				request.setAttribute("registeredList", d.getCourseDAO().listOnlyIds(user));
				forwardWithAlert(request, response, "/WEB-INF/student/allCourses.jsp");
				break;
			case "teams":
				request.setAttribute("teams", d.getTeamDAO().list(user));
//				request.setAttribute("registeredList", d.getCourseDAO().listOnlyIds(user));
				forwardWithAlert(request, response, "/WEB-INF/student/teams.jsp");
				break;
			default:
				request.setAttribute("MycoursesList", d.getCourseDAO().list(user));
				forwardWithAlert(request, response, "/WEB-INF/student/index.jsp");
				break;
			}
		} catch (Exception e) {
			forwardWithAlert(request, response, "/WEB-INF/student/index.jsp", DB_ERROR);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		}

}

	


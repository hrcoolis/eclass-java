package servlets;
import static servlets.ServletUtils.*;
import static servlets.ServletUtils.isEmpty;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;

@WebServlet("/course/*")
public class Course extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Course() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// /courseId/{announcments,assignments}
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			forwardWithAlert(request, response, "/WEB-INF/course/index.jsp", PARAM_ERROR);
			return;
		}
		
		List<String> parts = Arrays.asList(pathInfo.split("/"));
		Long courseId = safeLong(safeGetFromList(parts, 1));
		String page = safeGetFromList(parts, 2);

		if (isEmpty(courseId)) {
			forwardWithAlert(request, response, "/WEB-INF/course/index.jsp", PARAM_ERROR);
			return;
		}
		
		try {
			DAOFactory d = DAOFactory.getInstance();
			beans.Course c = d.getCourseDAO().find(courseId);
			request.setAttribute("course", c);
			
			switch (page) {
			case "announcements":
				request.setAttribute("announcements", d.getAnnouncementDAO().list(c));
				request.setAttribute("assignments", d.getAssignmentDAO().list(c));
				forwardWithAlert(request, response, "/WEB-INF/course/announcements.jsp");
				break;
			case "assignments":
				request.setAttribute("assignments", d.getAssignmentDAO().list(c));
				forwardWithAlert(request, response, "/WEB-INF/course/assignments.jsp");
				break;
			default:
				forwardWithAlert(request, response, "/WEB-INF/course/index.jsp");
				break;
			}
		} catch (Exception e) {
			forwardWithAlert(request, response, "/WEB-INF/course/index.jsp", DB_ERROR);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

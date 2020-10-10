package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Announcement;
import beans.Professor;
import dao.CourseDAO;
import dao.DAOFactory;

@WebServlet("/professor/create/announcement")
public class ProfCreateAnnouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na_title = request.getParameter("na_title");
		String na_description = request.getParameter("na_description");
		String na_cid = request.getParameter("na_cid");
		
		if (isEmpty(na_title, na_cid, na_description)) {
			forwardWithAlert(request, response, "/professor", PARAM_ERROR);
			return;
		}
		
		HttpSession session = request.getSession();
		Professor prof = (Professor) session.getAttribute("user");
		
		try {
			DAOFactory d = DAOFactory.getInstance();
			beans.Course course = d.getCourseDAO().find(Long.parseLong(na_cid));
			
			if (course == null || !course.getProfessor().equals(prof)) {
				forwardWithAlert(request, response, "/professor", ACCESS_ERROR + "course");
				return;
			}
			
			Announcement a = new Announcement();
			a.setTitle(na_title);
			a.setDescription(na_description);
			a.setCourse(course);
			d.getAnnouncementDAO().create(a);

			forwardWithAlert(request, response, "/course/" + na_cid + "/announcements",
				"New announcement created sucessfully!", "success");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/professor", DB_ERROR);
		}
		
		
	}

}

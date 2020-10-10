package servlets;

import static servlets.ServletUtils.isEmpty;
import static servlets.ServletUtils.safeLong;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Course;
import dao.CourseDAO;
import dao.DAOFactory;

@WebServlet("/search/course/*")
public class SearchCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerm = request.getParameter("searchTerm");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		if (isEmpty(searchTerm)) {
			out.printf("{\"%s\": \"%s\"}", "courses", "[]");
			return;
		}
		
		try {
			CourseDAO cdao = DAOFactory.getInstance().getCourseDAO();
			out.printf("{\"%s\": %s}", "courses", courseListToJson(cdao.listSearchByTitle(searchTerm)));
		} catch (Exception e) {
			out.printf("{\"%s\": \"%s\"}", "courses", "[]");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String courseListToJson(List<Course> cl) {
		String s = "[ ";
		int lsize = cl.size();
		for (int i = 0; i < lsize; ) {
			s += String.format("{ \"%s\" : \"%s\", \"%s\" : \"%s\"}", "id", cl.get(i).getId(), "title", cl.get(i).getTitle());
			if (++i < lsize ) s += ", ";
		}
		
		return s + "]";
	}

}

package servlets;

import static servlets.ServletUtils.DB_ERROR;
import static servlets.ServletUtils.PARAM_ERROR;
import static servlets.ServletUtils.forwardWithAlert;
import static servlets.ServletUtils.isEmpty;
import static servlets.ServletUtils.safeGetFromList;
import static servlets.ServletUtils.safeLong;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Student;
import dao.CourseDAO;
import dao.DAOFactory;

@WebServlet({ "/student/register/course/*", "/student/unregister/course/*" })
public class StudRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Student user = (Student) session.getAttribute("user");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			out.printf("{\"%s\": \"%s\"}", "result", "param_error");
			return;
		}
		
		List<String> parts = Arrays.asList(pathInfo.split("/"));
		Long targetId = safeLong(safeGetFromList(parts, 1));

		if (isEmpty(targetId)) {
			out.printf("{\"%s\": \"%s\"}", "result", "param_error");
			return;
		}
		
		try {
			CourseDAO cdao = DAOFactory.getInstance().getCourseDAO();
			beans.Course c = cdao.find(targetId);

			switch (request.getServletPath()) {
			case "/student/register/course":
				cdao.register(c, user);
				out.printf("{\"%s\": \"%s\"}", "result", "registered");
				break;
			case "/student/unregister/course":
				cdao.unregister(c, user);
				out.printf("{\"%s\": \"%s\"}", "result", "unregistered");
				break;
			default:
				out.printf("{\"%s\": \"%s\"}", "result", "invalid_action");
				break;
			}

		} catch (Exception e) {
			out.printf("{\"%s\": \"%s\"}", "result", "db_error");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

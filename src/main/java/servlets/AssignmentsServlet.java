package servlets;

import static servlets.ServletUtils.forwardWithAlert;
import static servlets.ServletUtils.isEmpty;
import static servlets.ServletUtils.safeGetFromList;
import static servlets.ServletUtils.safeLong;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Assignment;
import dao.DAOFactory;

@WebServlet("/assignment/*")
public class AssignmentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		List<String> parts = Arrays.asList(pathInfo.split("/"));
		Long targetId = safeLong(safeGetFromList(parts, 1));
		
		if (isEmpty(targetId)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			DAOFactory d = DAOFactory.getInstance();
			Assignment a = d.getAssignmentDAO().find(targetId);
			
			request.setAttribute("a", a);
			request.setAttribute("asubs", d.getAssignmentSubmissionDAO().list(a));
			forwardWithAlert(request, response, "/WEB-INF/assignment.jsp");
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

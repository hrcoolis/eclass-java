package servlets;

import static servlets.ServletUtils.isEmpty;
import static servlets.ServletUtils.safeGetFromList;
import static servlets.ServletUtils.safeLong;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Assignment;
import dao.DAOFactory;

@WebServlet({ "/download/assignment/*", "/download/assignmentsub/*" })
public class DownloadFile extends HttpServlet {
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
			switch (request.getServletPath()) {
			case "/download/assignment":
				sendFile(response, DAOFactory.getInstance().getAssignmentDAO().find(targetId).getFile());
				break;
			case "/download/assignmentsub":
				sendFile(response, DAOFactory.getInstance().getAssignmentSubmissionDAO().find(targetId).getFile());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void sendFile(HttpServletResponse response, InputStream fileStream) throws IOException {
                 OutputStream out = response.getOutputStream();
                 byte[] buffer = new byte[2048];
                 int bytesRead;
                 while((bytesRead = fileStream.read(buffer)) != -1) {
            	     out.write(buffer, 0, bytesRead);
                 }
	}

}

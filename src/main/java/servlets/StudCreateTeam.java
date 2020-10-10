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
import beans.Student;
import dao.CourseDAO;
import dao.DAOFactory;
import dao.TeamDAO;

@WebServlet("/student/create/team")
public class StudCreateTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nt_name= request.getParameter("nt_name");
		String nt_lastname = request.getParameter("nt_lastname");
		String nt_am = request.getParameter("nt_am");
		String nt_email = request.getParameter("nt_email");
		
		if (isEmpty(nt_name, nt_lastname, nt_am, nt_email)) {
			forwardWithAlert(request, response, "/student/teams", PARAM_ERROR);
			return;
		}
		
		HttpSession session = request.getSession();
		Student stud = (Student) session.getAttribute("user");
		
		try {
			TeamDAO tDao = DAOFactory.getInstance().getTeamDAO();
			beans.Team team = new beans.Team();
			team.setStudent_id(stud.getId());
			team.setName(nt_name);
			team.setLastname(nt_lastname);
			team.setAm(nt_am);
			team.setEmail(nt_email);
			
			tDao.create(team);
			forwardWithAlert(request, response, "/student/teams", "New member" + team.getName() + " added sucessfully!", "success");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/student/teams", DB_ERROR);
		}
		
		
	}

}

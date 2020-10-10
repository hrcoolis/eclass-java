package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Professor;
import beans.Student;
import beans.User;
import dao.DAOFactory;
import dao.UserDAO;

@WebFilter("/*")
public class UserFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String remoteUser = request.getRemoteUser();

		if (remoteUser != null) {
			HttpSession session = request.getSession();
			User user_tmp = (User) session.getAttribute("user");

			if (user_tmp == null || !user_tmp.getUsername().equals(remoteUser)) {
				DAOFactory df = DAOFactory.getInstance();
				
				if (request.isUserInRole("professor")) {
					Professor user = df.getProfessorDAO().find(remoteUser);
					session.setAttribute("user", user);
				} else if (request.isUserInRole("student")) {
					Student user = df.getStudentDAO().find(remoteUser);
					session.setAttribute("user", user);
				}
			}
		}
		
		chain.doFilter(req, res);
	}

}

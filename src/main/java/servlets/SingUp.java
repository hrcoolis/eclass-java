package servlets;

import static servlets.ServletUtils.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Role;
import beans.UserReg;
import dao.DAOFactory;
import dao.UserRegDAO;


@WebServlet("/SingUp/*")
public class SingUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reg_username = request.getParameter("reg_username");
		String reg_password = request.getParameter("reg_password");
		String reg_first_name = request.getParameter("reg_first_name");
		String reg_last_name = request.getParameter("reg_last_name");
		String reg_email = request.getParameter("reg_email");
		String reg_am = request.getParameter("reg_am");
		String reg_comment = request.getParameter("reg_comment");
		String reg_role_ = request.getParameter("reg_role");
		Role reg_role = Role.valueOf(reg_role_);

		if (isEmpty(reg_username, reg_password, reg_first_name, reg_last_name, reg_role_, reg_email)
				|| reg_role == Role.student && isEmpty(reg_am)) {
			forwardWithAlert(request, response, "/index.jsp", PARAM_ERROR);
			return;
		}

		try {
			UserRegDAO uregDAO = DAOFactory.getInstance().getUserRegDAO();

			UserReg ureq = new UserReg();
			ureq.setUsername(reg_username);
			ureq.setPasswordDigest(reg_password);
			ureq.setFirstName(reg_first_name);
			ureq.setLastName(reg_last_name);
			ureq.setEmail(reg_email);
			ureq.setAm(reg_am);
			ureq.setRole(reg_role);
			ureq.setComment(reg_comment);
			uregDAO.create(ureq);

			forwardWithAlert(request, response, "/index.jsp", "<h4>Account Registered Successfully!</h4>", "info");
		} catch (Exception e) {
			forwardWithAlert(request, response, "/index.jsp", DB_ERROR);
		}
	}

}

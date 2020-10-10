package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOFactory {
	private DataSource dataSource;

	public DAOFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	DAOFactory() {
	}

	public static DAOFactory getInstance() throws NullPointerException {
		Context initContext;
		Context envContext;
		DataSource dataSource;
		try {
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/postgres");
		} catch (NamingException e) {
			throw new NullPointerException("Resources error");
		}

		if (dataSource == null) {
			throw new NullPointerException("Please set jdbc/postgres resource in contex.xml");
		}
		return new DAOFactory(dataSource);
	}

	Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public UserDAO getUserDAO() {
		return new UserDAO(this);
	}


	public ProfessorDAO getProfessorDAO() {
		return new ProfessorDAO(this);
	}

	public StudentDAO getStudentDAO() {
		return new StudentDAO(this);
	}

	public CourseDAO getCourseDAO() {
		return new CourseDAO(this);
	}
	public TeamDAO getTeamDAO() {
		return new TeamDAO(this);
	}

	public UserRegDAO getUserRegDAO() {
		return new UserRegDAO(this);
	}

	public AnnouncementDAO getAnnouncementDAO() {
		return new AnnouncementDAO(this);
	}
	
	public AssignmentDAO getAssignmentDAO() {
		return new AssignmentDAO(this);
	}

	public AssignmentSubmissionDAO getAssignmentSubmissionDAO() {
		return new AssignmentSubmissionDAO(this);
	}
}

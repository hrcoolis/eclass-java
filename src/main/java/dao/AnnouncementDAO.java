package dao;

import static dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Announcement;
import beans.Course;
import beans.Professor;
import beans.Student;
import beans.User;


public class AnnouncementDAO {
	private static final String SQL_INSERT =
			"INSERT INTO announcemnts (course_id, title, description) VALUES (?,?,?) RETURNING id,date";
	private static final String SQL_UPDATE =
			"UPDATE announcemnts SET (title, description, date) = (?, ?, CURRENT_TIMESTAMP) WHERE id = ? RETURNING date";
	private static final String SQL_DELETE =
			"DELETE FROM announcemnts WHERE id = ?";
	private static final String SQL_LIST_BY_COURSE =
			"SELECT a.id AS a_id, a.course_id AS a_course_id, a.title AS a_title, a.description AS a_description, a.date AS a_date FROM announcemnts a WHERE course_id = ? ORDER BY date DESC";
	private static final String SQL_JOIN__ =
			  "SELECT a.id AS a_id, a.course_id AS a_course_id, a.title AS a_title, a.description AS a_description, a.date AS a_date, "
			+ "c.id AS c_id, c.code AS c_code, c.title AS c_title, c.professor_id AS c_professor_id, c.description AS c_description, "
			+ "p.id AS p_id, p.username AS p_username, p.first_name AS p_first_name, p.last_name AS p_last_name, p.email AS p_email, p.password AS p_password "
			+ "FROM announcemnts a "
			+ "INNER JOIN courses c ON a.course_id=c.id "
			+ "INNER JOIN professors p ON c.professor_id = p.id ";
	private static final String SQL_FIND_BY_ID = 
			SQL_JOIN__ + "WHERE a.id = ?";
	private static final String SQL_ORDER__ = "ORDER BY a.course_id, date DESC";
	private static final String SQL_LIST_BY_STUDENT = 
			SQL_JOIN__
			+ "INNER JOIN course_students as cs ON a.course_id = cs.course_id "
			+ "WHERE cs.student_id = ? "
			+ SQL_ORDER__;

	private DAOFactory daoFactory;
	
	AnnouncementDAO(DAOFactory daoFactory){
		this.daoFactory=daoFactory;
	}
	
	public Announcement find(long id) {
		Announcement announcement = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_ID, false, id);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				announcement = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return announcement;
	}

	public List<Announcement> list(Course course) {
		return list(SQL_LIST_BY_COURSE, course, course.getId());
	}
	
	public List<Announcement> list(Student student) {
		return list(SQL_LIST_BY_STUDENT, null, student.getId());
	}
	
	private List<Announcement> list(String sql, Course course, Object... values) {
		List<Announcement> announcements = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();){
			while(resultSet.next()) {
				announcements.add(course != null ? map(resultSet, course) : map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return announcements;
	}

	public void create(Announcement announcement) throws IllegalArgumentException, DAOException {
		if (announcement.getId() != null) {
			throw new IllegalArgumentException("Announcement is already created");
		}

		Object[] values =  {
				announcement.getCourse().getId(),
				announcement.getTitle(),
				announcement.getDescription()
		};
		
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				announcement.setDate(resultSet.getTimestamp("date"));
				announcement.setId(resultSet.getLong("id"));
			} else {
				throw new DAOException("Creating announcement failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void update(Announcement announcement) throws DAOException {
		if (announcement.getId() == null) {
			throw new IllegalArgumentException("announcement is not created yet, the announcement ID is null.");
		}

		Object[] values = { announcement.getTitle(), announcement.getDescription(), announcement.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				announcement.setDate(resultSet.getTimestamp("date"));
			} else {
				throw new DAOException("Updating announcement failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void delete(Announcement announcement) throws DAOException {
		Object value =  announcement.getId() ;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, value);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting announcement failed, no rows affected.");
			} else {
				announcement.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private static Announcement map(ResultSet resultSet) throws SQLException {
		return map(resultSet, mapCourse(resultSet));
	}
	
	private static Announcement map(ResultSet resultSet, Course course) throws SQLException {
		Announcement announcement = new Announcement();
		announcement.setId(resultSet.getLong("a_id"));
		announcement.setTitle(resultSet.getString("a_title"));
		announcement.setCourse(course);
		announcement.setDescription(resultSet.getString("a_description"));
		announcement.setDate(resultSet.getTimestamp("a_date"));
		return announcement;
	}
	
	private static Course mapCourse(ResultSet resultSet) throws SQLException {
		Course course = new Course();
		course.setId(resultSet.getLong("c_id"));
		course.setCode(resultSet.getString("c_code"));
		course.setTitle(resultSet.getString("c_title"));
		course.setProfessor(mapProf(resultSet));
		course.setDescription(resultSet.getString("c_description"));		
		return course;
	}
	
	private static Professor mapProf(ResultSet resultSet) throws SQLException {
		Professor professor = new Professor();
		professor.setId(resultSet.getLong("p_id"));
		professor.setUsername(resultSet.getString("p_username"));
		professor.setPassword(resultSet.getString("p_password"));
		professor.setFirstName(resultSet.getString("p_first_name"));
		professor.setLastName(resultSet.getString("p_last_name"));
		professor.setEmail(resultSet.getString("p_email"));
		return professor;
	}

}
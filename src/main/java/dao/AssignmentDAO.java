package dao;

import static dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Assignment;
import beans.Course;
import beans.Professor;
import beans.Student;

public class AssignmentDAO {

	private static final String SQL_INSERT =
			  "INSERT INTO assignments(course_id, title, description, max_grade, file, filename, deadline) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING creation_date, id";
	
	private static final String SQL_DELETE = "DELETE FROM assignments WHERE id = ?";
	
	private static final String SQL_JOINS__ =
			  "SELECT a.id AS a_id, a.course_id AS a_course_id, a.title AS a_title, a.description AS a_description, a.active AS a_active, a.max_grade AS a_max_grade, a.file AS a_file, a.filename AS a_filename, a.creation_date AS a_creation_date, a.deadline AS a_deadline, "
			+ "c.id AS c_id, c.code AS c_code, c.title AS c_title, c.professor_id AS c_professor_id, c.description AS c_description, "
			+ "p.id AS p_id, p.username AS p_username, p.first_name AS p_first_name, p.last_name AS p_last_name, p.email AS p_email, p.password AS p_password "
			+ "FROM assignments a INNER JOIN courses c ON a.course_id = c.id INNER JOIN professors p ON c.professor_id = p.id ";

	private static final String SQL_FIND_BY_ID = SQL_JOINS__ + "WHERE a.id = ?";
	
	private static final String SQL_LIST_BY_STUDENT =
			  SQL_JOINS__
			+ "INNER JOIN course_students cs on a.course_id = cs.course_id "
			+ "WHERE cs.student_id = ?";

	private static final String SQL_LIST_BY_COURSE =
			  "SELECT a.id AS a_id, a.course_id AS a_course_id, a.title AS a_title, a.description AS a_description, a.active AS a_active, a.max_grade AS a_max_grade, a.file AS a_file, a.filename AS a_filename, a.creation_date AS a_creation_date, a.deadline AS a_deadline "
			+ "FROM assignments a WHERE course_id = ?";

	private DAOFactory daoFactory;
	
	AssignmentDAO(DAOFactory daoFactory){
		this.daoFactory=daoFactory;
	}
	
	public Assignment find(long id) {
		Assignment assignment = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_ID, false, id);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				assignment = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return assignment;
	}
	
	public List<Assignment> list(Course course) {
		return list(SQL_LIST_BY_COURSE, course, course.getId());
	}
	
	public List<Assignment> list(Student student) {
		return list(SQL_LIST_BY_STUDENT, null, student.getId());
	}
	
	private List<Assignment> list(String sql, Course course, Object... values) {
		List<Assignment> assignments = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();){
			while(resultSet.next()) {
				assignments.add(course != null ? map(resultSet, course) : map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return assignments;
	}
	
	public void create(Assignment assignment) throws IllegalArgumentException, DAOException {
		if (assignment.getId() != null) {
			throw new IllegalArgumentException("Assignment is already created");
		}

		Object[] values =  {
				assignment.getCourse().getId(),
				assignment.getTitle(),
				assignment.getDescription(),
				assignment.getMaxGrade(),
				assignment.getFile(),
				assignment.getFilename(),
				toSqlDate(assignment.getDeadline())
		};
		
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				assignment.setCreationDate(resultSet.getTimestamp("creation_date"));
				assignment.setId(resultSet.getLong("id"));
			} else {
				throw new DAOException("Creating assignment failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void delete(Assignment assignment) throws DAOException {
		Object value =  assignment.getId() ;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, value);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting assignment failed, no rows affected.");
			} else {
				assignment.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private static Assignment map(ResultSet resultSet) throws SQLException {
		return map(resultSet, mapCourse(resultSet));
	}
	
	private static Assignment map(ResultSet resultSet, Course course) throws SQLException {
		Assignment assignment = new Assignment();
		assignment.setId(resultSet.getLong("a_id"));
		assignment.setCourse(course);
		assignment.setTitle(resultSet.getString("a_title"));
		assignment.setDescription(resultSet.getString("a_description"));
		assignment.setMaxGrade(resultSet.getFloat("a_max_grade"));
		assignment.setCreationDate(resultSet.getTimestamp("a_creation_date"));
		assignment.setDeadline(resultSet.getTimestamp("a_deadline"));
		assignment.setFile(resultSet.getBinaryStream("a_file"));
		assignment.setFilename(resultSet.getString("a_filename"));
		return assignment;
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
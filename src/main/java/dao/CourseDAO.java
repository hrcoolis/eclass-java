package dao;

import static dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Course;
import beans.Professor;
import beans.Student;



public class CourseDAO {
	
	private static final String SQL_INSERT_COURSE =
			"INSERT INTO courses (code, title, professor_id, description) VALUES (?,?,?,?)";
	private static final String SQL_DELETE_COURSE =
			"DELETE FROM courses WHERE id = ?";
	
	private static final String SQL_COURSES__ =
			"SELECT * FROM courses INNER JOIN professors ON professor_id = professors.id ";
	private static final String SQL_LIST_ORDER_BY_CODE = SQL_COURSES__ + "ORDER BY code";
	private static final String SQL_LIST_ORDER_BY_ID = SQL_COURSES__ + "ORDER BY courses.id";
	private static final String SQL_LIST_ORDER_BY_TITLE = SQL_COURSES__ + "ORDER BY title";
	private static final String SQL_FIND_BY_TITLE = SQL_COURSES__ + "WHERE title = ? ";
	private static final String SQL_FIND_BY_CODE = SQL_COURSES__ + "WHERE code = ? ";
	private static final String SQL_FIND_BY_ID = SQL_COURSES__ + "WHERE courses.id = ?";
	private static final String SQL_SEARCH_BY_TITLE = SQL_COURSES__ + "WHERE title ILIKE '%'||?||'%'";

	
	private static final String SQL_LIST_PROFESSORS_COURSES =
			"SELECT * FROM courses WHERE professor_id = ?";
	private static final String SQL_LIST_STUDENTS_COURSES =
			SQL_COURSES__ + "INNER JOIN course_students ON courses.id = course_students.course_id WHERE student_id = ?";
	

	private static final String SQL_LIST_STUDENTS_COURSES_IDSONLY =
			"SELECT course_id FROM course_students WHERE student_id = ?";
			
	private static final String SQL_REGISTER_STUDENT_TO_COURSE =
			"INSERT INTO course_students (course_id , student_id) VALUES (?,?)";
	private static final String SQL_UNREGISTER_STUDENT_FROM_COURSE =
			"DELETE FROM course_students WHERE course_id = ? AND student_id = ?";
	

	private DAOFactory daoFactory;
	
	CourseDAO(DAOFactory daoFactory){
		this.daoFactory=daoFactory;
	}
	
	// Actions
	// ------------------------------------------------------------------------------------
	public Course find(Long id) throws DAOException {
		return find(SQL_FIND_BY_ID , id);
	}

	public Course find(String title ) throws DAOException {
		return find(SQL_FIND_BY_TITLE, title);
	}
	
	public Course findByCode(String code) throws DAOException {
		return find(SQL_FIND_BY_CODE, code);
	}
	
	
	/**
	 * Returns the course from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql    The SQL query to be executed in the database.
	 * @param values The PreparedStatement values to be set.
	 * @return The course from the database matching the given SQL query with the
	 *         given values.
	 */
	private Course find(String sql, Object... values) throws DAOException {
		Course course = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				course = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return course;
	}

	public List<Course> listSearchByTitle(String searchTerm) throws DAOException{
		return list(SQL_SEARCH_BY_TITLE, searchTerm);
	}

	//// LISTS
	public List<Course> listAllById() throws DAOException{
		return list(SQL_LIST_ORDER_BY_ID);
	}
	public List<Course> listAllByTitle() throws DAOException{
		return list(SQL_LIST_ORDER_BY_TITLE);
	}
	public List<Course> listAllByCode() throws DAOException{
		return list(SQL_LIST_ORDER_BY_CODE);
	}
	
	public List<Course> list(Student student) throws DAOException{
		return list(SQL_LIST_STUDENTS_COURSES, student.getId());
	}
	
	public List<Long> listOnlyIds(Student student) throws DAOException{
		List<Long> coursesIds = new ArrayList<>();
	
		try(Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_LIST_STUDENTS_COURSES_IDSONLY, false, student.getId());
				ResultSet resultSet = statement.executeQuery();){
			while(resultSet.next()) {
				coursesIds.add(resultSet.getLong(1));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return coursesIds;
	}
	
	public List<Course> list(Professor professor) throws DAOException{
		return list(SQL_LIST_PROFESSORS_COURSES, professor, professor.getId());
	}
	
	private List<Course> list(String sql, Object... values) throws DAOException {
		return list(sql, null, values);
	}
	
	private List<Course> list(String sql, Professor prof, Object... values) throws DAOException {
		List<Course> courses = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();){
			while(resultSet.next()) {
				courses.add(prof != null ? map(resultSet, prof) : map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return courses;
	}
	
	public void register(Course course, Student student) throws IllegalArgumentException, DAOException {
		Object[] values =  { course.getId(), student.getId() };
		
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_REGISTER_STUDENT_TO_COURSE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Registering course failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void unregister(Course course, Student student) throws IllegalArgumentException, DAOException {
		Object[] values =  { course.getId(), student.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_UNREGISTER_STUDENT_FROM_COURSE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Unregister course failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void create(Course course) throws IllegalArgumentException, DAOException {
		if (course.getId() != null) {
			throw new IllegalArgumentException("Course is already created");
		}

		Object[] values =  {
				course.getCode(),
				course.getTitle(),
				course.getProfessor().getId(),
				course.getDescription()
		};
		
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT_COURSE, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating course failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					course.setId(generatedKeys.getLong(1));
				} else {
					throw new DAOException("Creating course failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void delete(Course course) throws DAOException {
		Object value =  course.getId() ;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE_COURSE , false, value);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting course failed, no rows affected.");
			} else {
				course.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	
	// Helpers
	// ------------------------------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to a Course
	 * @param resultSet The ResultSet of which the current row is to be mapped to a Course.
	 * @return The mapped Course from the current row of the given ResultSet.
	 */
	
	private static Course map(ResultSet resultSet) throws SQLException {
		return map(resultSet, mapProf(resultSet));
	}
	
	private static Course map(ResultSet resultSet, Professor professor) throws SQLException {
		Course course = new Course();
		course.setId(resultSet.getLong("id"));
		course.setCode(resultSet.getString("code"));
		course.setTitle(resultSet.getString("title"));
		course.setProfessor(professor);
		course.setDescription(resultSet.getString("description"));		
		return course;
	}
	
	private static Professor mapProf(ResultSet resultSet) throws SQLException {
		Professor professor = new Professor();
		professor.setId(resultSet.getLong("professor_id"));
		professor.setUsername(resultSet.getString("username"));
		professor.setPassword(resultSet.getString("password"));
		professor.setFirstName(resultSet.getString("first_name"));
		professor.setLastName(resultSet.getString("last_name"));
		professor.setEmail(resultSet.getString("email"));
		return professor;
	}

}

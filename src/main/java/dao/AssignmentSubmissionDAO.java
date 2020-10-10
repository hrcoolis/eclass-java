package dao;

import static dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Assignment;
import beans.AssignmentSubmission;
import beans.Course;
import beans.Student;

public class AssignmentSubmissionDAO {
	
	private static final String SQL_INSERT =
			  "INSERT INTO assignment_submissions(assignment_id, student_id, comment, file, filename) "
			+ "VALUES(?, ?, ?, ?, ?) RETURNING submission_date, id";
	private static final String SQL_DELETE = "DELETE FROM assignment_submissions WHERE id = ?";
	private static final String SQL_UPDATE_GRADE =
			  "UPDATE assignment_submissions SET (grade, grade_comment, grade_submission_date) = (?, ?, CURRENT_TIMESTAMP) "
			+ "WHERE id = ? RETURNING grade_submission_date";
	private static final String SQL_FIND_BY_ID = 
			"SELECT * FROM assignment_submissions WHERE id = ?";
	private static final String SQL_LIST_BY_ASSIGNMENT =
			"SELECT * FROM assignment_submissions WHERE assignment_id = ?";
	private static final String SQL_LIST_BY_STUDENT =
			"SELECT * FROM assignment_submissions WHERE student_id = ?";
	
	private DAOFactory daoFactory;
	
	AssignmentSubmissionDAO(DAOFactory daoFactory){
		this.daoFactory=daoFactory;
	}
	
	public AssignmentSubmission find(long id) {
		AssignmentSubmission asu = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_ID, false, id);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				Student s = daoFactory.getStudentDAO().find(resultSet.getLong("student_id"));
				Assignment a = daoFactory.getAssignmentDAO().find(resultSet.getLong("assignment_id"));
				asu = map(resultSet, a, s);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return asu;
	}
	
	public List<AssignmentSubmission> list(Assignment assignment) {
		return list(SQL_LIST_BY_ASSIGNMENT, assignment, null, assignment.getId());
	}
	
	public List<AssignmentSubmission> list(Student student) {
		return list(SQL_LIST_BY_STUDENT, null, student, student.getId());
	}
	
	private List<AssignmentSubmission> list(String sql, Assignment a, Student s, Object... values) {
		List<AssignmentSubmission> asus = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();){
			while(resultSet.next()) {
				asus.add(map(
						resultSet,
						a != null ? a : daoFactory.getAssignmentDAO().find(resultSet.getLong("assignment_id")),
						s != null ? s : daoFactory.getStudentDAO().find(resultSet.getLong("student_id"))
				));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return asus;
	}
	
	public void create(AssignmentSubmission asu) throws IllegalArgumentException, DAOException {
		if (asu.getId() != null) {
			throw new IllegalArgumentException("Assignment sub is already created");
		}

		Object[] values =  {
				asu.getAssignment().getId(),
				asu.getStudent().getId(),
				asu.getComment(),
				asu.getFile(),
				asu.getFilename()
		};
		
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				asu.setSubmissionDate(resultSet.getTimestamp("submission_date"));
				asu.setId(resultSet.getLong("id"));
			} else {
				throw new DAOException("Creating assignment sub failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void grade(AssignmentSubmission asu) throws DAOException {
		if (asu.getId() == null) {
			throw new IllegalArgumentException("assignment is not created yet, the assignment ID is null.");
		}

		Object[] values = { asu.getGrade(), asu.getGradeComment(), asu.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_GRADE, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				asu.setGradeSubmissionDate(resultSet.getTimestamp("grade_submission_date"));
			} else {
				throw new DAOException("Updating Grade failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void delete(AssignmentSubmission asu) throws DAOException {
		Object value =  asu.getId() ;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, value);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting assignment sub failed, no rows affected.");
			} else {
				asu.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	private static AssignmentSubmission map(ResultSet resultSet, Assignment assignment, Student student) throws SQLException {
		AssignmentSubmission asu = new AssignmentSubmission();
		asu.setId(resultSet.getLong("id"));
		asu.setAssignment(assignment);
		asu.setStudent(student);
		asu.setComment(resultSet.getString("comment"));
		asu.setSubmissionDate(resultSet.getTimestamp("submission_date"));
		asu.setGrade(resultSet.getFloat("grade"));
		asu.setGradeComment(resultSet.getString("grade_comment"));
		asu.setGradeSubmissionDate(resultSet.getTimestamp("grade_submission_date"));
		asu.setFile(resultSet.getBinaryStream("file"));
		asu.setFilename(resultSet.getString("filename"));
		return asu;
	}
}
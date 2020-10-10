package dao;

import static dao.DAOUtil.*;

import beans.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
	private static final String SQL_FIND_BY_ID =
			"SELECT id, username, password, first_name, last_name, email, am FROM students WHERE id = ?";
	private static final String SQL_FIND_BY_USERNAME =
			"SELECT id, username, password, first_name, last_name, email, am FROM students WHERE username = ?";
	private static final String SQL_LIST_ORDER_BY_ID =
			"SELECT id, username, password, first_name, last_name, email, am FROM students ORDER BY id";
	private static final String SQL_INSERT =
			"INSERT INTO students (username, password, first_name, last_name, email, am) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE =
			"UPDATE students SET username = ?, first_name = ?, last_name = ?, email = ?, am = ? WHERE id = ?";
	private static final String SQL_DELETE =
			"DELETE FROM students WHERE id = ?";

	protected DAOFactory daoFactory;
	
	StudentDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	//
	// Actions
	// ------------------------------------------------------------------------------------

	public Student find(Long id) throws DAOException {
		return find(SQL_FIND_BY_ID, id);
	}

	public Student find(String username) throws DAOException {
		return find(SQL_FIND_BY_USERNAME, username);
	}

	private Student find(String sql, Object... values) throws DAOException {
		Student student = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				student = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return student;
	}

	public List<Student> list() throws DAOException {
		List<Student> students = new ArrayList<>();

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				students.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return students;
	}

	public void create(Student student) throws IllegalArgumentException, DAOException {
		if (student.getId() != null) {
			throw new IllegalArgumentException("Student is already created, the user ID is not null.");
		}

		Object[] values = { 
				student.getUsername(),
				student.getPassword(),
				student.getFirstName(),
				student.getLastName(),
				student.getEmail(),
				student.getAm()};

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating student failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					student.setId(generatedKeys.getLong(1));
				} else {
					throw new DAOException("Creating student failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Student student) throws DAOException {
		if (student.getId() == null) {
			throw new IllegalArgumentException("Student is not created yet, the student ID is null.");
		}

		Object[] values = {
				student.getUsername(),
				student.getFirstName(),
				student.getLastName(),
				student.getEmail(),
				student.getAm(),
				student.getId()};

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Updating student failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void delete(Student student) throws DAOException {
		Object[] values = { student.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting student failed, no rows affected.");
			} else {
				student.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void changePassword(Student student) throws DAOException {
		UserDAO udao = new UserDAO(this.daoFactory);
		udao.changePassword(student);
	}

	// Helpers
	// ------------------------------------------------------------------------------------
	private static Student map(ResultSet resultSet) throws SQLException {
		Student student = new Student();
		student.setId(resultSet.getLong("id"));
		student.setUsername(resultSet.getString("username"));
		student.setPassword(resultSet.getString("password"));
		student.setFirstName(resultSet.getString("first_name"));
		student.setLastName(resultSet.getString("last_name"));
		student.setEmail(resultSet.getString("email"));
		student.setAm(resultSet.getString("am"));
		return student;
	}

}

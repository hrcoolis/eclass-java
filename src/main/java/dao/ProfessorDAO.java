package dao;

import static dao.DAOUtil.*;

import beans.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
	private static final String SQL_FIND_BY_ID =
			"SELECT id, username, password, first_name, last_name, email FROM professors WHERE id = ?";
	private static final String SQL_FIND_BY_USERNAME =
			"SELECT id, username, password, first_name, last_name, email FROM professors WHERE username = ?";
	private static final String SQL_LIST_ORDER_BY_ID =
			"SELECT id, username, password, first_name, last_name, email FROM professors ORDER BY id";
	private static final String SQL_INSERT =
			"INSERT INTO professors (username, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE =
			"UPDATE professors SET username = ?, first_name = ?, last_name = ?, email = ? WHERE id = ?";
	private static final String SQL_DELETE =
			"DELETE FROM professors WHERE id = ?";

	protected DAOFactory daoFactory;
	
	ProfessorDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	//
	// Actions
	// ------------------------------------------------------------------------------------

	public Professor find(Long id) throws DAOException {
		return find(SQL_FIND_BY_ID, id);
	}

	public Professor find(String username) throws DAOException {
		return find(SQL_FIND_BY_USERNAME, username);
	}

	private Professor find(String sql, Object... values) throws DAOException {
		Professor professor = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				professor = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return professor;
	}

	public List<Professor> list() throws DAOException {
		List<Professor> professors = new ArrayList<>();

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				professors.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return professors;
	}

	public void create(Professor professor) throws IllegalArgumentException, DAOException {
		if (professor.getId() != null) {
			throw new IllegalArgumentException("Professor is already created, the user ID is not null.");
		}

		Object[] values = { 
				professor.getUsername(),
				professor.getPassword(),
				professor.getFirstName(),
				professor.getLastName(),
				professor.getEmail()};

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating professor failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					professor.setId(generatedKeys.getLong(1));
				} else {
					throw new DAOException("Creating professor failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Professor professor) throws DAOException {
		if (professor.getId() == null) {
			throw new IllegalArgumentException("Professor is not created yet, the professor ID is null.");
		}

		Object[] values = {
				professor.getUsername(),
				professor.getFirstName(),
				professor.getLastName(),
				professor.getEmail(),
				professor.getId()};

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Updating professor failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void delete(Professor professor) throws DAOException {
		Object[] values = { professor.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting professor failed, no rows affected.");
			} else {
				professor.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void changePassword(Professor professor) throws DAOException {
		UserDAO udao = new UserDAO(this.daoFactory);
		udao.changePassword(professor);
	}
	
	// Helpers
	// ------------------------------------------------------------------------------------
	private static Professor map(ResultSet resultSet) throws SQLException {
		Professor professor = new Professor();
		professor.setId(resultSet.getLong("id"));
		professor.setUsername(resultSet.getString("username"));
		professor.setPassword(resultSet.getString("password"));
		professor.setFirstName(resultSet.getString("first_name"));
		professor.setLastName(resultSet.getString("last_name"));
		professor.setEmail(resultSet.getString("email"));
		return professor;
	}

}

package dao;

import static dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;

public class UserDAO {
	private static final String SQL_FIND_BY_ID =
			"SELECT id, username, password FROM users WHERE id = ?";
	private static final String SQL_FIND_BY_USERNAME =
			"SELECT id, username, password FROM users WHERE username = ?";
	private static final String SQL_LIST_ORDER_BY_ID =
			"SELECT id, username FROM users ORDER BY id";
	private static final String SQL_INSERT =
			"INSERT INTO users (username, password) VALUES (?, ?)";
	private static final String SQL_UPDATE =
			"UPDATE users SET username = ? WHERE id = ?";
	private static final String SQL_DELETE =
			"DELETE FROM users WHERE id = ?";
	private static final String SQL_EXIST_USERNAME =
			"SELECT id FROM users WHERE username = ?";
	private static final String SQL_CHANGE_PASSWORD =
			"UPDATE users SET password = ? WHERE id = ?";
	
	private DAOFactory daoFactory;

	UserDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions
	// ------------------------------------------------------------------------------------

	public User find(Long id) throws DAOException {
		return find(SQL_FIND_BY_ID, id);
	}

	public User find(String username) throws DAOException {
		return find(SQL_FIND_BY_USERNAME, username);
	}

	/**
	 * Returns the user from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql    The SQL query to be executed in the database.
	 * @param values The PreparedStatement values to be set.
	 * @return The user from the database matching the given SQL query with the
	 *         given values.
	 */
	private User find(String sql, Object... values) throws DAOException {
		User user = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				user = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return user;
	}

	public List<User> list() throws DAOException {
		List<User> users = new ArrayList<>();

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				users.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return users;
	}

	public void create(User user) throws IllegalArgumentException, DAOException {
		if (user.getId() != null) {
			throw new IllegalArgumentException("User is already created, the user ID is not null.");
		}

		Object[] values = { user.getUsername(), user.getPassword() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getLong(1));
				} else {
					throw new DAOException("Creating user failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(User user) throws DAOException {
		if (user.getId() == null) {
			throw new IllegalArgumentException("User is not created yet, the user ID is null.");
		}

		Object[] values = { user.getUsername(), user.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Updating user failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void delete(User user) throws DAOException {
		Object[] values = { user.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting user failed, no rows affected.");
			} else {
				user.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean existUsername(String username) throws DAOException {
		Object[] values = { username };

		boolean exist = false;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_EXIST_USERNAME, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			exist = resultSet.next();
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return exist;
	}

	public void changePassword(User user) throws DAOException {
		if (user.getId() == null) {
			throw new IllegalArgumentException("User is not created yet, the user ID is null.");
		}

		Object[] values = { user.getPassword(), user.getId() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_CHANGE_PASSWORD, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Changing password failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	// Helpers
	// ------------------------------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to an User.
	 * 
	 * @param resultSet The ResultSet of which the current row is to be mapped to an User.
	 * @return The mapped User from the current row of the given ResultSet.
	 */
	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		return user;
	}
}

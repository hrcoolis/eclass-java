package dao;

import static dao.DAOUtil.*;

import beans.Role;
import beans.Student;
import beans.User;
import beans.UserReg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRegDAO {
	
	
	private static final String SQL_INSERT_ST =
			"INSERT INTO students (username, first_name, last_name, email, am, password)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_PR =
			"INSERT INTO professors (username, first_name, last_name, email, password)"
			+ " VALUES (?, ?, ?, ?, ?)";


	protected DAOFactory daoFactory;

	UserRegDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	

	

	public List<UserReg> list(String sql) throws DAOException {
		List<UserReg> ureqs = new ArrayList<>();

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				ureqs.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return ureqs;
	}

	public void create(UserReg ureg) throws IllegalArgumentException, DAOException {
		if (ureg.getId() != null) {
			throw new IllegalArgumentException("User is already created, the user ID is not null.");
		}

		Object[] values_st = {
				ureg.getUsername(),
				ureg.getFirstName(),
				ureg.getLastName(),
				ureg.getEmail(),
				ureg.getAm(),
				ureg.getPassword() };
		
		Object[] values_pr = {
				ureg.getUsername(),
				ureg.getFirstName(),
				ureg.getLastName(),
				ureg.getEmail(),
				ureg.getPassword() };

		try (Connection connection = daoFactory.getConnection();) {
				String role = ureg.getRole().name();
				PreparedStatement statement = null;
				int affectedRows = 0;
			if(role == "student") {
				statement = prepareStatement(connection, SQL_INSERT_ST, true, values_st);
			}
			else {
				statement = prepareStatement(connection, SQL_INSERT_PR, true, values_pr);
			}
			
			 affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new DAOException("Creating user request failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					ureg.setId(generatedKeys.getLong(1));
				} else {
					throw new DAOException("Creating user request failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	

	private static UserReg map(ResultSet resultSet) throws SQLException {
		UserReg ureg = new UserReg();
		ureg.setId(resultSet.getLong("id"));
		ureg.setUsername(resultSet.getString("username"));
		ureg.setPassword(resultSet.getString("password"));
		ureg.setFirstName(resultSet.getString("first_name"));
		ureg.setLastName(resultSet.getString("last_name"));
		ureg.setEmail(resultSet.getString("email"));
		ureg.setAm(resultSet.getString("am"));
		ureg.setRole(Role.valueOf(resultSet.getString("role")));
		ureg.setComment(resultSet.getString("comment"));
		
		
		return ureg;
	}
}
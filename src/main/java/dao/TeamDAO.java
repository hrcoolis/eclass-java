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
import beans.Team;

public class TeamDAO {

	private static final String SQL_INSERT =
			  "INSERT INTO teams(student_id, name, lastname, am, email) "
			+ "VALUES(?, ?, ?, ?, ?) RETURNING id";
	
	
	private static final String SQL_DELETE = "DELETE FROM teams WHERE id = ?";
	
	private static final String SQL_JOINS__ =
			  "SELECT t.id AS t_id, t.student_id AS t_student_id, t.name AS t_name, t.lastname AS t_lastname, t.am AS t_am, t.email AS t_email"
			+ "s.id AS s_id"
			+ "FROM teams t INNER JOIN students s ON t.student_id = s.id ";

	private static final String SQL_FIND_BY_ID = SQL_JOINS__ + "WHERE t.id = ?";
	private static final String SQL_LIST_BY_STUDENT =
			"SELECT  teams.name AS t_name, teams.lastname AS t_lastname, teams.am AS t_am, teams.email AS t_email FROM teams WHERE teams.student_id= ?";
	
	
	private DAOFactory daoFactory;
	
	TeamDAO(DAOFactory daoFactory){
		this.daoFactory=daoFactory;
	}
	
	public Team find(long id) {
		Team team = null;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_ID, false, id);
			
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				team = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return team;
	}
	
//	public List<Assignment> list(Course course) {
//		return list(SQL_LIST_BY_COURSE, course, course.getId());
//	}
//	
//	public List<Assignment> list(Student student) {
//		return list(SQL_LIST_BY_STUDENT, null, student.getId());
//	}
	
//	private List<Assignment> list(String sql, Student student, Object... values) {
//		List<Assignment> teams = new ArrayList<>();
//		
//		try(Connection connection = daoFactory.getConnection();
//				PreparedStatement statement = prepareStatement(connection, sql, false, values);
//				ResultSet resultSet = statement.executeQuery();){
//			while(resultSet.next()) {
//				teams.add(student != null ? map(resultSet, student) : map(resultSet));
//			}
//		} catch (SQLException e) {
//			throw new DAOException(e);
//		}
//		return teams;
//	}
	
//	
	public List<Team> list(Student student) {
		return list(SQL_LIST_BY_STUDENT, null, student.getId());
	}
	
	private List<Team> list(String sql, Team team, long id) {
		List<Team> teams = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, sql, false, id);
				ResultSet resultSet = statement.executeQuery();){
			while(resultSet.next()) {
				teams.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return teams;
	}
	
	public void create(Team team) throws IllegalArgumentException, DAOException{
		if (team.getId() != null) {
			throw new IllegalArgumentException("Team is already created");
		}

		Object[] values =  {
			team.getStudent_id(),
			team.getName(),
			team.getLastname(),
			team.getAm(),
			team.getEmail()
		};
		
		try {
			
			Connection connection = daoFactory.getConnection();
			PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values);
			ResultSet resultSet = statement.executeQuery();
				 
			if (resultSet.next()) {
				team.setId(resultSet.getLong("id"));
			} else {
				throw new DAOException("Creating team failed, no rows affected.");
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	
	}
	public void delete(Team team) throws DAOException {
		Object value =  team.getId() ;

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, value);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting team failed, no rows affected.");
			} else {
				team.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

//	private static Student map(ResultSet resultSet) throws SQLException {
//		return map(resultSet, mapStudent(resultSet));
//	}
//	
	private static Team map(ResultSet resultSet) throws SQLException {
		Team team = new Team();
//		team.setId(resultSet.getLong("t_id"));
//		team.setStudent_id(resultSet.getLong("t_student_id"));
		team.setName(resultSet.getString("t_name"));
		team.setLastname(resultSet.getString("t_lastname"));
		team.setAm(resultSet.getString("t_am"));
		team.setEmail(resultSet.getString("t_email"));
		
		return team;
	}
	
//	private static Student mapStudent(ResultSet resultSet) throws SQLException {
//		Student student = new Student();
//		student.setId(resultSet.getLong("s_id"));
//				
//		return student;
//	}
	

	
}
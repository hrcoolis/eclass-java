package dao;

import java.sql.PreparedStatement;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import java.sql.Date;

public final class DAOUtil {
	private DAOUtil() {
	}

	/**
	 * Returns a PreparedStatement of the given connection, set with the given SQL
	 * query and the given parameter values.
	 * 
	 * @param connection          The Connection to create the PreparedStatement
	 *                            from.
	 * @param sql                 The SQL query to construct the PreparedStatement
	 *                            with.
	 * @param returnGeneratedKeys Set whether to return generated keys or not.
	 * @param values              The parameter values to be set in the created
	 *                            PreparedStatement.
	 */
	public static PreparedStatement prepareStatement(Connection connection, String sql,
			boolean returnGeneratedKeys, Object... values) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		setValues(statement, values);
		return statement;
	}

	/**
	 * Set the given parameter values in the given PreparedStatement.
	 * 
	 * @param connection The PreparedStatement to set the given parameter values in.
	 * @param values     The parameter values to be set in the created
	 *                   PreparedStatement.
	 */
	public static void setValues(PreparedStatement statement, Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof InputStream)
				statement.setBinaryStream(i + 1, (InputStream) values[i]);
			else
				statement.setObject(i + 1, values[i]);
		}
	}

	/**
	 * Converts the given java.util.Date to java.sql.Date.
	 */
	public static Date toSqlDate(java.util.Date date) {
		return (date != null) ? new Date(date.getTime()) : null;
	}
}

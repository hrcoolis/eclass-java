package dao;

/**
 * This class represents a generic DAO exception. It should wrap any exception
 * of the underlying code, such as SQLExceptions.
 */

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}

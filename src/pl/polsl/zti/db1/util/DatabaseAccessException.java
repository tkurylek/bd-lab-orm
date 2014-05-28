package pl.polsl.zti.db1.util;

public class DatabaseAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseAccessException(Exception e) {
		super(e);
	}
	
	public DatabaseAccessException(String message, Exception e) {
		super(message, e);
	}
}
package co.darma.exceptions;

public class InvalidAccessTokenException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAccessTokenException(String msg) {
		super(msg);
	}
}

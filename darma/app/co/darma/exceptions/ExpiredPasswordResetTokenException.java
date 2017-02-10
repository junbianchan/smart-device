package co.darma.exceptions;

public class ExpiredPasswordResetTokenException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExpiredPasswordResetTokenException(String msg) {
		super(msg);
	}
}

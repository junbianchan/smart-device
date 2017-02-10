package co.darma.exceptions;

public class InvalidCredentialException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialException(String email) {
		super("Provided credetials are invalid: " + email);
	}
}

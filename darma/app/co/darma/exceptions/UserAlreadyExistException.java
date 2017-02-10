package co.darma.exceptions;

public class UserAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String email) {
		super("User with email '" + email + "' already exists");
	}
}

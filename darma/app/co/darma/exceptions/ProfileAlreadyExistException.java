package co.darma.exceptions;

public class ProfileAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProfileAlreadyExistException(Long memberId) {
		super("Member '" + memberId + "' already has a created profile");
	}
}

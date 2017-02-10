package co.darma.exceptions;

public class InvalidDevicePasscodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidDevicePasscodeException(String email) {
		super("Provided credetials are invalid: " + email);
	}
}

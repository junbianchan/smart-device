package co.darma.exceptions;

public class InvalidThirdPartyAuthTokenException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidThirdPartyAuthTokenException(String msg) {
		super(msg);
	}
}

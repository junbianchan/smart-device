package co.darma.exceptions;

public class InternalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalException(String msg) {
		super(msg);
	}
	
	public InternalException(String msg, Exception e) {
		super(msg, e);
	}
}

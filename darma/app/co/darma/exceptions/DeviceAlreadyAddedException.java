package co.darma.exceptions;

public class DeviceAlreadyAddedException extends Exception {

	private static final long serialVersionUID = 1L;

	public DeviceAlreadyAddedException(String id) {
		super("Device with id " + id + " has already been added.");
	}
}

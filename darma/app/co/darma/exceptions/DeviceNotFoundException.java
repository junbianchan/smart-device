package co.darma.exceptions;

public class DeviceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public DeviceNotFoundException(String id) {
		super("Cannot find device with id " + id);
	}
}

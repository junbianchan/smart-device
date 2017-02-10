package co.darma.models.data;

import co.darma.exceptions.InvalidParameterException;

public enum SittingChange {

	KEEP_LEFT_WITH_MOVEMENT(1), 
	KEEP_RIGHT_WITH_MOVEMENT(2), 
	CHANGE_TO_RIGHT(3), 
	CHANGE_TO_LEFT(4), 
	CHANGE_TO_FORWARD(5), 
	CHANGE_TO_NORMAL(6);

	private int val;

	private SittingChange(int val) {
		this.val = val;
	}

	public int val() {
		return val;
	}

	public static SittingChange fromDB(int change)
			throws InvalidParameterException {
		for (SittingChange p : SittingChange.values()) {
			if (p.val() == change) {
				return p;
			}
		}
		throw new InvalidParameterException("Cannot find SittingChange - "
				+ change);
	}

	public static SittingChange fromString(String str)
			throws InvalidParameterException {
		if ("KEEP_LEFT_WITH_MOVEMENT".equalsIgnoreCase(str)) {
			return KEEP_LEFT_WITH_MOVEMENT;
		} else if ("KEEP_RIGHT_WITH_MOVEMENT".equalsIgnoreCase(str)) {
			return KEEP_RIGHT_WITH_MOVEMENT;
		} else if ("CHANGE_TO_RIGHT".equalsIgnoreCase(str)) {
			return CHANGE_TO_RIGHT;
		} else if ("CHANGE_TO_LEFT".equalsIgnoreCase(str)) {
			return CHANGE_TO_LEFT;
		} else if ("CHANGE_TO_FORWARD".equalsIgnoreCase(str)) {
			return CHANGE_TO_FORWARD;
		} else if ("CHANGE_TO_NORMAL".equalsIgnoreCase(str)) {
			return CHANGE_TO_NORMAL;
		} else {
			throw new InvalidParameterException("Cannot find SittingChange - "
					+ str);
		}
	}

}

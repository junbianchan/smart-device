package co.darma.models.data;

import co.darma.exceptions.InvalidParameterException;

public enum ThirdParty {

	FACEBOOK("F"), QQ("Q");

	private String party;

	private ThirdParty(String party) {
		this.party = party;
	}

	public String value() {
		return party;
	}

	public static ThirdParty fromDB(String str)
			throws InvalidParameterException {
		for (ThirdParty p : ThirdParty.values()) {
			if (p.value().equals(str)) {
				return p;
			}
		}
		throw new InvalidParameterException(
				"Cannot find third party network - " + str);
	}

	public static ThirdParty fromString(String str)
			throws InvalidParameterException {
		if ("facebook".equalsIgnoreCase(str)) {
			return FACEBOOK;
		} else {
			throw new InvalidParameterException(
					"Cannot find third party network - " + str);
		}
	}

}

package co.darma.forms;

import play.data.validation.Constraints.Required;

public class ThirdPartySignInForm {
	@Required
	public String thirdPartyId;
	@Required
	public String thirdParty;
	@Required
	public String authToken;

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public String getThirdParty() {
		return thirdParty;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}

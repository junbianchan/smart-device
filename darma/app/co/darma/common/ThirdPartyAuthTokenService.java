package co.darma.common;

import co.darma.models.data.ThirdParty;

public interface ThirdPartyAuthTokenService {

	public boolean isTokenValid(String authToken, String id, ThirdParty thirdParty);

}

package co.darma.services;

import java.sql.SQLException;

import co.darma.exceptions.InvalidThirdPartyAuthTokenException;
import co.darma.models.data.ThirdParty;
import co.darma.models.returns.SignInResult;

public interface ThirdPartyMemberService {

    public SignInResult signIn(String thirdPartyId, ThirdParty thirdParty,
                               String authToken, String agent) throws InvalidThirdPartyAuthTokenException,
            SQLException, Exception;

}

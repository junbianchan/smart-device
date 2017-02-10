package co.darma.services.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.darma.common.WSRequestHandler;
import co.darma.exceptions.InternalException;
import co.darma.models.data.ThirdParty;
import co.darma.models.thirdpartyresp.FBUser;
import co.darma.services.ThirdPartyAuthTokenService;
import play.Logger;

public class ThirdPartyAuthTOkenServiceImpl implements
        ThirdPartyAuthTokenService {

    private static String FB_AUTH_TOKEN_VALIDATION_URL = "https://graph.facebook.com/me?fields=id&access_token=";

    public static ThirdPartyAuthTokenService createInstance() {
        return new ThirdPartyAuthTOkenServiceImpl();
    }

    @Override
    public boolean isTokenValid(String authToken, String id,
                                ThirdParty thirdParty) {
        if (thirdParty == ThirdParty.FACEBOOK) {
            return validateFacebookToken(authToken, id);
        }
        return false;
    }

    private boolean validateFacebookToken(String authToken, String id) {
        try {
//            Logger.info(" facebook token is :" + authToken + ",id :" + id);
            String url = FB_AUTH_TOKEN_VALIDATION_URL + authToken.trim();
            String respMsg = WSRequestHandler.sendGetRequest(url);
            ObjectMapper mapper = new ObjectMapper();
            FBUser user = mapper.readValue(respMsg, FBUser.class);
            return id.equals(user.getId());
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new InternalException(
                    "Invalid response returned from Facebook auth request.");
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new InternalException(
                    "Invalid response returned from Facebook auth request.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalException(
                    "Invalid response returned from Facebook auth request.");
        }
    }
}

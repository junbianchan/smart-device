package co.darma.smartmattress.mattress;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.service.DarmaAuthService;
import co.darma.smartmattress.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/11/16.
 */
public class DarmaAuthServiceTest extends BaseTestCase {

    public void testAuth() throws JsonProcessingException {
        DarmaAuthService service = getContext().getBean(DarmaAuthService.class);

        Map map = new HashMap<String, String>();
        map.put("grant_type", "password");
        map.put("user_name", "meddo");
        map.put("password", "medd0@222015");
        map.put("scope", "default");
        ObjectMapper mapper = new ObjectMapper();

//        System.out.println(service.auth(mapper.writeValueAsString(map)));
//        System.out.println(service.authByUserNameAndPass("password", "frank", "hello world.", "default"));
    }

    public void testRefreshToken() {
        TokenService service = getContext().getBean(TokenService.class);
//        System.out.println(service.refreshToken("refresh_token", "134f85c8f8fa44c08965ff581712b23c", "default"));
    }


}

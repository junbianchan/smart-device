package co.darma.smartmattress.auth;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;

/**
 * Created by frank on 15/11/27.
 */
public class AccessTokenManagerTest extends BaseTestCase {

    public void testAuth() throws Exception {

        AccessTokenManager manager = (AccessTokenManager) getContext().getBean(AccessTokenManager.class);

//        AccessContext context = manager.authByUserNameAndPass("michael", "123456");

        String token = "b6871280e57e42f9a7552a39b0da3978";

        System.out.println("Auth result :" + manager.authByToken(token));
//        System.out.println(" refresh:" + manager.refreshToken(token));
        System.out.println();
    }

    public void testLogin() throws Exception {

        AccessTokenManager manager = (AccessTokenManager) getContext().getBean(AccessTokenManager.class);
        AccessContext context = manager.authByUserNameAndPass("meddo", "medd0@2015");
        System.out.println(context);
    }
}

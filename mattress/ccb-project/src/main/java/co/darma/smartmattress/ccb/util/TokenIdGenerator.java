package co.darma.smartmattress.ccb.util;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by frank on 15/11/13.
 */
public class TokenIdGenerator {

    public static String tokenGenerate() throws NoSuchAlgorithmException {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

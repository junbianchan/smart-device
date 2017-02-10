package co.darma.smartmattress.util;

import co.darma.smartmattress.encrypt.AESEncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 用来把密码解密
 * Created by frank on 15/10/21.
 */
public class DarmaPropertiesPersister extends DefaultPropertiesPersister {

    public void load(Properties props, InputStream is) throws IOException {

        props.load(is);
        String password = props.getProperty("password");
        String srcPass = AESEncryptUtil.decrypt(password);
        if (!StringUtils.isEmpty(srcPass)) {
            props.setProperty("password", srcPass);
        }
        return;
    }
}

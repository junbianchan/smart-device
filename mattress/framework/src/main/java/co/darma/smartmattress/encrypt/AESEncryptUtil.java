package co.darma.smartmattress.encrypt;

import co.darma.smartmattress.util.ByteUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

/**
 * AES Coder<br/>
 * secret key length:	128bit, default:	128 bit<br/>
 * mode:	ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding:	Nopadding/PKCS5Padding/ISO10126Padding/
 *
 * @author Aub
 */
public class AESEncryptUtil {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String DEFAULT_KEY_PATH = "/opt/darma/server/conf/darma_conf.properties";

    private static final String KEY_WORD = "token";

    /**
     * 初始化密钥
     *
     * @return byte[] 密钥
     * @throws Exception
     */
    public static byte[] initSecretKey() {
        File keyfile = new File(DEFAULT_KEY_PATH);
        if (keyfile.exists()) {

            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(keyfile));

                String keyStr = properties.getProperty(KEY_WORD);

                if (!StringUtils.isEmpty(keyStr)) {
                    return ByteUtils.hexStringToByteArray(keyStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return renewKey();
    }


    private static byte[] renewKey() {
        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        kg.init(128);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return 密钥
     */
    private static Key toKey(byte[] key) {
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  二进制密钥
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }


    /**
     * 加密
     *
     * @param data            待加密数据
     * @param key             二进制密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param key             密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }


    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  二进制密钥
     * @return byte[]    解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[]    解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data            待解密数据
     * @param key             二进制密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]    解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    /**
     * 解密
     *
     * @param data            待解密数据
     * @param key             密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]    解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式

        long radomTime = System.currentTimeMillis();
        byte[] slatByte = ByteUtils.longTo4ByteArray(radomTime);
        SecureRandom salt = new SecureRandom(slatByte);
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }


//  /*  *//***
//     * 工具的用法
//     * command password
//     *
////     * @param args
//     */
//
/*
    public static void main(String[] args) {
        System.out.println("args = [" + args.length + "]");
        if (args.length < 1 || StringUtils.isEmpty(args[0])) {
            System.err.println("Usage:Command password");
            return;
        }
        String srcPassword = args[0];
        byte[] key = initSecretKey();
        byte[] passByte = srcPassword.getBytes();
        try {
            byte[] encryptedPass = encrypt(passByte, key);
            System.out.println("encrypted password = [" + hexToString(encryptedPass) + "]");
        } catch (Exception e) {
            System.err.println("error = [" + e.getMessage() + "]");
        }
    }
*/

    /*
    private static String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for (byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }*/
    public static String decrypt(String encryptedPass) {
        if (StringUtils.isEmpty(encryptedPass)) {
            return "";
        }
        try {
            return ByteUtils.hexToString(decrypt(ByteUtils.hexStringToByteArray(encryptedPass), initSecretKey()));
        } catch (Exception e) {
            return null;
        }
    }

}
package co.darma.smartmattress.encrypt;

import co.darma.smartmattress.util.ByteUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.IncompleteElementException;

import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * Created by frank on 15/11/13.
 */
public class SHA2EncryptUtil {


    public static final int SALT_BYTE_SIZE = 32;

    private static final String DEFAULT_ALGORITHM = "SHA-256";


    public static String encrypt(String srcPass, String salt) throws Exception {

        if (StringUtils.isEmpty(srcPass)) {
            throw new IncompleteElementException("Source password is empty.");
        }
        byte[] srcPassByte = srcPass.getBytes();
        byte[] saltByte = ByteUtils.hexStringToByteArray(salt);
        byte[] enryptedPass = encrypt(srcPassByte, saltByte);
        return ByteUtils.hexToString(enryptedPass);
    }


    /**
     * 加密
     *
     * @param data 待加密数据
     * @param salt 盐值
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] salt) throws Exception {
        return encrypt(data, salt, DEFAULT_ALGORITHM);
    }


    /**
     * 加密
     *
     * @param data            待加密数据
     * @param salt            二进制盐值
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] salt, String cipherAlgorithm) throws Exception {

        if (data == null || salt == null) {
            throw new IncompleteElementException("Source data or salt is Invalid.");
        }
        byte[] finalData = new byte[data.length + salt.length];
        System.arraycopy(data, 0, finalData, 0, data.length);
        System.arraycopy(salt, 0, finalData, data.length, salt.length);
        return encrypt(finalData, cipherAlgorithm);
    }


    /**
     * 加密
     *
     * @param data            待加密数据
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[]    加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String cipherAlgorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(cipherAlgorithm);
        //执行操作
        return md.digest(data);
    }

    /**
     * 生产盐值
     *
     * @return byte[]
     */
    public static byte[] generateNewSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }


    public static void main(String[] args) throws Exception {
        byte[] salt = SHA2EncryptUtil.generateNewSalt();
        String saltStr = ByteUtils.hexToString(salt);
        String password = "123456";
        Long startTime = System.currentTimeMillis();
        String decryptedPass = encrypt(password, saltStr);
        Long endTime = System.currentTimeMillis();

        System.out.println("password = [" + password + "]");
        System.out.println("salt = [" + saltStr + "]");
        System.out.println("decryptedPass = [" + decryptedPass + "]");
        System.out.println("dencrypted cost:" + (endTime - startTime));
    }

}

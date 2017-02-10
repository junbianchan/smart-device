package co.darma.smartmattress.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by frank on 15/10/12.
 */
public class ByteUtils {

    public static byte[] getSubByte(byte[] srcByte, int index, int length) {
        if (validateArg(srcByte, index, length)) return null;
        byte[] result = new byte[length];
        System.arraycopy(srcByte, index, result, 0, length);
        return result;
    }

    /**
     * 转16为byte转成int，由于16byte表示的无符号整数，该函数只能接收2为的byte，切记。
     * <p/>
     * 这里需要测试
     * //TODO
     *
     * @param array
     * @return
     */
    public static int translate2ByteArrayToInt(byte[] array) {
        if (array == null || array.length > 2) {
            throw new IllegalArgumentException("Byte Array length should less than 2 but not null. now it's length is :"            //<br>
                    + (array == null ? 0 : array.length));
        }

        if (array.length == 1) {
            array = new byte[]{0x00, array[0]};
        }

        int result = (((array[0]) & 0xFF) << 8) + (((array[1]) & 0xFF));

        return result;

    }


    public static long translate4ByteArrayToLong(byte[] array) {
        if (array == null || array.length != 4) {
            throw new IllegalArgumentException("Byte Array length should be 4. now it's length is :"
                    + (array == null ? 0 : array.length));
        }
        long result = 0l;

        Byte tmpByte;

        for (int i = 0; i < 4; ++i) {
            result = ((array[i] & 0xFF)) + (result << 8);
        }
        return result;

    }

//    public static void main(String[] args) {
//        System.out.println(translate4ByteArrayToLong(new Byte[]{0x01, 0x01, 0x01, 0x01}));
//    }

//    public static void main(String[] args) {
//        System.out.print(ByteUtils.translate2ByteArrayToInt(new Byte[]{0x1, 0x2}));
//    }

    private static boolean validateArg(byte[] srcByte, int index, int length) {
        if (srcByte == null || srcByte.length == 0 || length < 0 || length > srcByte.length) {
            return true;
        }

        if (index >= srcByte.length || index < 0 || (index + length) > srcByte.length) {
            return true;
        }
        return false;
    }

    public static String hexToString(byte[] content) {

        StringBuilder sb = new StringBuilder();
        if (content != null && content.length != 0) {

            for (byte b : content) {
                sb.append(Integer.toHexString((b >> 4) & 0xF))
                        .append(Integer.toHexString(b & 0xF));
            }
        }
        return sb.toString();
    }

    public static byte[] longTo4ByteArray(Long time) {
        if (time != null) {
            byte[] result = new byte[4];
            result[0] = (byte) ((time >> 24) & 0xFF);
            result[1] = (byte) ((time >> 16) & 0xFF);
            result[2] = (byte) ((time >> 8) & 0xFF);
            result[3] = (byte) (time & 0xFF);
            return result;
        }
        return null;
    }

    public static byte[] int2byteArray(int input) {
        byte[] result = new byte[4];
        result[0] = (byte) ((input >> 24) & 0xFF);
        result[1] = (byte) ((input >> 16) & 0xFF);
        result[2] = (byte) ((input >> 8) & 0xFF);
        result[3] = (byte) (input & 0xFF);
        return result;
    }

    public static byte[] hexStringToByteArray(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        if (s.startsWith("0x") || s.startsWith("0X")) {
            s = s.substring(2);
        }
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


    public static void main(String[] args) {
//        System.out.println("args = [" + ByteUtils.hexToString(ByteUtils.longTo4ByteArray(123456789L)) + "]");
        System.out.println("args = [" + ByteUtils.hexToString(ByteUtils.hexStringToByteArray("0x0a")) + "]");
    }

}

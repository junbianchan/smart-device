package co.darma.smartmattress.constant;

/**
 * Created by frank on 15/10/19.
 */
public class ErrorCodeConstant {

    public static final int ERROR_CODE_MAGIC = 10;

    /**
     * wifi客户端数据包格式错误
     */
    public static final int ERROR_MATTRESS_CLIENT = 4100;

    /**
     * 校验码计算不一致，包可能错误，不计算
     */
    public static final int ERROR_MATTRESS_CLIENT_CRC_ERROR = 4101;
}

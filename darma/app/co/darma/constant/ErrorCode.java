package co.darma.constant;

/**
 * Created by frank on 15/11/18.
 */
public class ErrorCode {

    /**
     * 参数错误
     */
    public static final int INVALID_ARGUMENT = 400000;

    /**
     * 邮箱格式不正确
     * "^\\s*?(.+)@(.+?)\\s*$"
     */
    public static final int EMAIL_FORMAT_ERROR = 400001;

    /**
     * 密码在长度大于等于8的情况下，不能全是数字、或者全是小写字母或者全是大写字母
     */
    public static final int PASSWORD_NOT_RIGHT = 400002;

    /**
     * 密码长度不能小于8
     */
    public static final int PASSWORD_TOO_SHORT = 400003;

    /**
     * 该字段是必填的
     */
    public static final int PARAMTER_REQUIRED = 400004;


    /**
     * 体重参数不正确
     */
    public static final int WEIGHT_REQUIRED = 400005;

    /**
     * 身高参数不正确
     */
    public static final int HEIGHT_REQUIRED = 400006;

    /**
     * 字段不能为空
     */
    public static final int FIELD_LENGTH_NOT_EMPTY = 400007;


    /**
     * 参数长度过长
     */
    public static final int FIELD_LENGH_TOO_LONG = 400008;

    /**
     * 用户已经存在
     */
    public static final int USER_EXSIT = 400009;

    /**
     * 邮箱未注册
     */
    public static final int EMAIL_NOT_EXSIT = 4000010;

    /**
     * 重置token过期
     */
    public static final int EXPIRED_PASSWORD_RESET_TOKEN = 4000011;

    /**
     * 登录失败，用户名或者密码错误
     */
    public static final int AUTH_FAILED = 410000;

    /**
     * token不正确或者过期
     */
    public static final int TOKEN_INVALID = 410001;

    /**
     * token超期
     */
    public static final int TOKEN_EXPIRED = 410002;

    /**
     * 拒绝登录，用户不允许再登录，用户过期，用户被加入黑名单等情况
     */
    public static final int DENIED = 420000;

    /**
     * 协议不支持
     */
    public static final int PROTOCOL_NOT_SUPPORT = 430000;

    /**
     * 资源未发现
     */
    public static final int RESOURCE_NOT_FOUND = 440000;

    /**
     * Profile未找到
     */
    public static final int PROFILE_NOT_FOUND = 440001;


    /**
     * 用户资源过期
     */
    public static final int RESOURCE_EXPIRE = 450000;

    /**
     * 服务器错误
     */
    public static final int SERVER_ERROR = 500000;


    /**
     * 第三方系统异常
     */
    public static final int THIRD_PARTY_SYSTEM_ERROR = 600000;

    /**
     * 第三方系统token错误或者过期
     */
    public static final int INVALID_THIRD_PARTY_AUTH_TOKEN = 600001;

}

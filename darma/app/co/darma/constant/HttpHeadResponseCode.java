package co.darma.constant;

/**
 * Created by frank on 15/12/9.
 */
public class HttpHeadResponseCode {

    public static final int SUCESS = 200;//	GET,PUT	资源	操作成功

    public static final int CREATED = 201;//	POST	资源,元数据	对象创建成功

    public static final int ACCEPTED = 202;//	POST,PUT,DELETE,PATCH	N/A	请求已经被接受

    public static final int NORESPONSE = 204;//	DELETE,PUT,PATCH	N/A	操作已经执行成功，但是没有返回数据

    public static final int REMOVED = 301;//	GET	link	资源已被移除

    public static final int REDIRECTTO = 303;//	GET	link	重定向

    public static final int NOMODIFY = 304;//	GET	N/A	资源没有被修改

    public static final int ARGUMENTERROR = 400;//	GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	参数列表错误(缺少，格式不匹配)

    public static final int UNAUTHRIZED = 401;//	GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	未授权

    public static final int AUTHRIZEDLIMITED = 403;//	GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	访问受限，授权过期

    public static final int NOTFOUND = 404;    //GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	资源，服务未找到

    public static final int HTTPNOTALLOWED = 405;//	GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	不允许的http方法

    public static final int RESOURCELOCKED = 409;//	GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	资源冲突，或者资源被锁定

    public static final int RESOUCENOTALLOWD = 415;//GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	不支持的数据(媒体)类型

    public static final int REQUESTTOMUCH = 429;//GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	请求过多被限制

    public static final int SYSTEMERROR = 500;//GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	系统内部错误

    public static final int INTERFACENOTFOUND = 501;//GET,PSOT,PUT,DELETE,PATCH	错误提示(消息)	接口未实现

}

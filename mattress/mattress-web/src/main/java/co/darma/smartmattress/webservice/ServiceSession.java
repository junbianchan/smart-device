package co.darma.smartmattress.webservice;

/**
 * Created by frank on 15/11/18.
 */
public class ServiceSession {

    /**
     * 授权token
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 过期时间
     * 设置小于0的时候，表示不会过期
     */
    private Long expireIn;

    private String password;

    private Long tokenUpdateTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTokenUpdateTime() {
        return tokenUpdateTime;
    }

    public void setTokenUpdateTime(Long tokenUpdateTime) {
        this.tokenUpdateTime = tokenUpdateTime;
    }

    /**
     * 本地判断会话是否过期
     *
     * @return
     */
    public boolean isExpired() {

        if (expireIn < 0) {
            return false;
        }

        if ((tokenUpdateTime + expireIn) < System.currentTimeMillis()) {
            return false;
        }

        return true;
    }
}

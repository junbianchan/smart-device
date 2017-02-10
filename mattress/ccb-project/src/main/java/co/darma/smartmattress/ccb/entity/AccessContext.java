package co.darma.smartmattress.ccb.entity;

/**
 * 保存的回话
 * Created by frank on 15/11/13.
 */
public class AccessContext {

    private Integer id;

    private String userName;

    private String accessToken;

    private String tokenType;

    private Long createTime;

    /**
     * 超期的时间，单位为秒数
     */
    private Long expiresIn;

    /**
     * 当前的是否为有效的
     */
    private boolean isActive = true;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void unAcitve() {
        setIsActive(false);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[id:").append(this.id)
                .append(",accessToken:").append(this.accessToken)
                .append(",createTime:").append(this.createTime)
                .append(",expireIn:").append(this.expiresIn)
                .append(",isActive:").append(this.isActive)
                .append("]");

        return sb.toString();
    }
}

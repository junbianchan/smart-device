package co.darma.smartmattress.webservice;

/**
 * Created by frank on 15/11/18.
 */
public class ServerConfiguration {

    private String userName;

    private String password;

    private String baseUrl;

    private String connectType;

    private String version;

    private String authAddress;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getAuthAddress() {
        return authAddress;
    }

    public void setAuthAddress(String authAddress) {
        this.authAddress = authAddress;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[Url:").append(this.baseUrl)
                .append(",connectType:").append(this.connectType)
                .append(",version:").append(this.version).append("]");

        return sb.toString();

    }
}

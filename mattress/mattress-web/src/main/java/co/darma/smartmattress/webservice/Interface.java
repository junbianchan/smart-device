package co.darma.smartmattress.webservice;

import java.util.Map;

/**
 * Created by frank on 15/11/18.
 */
public class Interface {

    private String interfaceInfo;

    private String requestType ;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getInterfaceInfo() {
        return interfaceInfo;
    }

    public void setInterfaceInfo(String interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }

}

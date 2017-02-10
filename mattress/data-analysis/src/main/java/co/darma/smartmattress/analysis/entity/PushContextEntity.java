package co.darma.smartmattress.analysis.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/11/5.
 */
public class PushContextEntity {

    private String deviceNo;

    private String userName;

    private Long timeStamp;

    private String type;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[deviceNo:").append(this.deviceNo).append(",userName:").append(this.userName)
                .append(",timeStamp").append(this.timeStamp).append(",type:").append(this.type).append("]");
        return sb.toString();
    }

    public Map<String,Object> toMap()
    {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("deviceNo",deviceNo);
        returnMap.put("userName",userName);
        returnMap.put("timeStamp",timeStamp);
        returnMap.put("type",type);
        returnMap.put("PropertyCode","2");

        return returnMap;
    }
}

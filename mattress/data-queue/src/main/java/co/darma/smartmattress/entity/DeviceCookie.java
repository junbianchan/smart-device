package co.darma.smartmattress.entity;

/**
 * 用来存储设备在线状态等一些信息
 * Created by frank on 15/12/15.
 */
public class DeviceCookie {

    /**
     * 设备号
     */
    private String deviceNo;


    /**
     * 是否在床
     */
    private Boolean onBed;

    /**
     * 最近一个登录时间
     */
    private Long lastLoginTime;

    /**
     * 最近一次离床包号
     */
    private int lastOffBedPacketNo = -1;

    /**
     * 上次上床时间
     */
    private Long lastUpBedTime;

    public DeviceCookie(String deviceNo) {
        this.setDeviceNo(deviceNo);
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Boolean getOnBed() {
        return onBed;
    }

    public void setOnBed(Boolean onBed) {
        this.onBed = onBed;
    }


    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLastOffBedPacketNo() {
        return lastOffBedPacketNo;
    }

    public void setLastOffBedPacketNo(int lastOffBedPacketNo) {
        this.lastOffBedPacketNo = lastOffBedPacketNo;
    }

    public Long getLastUpBedTime() {
        return lastUpBedTime;
    }

    public void setLastUpBedTime(long lastUpBedTime) {
        this.lastUpBedTime = lastUpBedTime;
    }

    @Override
    public String toString() {
        return "DeviceCookie{" +
                "deviceNo='" + deviceNo + '\'' +
                ", onBed=" + onBed +
                ", lastLoginTime=" + lastLoginTime +
                ", lastOffBedPacketNo=" + lastOffBedPacketNo +
                ", lastUpBedTime=" + lastUpBedTime +
                '}';
    }
}

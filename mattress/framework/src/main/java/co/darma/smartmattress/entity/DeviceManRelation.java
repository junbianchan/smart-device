package co.darma.smartmattress.entity;

/**
 * 设备和用户绑定关系
 * Created by frank on 15/10/27.
 */
public class DeviceManRelation {

    private Integer id;

    private Integer deviceId;

    private Integer userId;

    private Long boundTime;

    private Long unboudTime;

    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getBoundTime() {
        return boundTime;
    }

    public void setBoundTime(Long boundTime) {
        this.boundTime = boundTime;
    }

    public Long getUnboudTime() {
        return unboudTime;
    }

    public void setUnboudTime(Long unboudTime) {
        this.unboudTime = unboudTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[id:").append(this.id)
                .append(",deviceId:").append(this.deviceId)
                .append(",userId:").append(this.userId)
                .append(",boundTime:").append(this.boundTime)
                .append(",unboudTime:").append(this.unboudTime)
                .append(",isActive:").append(this.isActive);
        return sb.toString();
    }
}

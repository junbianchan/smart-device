package co.darma.smartmattress.analysis.entity;

/**
 * 用户行为表
 * 上床，下床等数据
 * Created by frank on 15/10/12.
 */
public class ManBehavior {

    /*
    *  id
    * */
    private Integer id;

    /**
     * 设备ID
     */
    private Integer deviceId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 标记时间
     */
    private Long markTime;


    /**
     * 类型
     */
    private ManBehaviorType type;


    private String algorithmVersion;


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

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ManBehaviorType getType() {
        return type;
    }

    public void setType(ManBehaviorType type) {
        this.type = type;
    }

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    @Override
    public String toString() {
        return "ManBehavior{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", userId=" + userId +
                ", markTime=" + markTime +
                ", type=" + type +
                ", algorithmVersion='" + algorithmVersion + '\'' +
                '}';
    }
}

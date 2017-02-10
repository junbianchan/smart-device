package co.darma.smartmattress.analysis.entity;

/**
 * Created by frank on 15/12/4.
 */
public class HealthData {

    private Integer id;

    /**
     * 设备ID
     */
    private co.darma.smartmattress.entity.Device device;

    /**
     * 发生时间
     */
    private Long markTime;

    /**
     * 呼吸值
     */
    private Integer breathValue;


    /**
     * 心率
     */
    private Integer heartRate;


    /**
     * 所使用的算法版本
     */
    private String algorithmVersion;


    /**
     * 用户ID
     */
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public co.darma.smartmattress.entity.Device getDevice() {
        return device;
    }

    public void setDevice(co.darma.smartmattress.entity.Device device) {
        this.device = device;
    }

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
    }

    public Integer getBreathValue() {
        return breathValue;
    }

    public void setBreathValue(Integer breathValue) {
        this.breathValue = breathValue;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

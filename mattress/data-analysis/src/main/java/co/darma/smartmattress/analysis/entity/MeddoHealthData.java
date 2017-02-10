package co.darma.smartmattress.analysis.entity;

/**
 * Created by frank on 15/11/25.
 */
public class MeddoHealthData {

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
     * 呼吸权重
     */
    private Integer breathValueWeight = 0;

    /**
     * 心率权重
     */
    private Integer heartRateWeight = 0;

    /**
     * 所使用的算法版本
     */
    private String algorithmVersion;

    private Long lastUpdateTime;

    private Integer reallyData = 0;

    public MeddoHealthData() {
    }


    public MeddoHealthData(HealthData healthData, co.darma.smartmattress.entity.Device device) {
        this.setDevice(device);
        this.setAlgorithmVersion(healthData.getAlgorithmVersion());
    }

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

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getBreathValueWeight() {
        return breathValueWeight;
    }

    public void setBreathValueWeight(Integer breathValueWeight) {
        this.breathValueWeight = breathValueWeight;
    }

    public Integer getHeartRateWeight() {
        return heartRateWeight;
    }

    public void setHeartRateWeight(Integer heartRateWeight) {
        this.heartRateWeight = heartRateWeight;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("[breathValue:").append(breathValue);
        sb.append(",breathValueWeight:").append(breathValueWeight);
        sb.append(",device:").append(device);
        sb.append(",heartRate:").append(heartRate);
        sb.append(",heartRateWeight:").append(heartRateWeight);
        sb.append(",algorithmVersion:").append(algorithmVersion);
        sb.append(",markTime:").append(markTime);
        sb.append("]");
        return sb.toString();

    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getReallyData() {
        return reallyData;
    }

    public void setReallyData(Integer reallyData) {
        this.reallyData = reallyData;
    }
}

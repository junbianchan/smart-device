package co.darma.smartmattress.analysis.entity;

import co.darma.smartmattress.entity.Device;

/**
 * Created by frank on 16/1/5.
 */
public class BodyMotionForSleep {

    private Integer deviceId;

    private Long startTime;

    private Long endTime;

    /**
     * 改段时间的心率数据
     */
    private String metaData;

    private int dataNumber;

    private String algorithmVersion;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }


    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public int getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }


    @Override
    public String toString() {
        return "BodyMotionForSleep{" +
                "deviceId=" + deviceId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", metaData='" + metaData + '\'' +
                ", dataNumber=" + dataNumber +
                ", algorithmVersion='" + algorithmVersion + '\'' +
                '}';
    }
}

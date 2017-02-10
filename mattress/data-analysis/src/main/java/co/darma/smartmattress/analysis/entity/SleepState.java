package co.darma.smartmattress.analysis.entity;

import co.darma.smartmattress.entity.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 睡眠状态
 * Created by frank on 16/1/4.
 */
public class SleepState {

    @JsonIgnore
    private Long id;

    /**
     * 设备ID
     */
    @JsonIgnore
    private Device device;

    /**
     * 数据发生时间
     */
    private Long startTime;

    /**
     * 数据结束时间
     */
    private Long endTime;

    /**
     * 睡眠状态，1醒着，2浅度睡眠，3深度睡眠
     */
    private Integer sleepState;

    /**
     * 数据更新时间
     */
    @JsonIgnore
    private Long updateTime;

    @JsonIgnore
    private String algorithmVersion;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @JsonProperty("start_time")
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("state")
    public Integer getSleepState() {
        return sleepState;
    }

    public void setSleepState(Integer sleepState) {
        this.sleepState = sleepState;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @JsonProperty("end_time")
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    @Override
    public String toString() {
        return "SleepState{" +
                "id=" + id +
                ", device=" + device +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", sleepState=" + sleepState +
                ", updateTime=" + updateTime +
                ", algorithmVersion='" + algorithmVersion + '\'' +
                '}';
    }
}

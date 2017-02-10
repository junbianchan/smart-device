package co.darma.smartmattress.analysis.entity;

import co.darma.smartmattress.entity.Device;

/**
 * 用于保存光纤原始数据
 * Created by frank on 16/1/27.
 */
public class OpticalData {

    private Device device;

    private Long startTime;

    private Long endTime;

    private Long updateTime;

    private String opticalData;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getOpticalData() {
        return opticalData;
    }

    public void setOpticalData(String opticalData) {
        this.opticalData = opticalData;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "OpticalData{" +
                "device=" + device +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", updateTime=" + updateTime +
                ", opticalData='" + opticalData + '\'' +
                '}';
    }
}

package co.darma.smartmattress.upgrade.entity;

import co.darma.smartmattress.entity.Device;

/**
 * 升级的请求
 * Created by frank on 15/12/17.
 */
public class UpgradeRequest {

    private Long id;

    private Device device;

    private Firmware sourceFirmware;

    private Firmware targetFirmware;

    private Long planUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Firmware getSourceFirmware() {
        return sourceFirmware;
    }

    public void setSourceFirmware(Firmware sourceFirmware) {
        this.sourceFirmware = sourceFirmware;
    }

    public Firmware getTargetFirmware() {
        return targetFirmware;
    }

    public void setTargetFirmware(Firmware targetFirmware) {
        this.targetFirmware = targetFirmware;
    }

    public Long getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(Long planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[id:").append(id);
        sb.append(",device:").append(device);
        sb.append(",sourceFirmware:").append(sourceFirmware);
        sb.append(",targetFirmware:").append(targetFirmware);
        sb.append(",planUpdateTime:").append(planUpdateTime).append("]");
        return sb.toString();
    }

}

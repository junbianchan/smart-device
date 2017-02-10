package co.darma.smartmattress.upgrade.entity;

import co.darma.smartmattress.entity.Device;

/**
 * Created by frank on 15/12/24.
 */
public class DeviceWithFirmware extends Device {

    private Firmware firmware;

    public DeviceWithFirmware() {
    }

    public DeviceWithFirmware(Device device) {
        this.setId(device.getId());
        this.setDeviceName(device.getDeviceName());
        this.setDeviceNo(device.getDeviceNo());
        this.setProject(device.getProject());
        this.setDeviceMacAddress(device.getDeviceMacAddress());
    }

    public Firmware getFirmware() {
        return firmware;
    }

    public void setFirmware(Firmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append(",firmware :").append(firmware);
        return sb.toString();
    }
}

package co.darma.smartmattress.upgrade.dao;

import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;

/**
 * Created by frank on 15/12/24.
 */
public interface DeviceWithFirmwareDao {

    public DeviceWithFirmware queryDeviceByDeviceNo(String deviceNo);

    public boolean updateDeviceFirmware(DeviceWithFirmware deviceWithFirmware);
}

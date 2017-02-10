package co.darma.smartmattress.dao;

import co.darma.smartmattress.entity.Device;

/**
 * Created by frank on 15/10/26.
 */
public interface DeviceDao {

    /**
     * 通过设备编码获取到设备的信息
     *
     * @param deviceNo
     * @return
     */
    public Device queryDeviceByDeviceNo(String deviceNo);

    public boolean insertDevice(Device device);

}

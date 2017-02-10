package co.darma.smartmattress.upgrade.dao.impl;

import co.darma.smartmattress.upgrade.dao.BaseDao;
import co.darma.smartmattress.upgrade.dao.DeviceWithFirmwareDao;
import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by frank on 15/12/29.
 */
public class DeviceWithFirmwareDaoImpl extends BaseDao implements DeviceWithFirmwareDao {

    @Override
    public DeviceWithFirmware queryDeviceByDeviceNo(String deviceNo) {

        List<DeviceWithFirmware> deviceWithFirmwares =
                getDataAcessor().queryList(IBATIS_PROFIX + "queryDeviceWithFirmware", deviceNo);

        if (CollectionUtils.isNotEmpty(deviceWithFirmwares)) {
            return deviceWithFirmwares.get(0);
        }
        return null;
    }

    @Override
    public boolean updateDeviceFirmware(DeviceWithFirmware deviceWithFirmware) {
        return getDataAcessor().saveOrUpdateObject("updateDeviceFirmware", deviceWithFirmware);
    }
}

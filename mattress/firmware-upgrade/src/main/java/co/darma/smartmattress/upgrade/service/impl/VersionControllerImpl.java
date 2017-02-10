package co.darma.smartmattress.upgrade.service.impl;

import co.darma.smartmattress.upgrade.dao.DeviceWithFirmwareDao;
import co.darma.smartmattress.upgrade.dao.FirmwareDao;
import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;
import co.darma.smartmattress.upgrade.entity.Firmware;
import co.darma.smartmattress.upgrade.service.VersionController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by frank on 15/12/24.
 */
public class VersionControllerImpl implements VersionController {

    private DeviceWithFirmwareDao deviceWithFirmwareDao;

    private FirmwareDao firmwareDao;

    private static Logger logger = Logger.getLogger(VersionControllerImpl.class);


    @Override
    public Firmware checkDeviceNeedUpdateOrNot(String deviceNo, Double clientVerion) {

        if (StringUtils.isEmpty(deviceNo) || clientVerion == null) {
            return null;
        }

        DeviceWithFirmware deviceWithFirmware = getDeviceFirmware(deviceNo);
        Firmware deviceFirmware = firmwareDao.queryFirmwarebyProjectAndVersion(deviceWithFirmware.getProject().getProjectId(), clientVerion);
        Firmware lastFirmware = getLastFirmwareware(deviceWithFirmware.getProject().getProjectId());

        if (lastFirmware != null) {
            if (deviceFirmware != null) {
                if (lastFirmware.compareTo(deviceFirmware) > 0) {
                    return lastFirmware;
                }
            } else if (lastFirmware.getVersionNo() > clientVerion) {
                return lastFirmware;
            }
        }
        return null;
    }

    @Override
    public DeviceWithFirmware getDeviceFirmware(String deviceNo) {
        DeviceWithFirmware deviceWithFirmware = deviceWithFirmwareDao.queryDeviceByDeviceNo(deviceNo);
        return deviceWithFirmware;
    }

    @Override
    public Firmware getLastFirmwareware(Integer projectId) {
        List<Firmware> firmwareList = firmwareDao.queryLastestFirmwareByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(firmwareList)) {
            return firmwareList.get(0);
        } else {
            return null;
        }

    }


    public DeviceWithFirmwareDao getDeviceWithFirmwareDao() {
        return deviceWithFirmwareDao;
    }

    public void setDeviceWithFirmwareDao(DeviceWithFirmwareDao deviceWithFirmwareDao) {
        this.deviceWithFirmwareDao = deviceWithFirmwareDao;
    }

    public void setFirmwareDao(FirmwareDao firmwareDao) {
        this.firmwareDao = firmwareDao;
    }
}

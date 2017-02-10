package co.darma.smartmattress.upgrade.service;

import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;
import co.darma.smartmattress.upgrade.entity.Firmware;

/**
 * Created by frank on 15/12/24.
 */
public interface VersionController {

    /**
     * 检查是否需要升级
     *
     * @param deviceNo
     * @return 固件对象，如果非空，那么就表示需要升级，而且返回升级的固件
     */
    public Firmware checkDeviceNeedUpdateOrNot(String deviceNo,Double clientVerion);

    /**
     * 查询设备的固件
     *
     * @param deviceNo
     * @return
     */
    public DeviceWithFirmware getDeviceFirmware(String deviceNo);

    /**
     * 获取改项目对应的最新固件
     *
     * @param projectId
     * @return
     */
    public Firmware getLastFirmwareware(Integer projectId);


}

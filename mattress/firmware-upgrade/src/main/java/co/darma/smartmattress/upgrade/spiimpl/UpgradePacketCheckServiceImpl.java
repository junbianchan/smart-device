package co.darma.smartmattress.upgrade.spiimpl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.service.Service;
import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;
import co.darma.smartmattress.upgrade.entity.Firmware;
import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import co.darma.smartmattress.upgrade.service.UpgradeRequestHandleService;
import co.darma.smartmattress.upgrade.service.UpgradeRequstService;
import co.darma.smartmattress.upgrade.service.VersionController;
import co.darma.smartmattress.upgrade.util.TaskExcutor;
import co.darma.smartmattress.util.ByteUtils;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

/**
 * Created by frank on 15/12/17.
 */
public class UpgradePacketCheckServiceImpl extends Service {

    private Logger logger = Logger.getLogger(UpgradePacketCheckServiceImpl.class);

    private VersionController versionController;

    private UpgradeRequstService upgradeRequstService;

    @Override
    public void execute() {
        try {

            Element element = QueueUtil.takePacketFromUpgradeQueue();
            logger.info("Start UpgradePacket CheckServiceImpl :" + element);
            if (element instanceof MattressPacket) {
                MattressPacket packet = (MattressPacket) element;

                String deviceNo = packet.getDeviceId();
                Double version = getVersion(packet.getData());
                if (version == null) {
                    logger.error("Unknown verion for package " + packet);
                    return;
                }

                // 判断是否需要升级
                Firmware firmware = versionController.checkDeviceNeedUpdateOrNot(deviceNo, version);
                // 需要升级
                if (firmware != null) {
                    // 创建升级任务
                    DeviceWithFirmware deviceWithFirmware = versionController.getDeviceFirmware(deviceNo);
                    if (!upgradeRequstService.deviceHashUpgradeRequestOrNot(
                            deviceWithFirmware.getId(),
                            firmware.getFirmwareId())) {
                        logger.info("Device " + deviceNo + " need upgrade to :" + firmware);

                        UpgradeRequest request = new UpgradeRequest();
                        request.setDevice(deviceWithFirmware);
                        request.setPlanUpdateTime(System.currentTimeMillis());
                        request.setTargetFirmware(firmware);
                        request.setSourceFirmware(deviceWithFirmware.getFirmware());
                        upgradeRequstService.addNewUpgradeRequest(request);

                        //先清理，后新增
                        TaskExcutor.cleanUnRunningChecker();
                        if (TaskExcutor.getTaskSize() < 1) {
                            // no running chen
                            TaskExcutor.addTask();
                        }
                    } else {
                        logger.error("The device :" + deviceWithFirmware + " has a upgrade Request already.");
                    }
                }
                // 结束
                logger.info("End UpgradePacket CheckServiceImpl");
            }
        } catch (SystemException e) {
            logger.error(LogUtil.logException(e));
        } catch (Exception e) {
            logger.error(LogUtil.traceException(e));
        }
    }

    private Double getVersion(byte[] data) {

        if (data.length >= 2) {

            Integer prefix = (int) (data[0] & 0xFF);
            Integer postfix = (int) (data[1] & 0xF);

            Double finalVersion = (prefix * 1.0) + (postfix / 10.0);

            return finalVersion;
        }
        return null;
    }


    public void setVersionController(VersionController versionController) {
        this.versionController = versionController;
    }

    public void setUpgradeRequstService(UpgradeRequstService upgradeRequstService) {
        this.upgradeRequstService = upgradeRequstService;
    }
}

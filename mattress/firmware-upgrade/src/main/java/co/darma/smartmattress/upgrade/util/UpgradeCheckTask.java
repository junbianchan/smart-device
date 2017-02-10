package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.upgrade.dao.DeviceWithFirmwareDao;
import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;
import co.darma.smartmattress.upgrade.entity.UpgradeJob;
import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import co.darma.smartmattress.upgrade.service.UpgradeRequstService;
import co.darma.smartmattress.util.LogUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by frank on 15/12/29.
 */
public class UpgradeCheckTask extends TimerTask {

    private UpgradeRequstService upgradeRequstService;

    private UpgradeEngine engine;

    private static Logger logger = Logger.getLogger(UpgradeCheckTask.class);

    private boolean fixJob = false;

    private volatile static boolean isRunning = false;

    private DeviceWithFirmwareDao deviceWithFirmwareDao;

    @Override
    public void run() {
        if (!isRunning) {
            logger.info("Start add a new request");
            isRunning = true;
            try {
                UpgradeRequest request = upgradeRequstService.getUpgradeRequest(null);
                while (request != null) {
                    try {
                        UpgradeJob job = new UpgradeJob(request);
                        String result;
                        if (StringUtils.isEmpty(result = engine.doUpdate(job))) {
                            upgradeRequstService.saveUpgradeRequestRecord(request, true, "success");
                            DeviceWithFirmware device = new DeviceWithFirmware(request.getDevice());
                            device.setFirmware(request.getTargetFirmware());
                            deviceWithFirmwareDao.updateDeviceFirmware(device);
                        } else {
                            upgradeRequstService.saveUpgradeRequestRecord(request, false, result);
                        }
                    } catch (IOException e) {
                        logger.error(LogUtil.logException(e));
                        upgradeRequstService.saveUpgradeRequestRecord(request, false, e.getMessage());
                    } catch (IllegalArgumentException e) {
                        logger.error(LogUtil.logException(e));
                        upgradeRequstService.saveUpgradeRequestRecord(request, false, e.getMessage());
                    } catch (Exception e) {
                        logger.error(LogUtil.logException(e));
                        upgradeRequstService.saveUpgradeRequestRecord(request, false, e.getMessage());
                    } finally {
                        try {
                            upgradeRequstService.deleteUpgradeRequst(request);
                        } catch (Exception e) {
                            logger.error(LogUtil.logException(e));
                        }
                    }
                    // next
                    request = upgradeRequstService.getUpgradeRequest(null);
                }
                logger.info("request is null");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(LogUtil.logException(e));
            } finally {
                isRunning = false;
            }
        } else {
            logger.error("Some Upgrade Task is running and canceled.");
        }
        this.cancel();
    }

    public void setUpgradeRequstService(UpgradeRequstService upgradeRequstService) {
        this.upgradeRequstService = upgradeRequstService;
    }

    public void setEngine(UpgradeEngine engine) {
        this.engine = engine;
    }

    public void setFixJob(boolean fixJob) {
        this.fixJob = fixJob;
    }

    @JsonProperty("isRunning")
    public boolean isRunning() {
        return isRunning;
    }

    @JsonProperty("isFixJob")
    public boolean isFixJob() {
        return fixJob;
    }

    public void setDeviceWithFirmwareDao(DeviceWithFirmwareDao deviceWithFirmwareDao) {
        this.deviceWithFirmwareDao = deviceWithFirmwareDao;
    }
}

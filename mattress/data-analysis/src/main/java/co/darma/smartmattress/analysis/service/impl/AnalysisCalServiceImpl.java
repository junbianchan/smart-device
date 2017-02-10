package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.dao.ManBehaviorDao;
import co.darma.smartmattress.analysis.dao.SleepStateDao;
import co.darma.smartmattress.analysis.entity.ManBehavior;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.analysis.service.AnalysisSleepService;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.constant.PacketConstant;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceCookie;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by frank on 16/1/6.
 */
public class AnalysisCalServiceImpl implements PacketAnalysisService {

    private ManBehaviorDao manBehaviorDao;

    private static final Logger logger = Logger.getLogger(AnalysisCalServiceImpl.class);

    private static Long TWO_DAY = 2 * 24 * 60 * 60L;

    private AnalysisSleepService analysisSleepService;

    private SleepStateDao sleepStateDao;

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {

        logger.info("AnalysisCalServiceImpl begin ");
        ServicePacket servicePacket = (ServicePacket) input.getArgByName(StateChangeHandleService.STAT_PACKET);
        int cmd = servicePacket.getCmd();

        Device device = (Device) input.getArgByName(HistoryPacketHandleService.DEVICE);
        if (device == null) {
            logger.error("Device is null for deviceNo " + servicePacket.getDeviceNo());
            return;
        }
        Integer deviceId = device.getId();
        //只关心离床和离线
        if (cmd == PacketConstant.AWAY_BED || cmd == PacketConstant.OFFLINE) {
            DeviceCookie cookie = QueueUtil.getDeviceCookie(servicePacket.getDeviceNo());
            logger.info("coolie is :" + cookie);
            Long startTime;
            if (cookie != null && cookie.getLastUpBedTime() != null) {
                startTime = cookie.getLastUpBedTime();
            } else {
                //已经离线过或者离床过
                Long limitTime = (System.currentTimeMillis() / 1000) - TWO_DAY;
                ManBehavior lastUpBed = manBehaviorDao.queryLastestManBehaviorByDevice(deviceId, limitTime);
                logger.info("behavior :" + lastUpBed);
                if (lastUpBed != null) {
                    startTime = lastUpBed.getMarkTime();
                } else {
                    startTime = (System.currentTimeMillis() / 1000) - TWO_DAY;
                }
            }

            Long endTime = servicePacket.getTimestamp();
            //计算出睡眠数据
            List<SleepState> sleepStateList = analysisSleepService.analysisSleepStates(device, startTime, endTime);
            logger.info("Analysis data: " + sleepStateList);
            sleepStateDao.saveOrUpdateSleepSatte(sleepStateList);
        }

    }


    public void setSleepStateDao(SleepStateDao sleepStateDao) {
        this.sleepStateDao = sleepStateDao;
    }

    public void setManBehaviorDao(ManBehaviorDao manBehaviorDao) {
        this.manBehaviorDao = manBehaviorDao;
    }

    public void setAnalysisSleepService(AnalysisSleepService analysisSleepService) {
        this.analysisSleepService = analysisSleepService;
    }
}

package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.analysis.dao.MotionForSleepDao;
import co.darma.smartmattress.analysis.entity.BodyMotionForSleep;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.healthAlgorithms.basic.DMAlgorithmMattress;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.analysis.util.ArrayHandle;
import co.darma.smartmattress.entity.Device;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 和DefaultHealthPacketAnalysisService 中计算体动的不同在于，这个是为了
 * 睡眠所需要而记录的体动。
 * Created by frank on 16/1/5.
 */
public class MotionRecordService implements PacketAnalysisService {

    private static Logger logger = Logger.getLogger(MotionRecordService.class);

    private DMAlgorithmMattress mattress = new DMAlgorithmMattress();

    private MotionForSleepDao motionForSleepDao;

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {
        Device device = (Device) input.getArgByName(HistoryPacketHandleService.DEVICE);
        
        List<ServicePacket> packetList = (List<ServicePacket>)
                input.getArgByName(DefaultHealthPacketAnalysisService.PACKET_LIST);

        if (device == null) {
            logger.error("Device Id is null ,the packet is invalid");
            return;
        }

        if (packetList == null) {
            logger.error("packetList Id is null ");
            return;
        }

        int[] opticalData = (int[]) output.getArgByName(DefaultHealthPacketAnalysisService.OPTICAL_DATA);

        if (opticalData != null) {

            int[] motion = mattress.offlineStatusCalulation(opticalData);

            if (motion != null) {
                String motionStr = ArrayHandle.intToString(0, motion);
                motionStr = motionStr.substring(0, motionStr.length() - 1);//去除最后一个逗号

                logger.info("Motion for Sleep :" + motionStr);
                Long startTime = packetList.get(0).getTimestamp();
                Long endTime = packetList.get(packetList.size() - 1).getTimestamp();

                BodyMotionForSleep motionForSleep = new BodyMotionForSleep();
                motionForSleep.setStartTime(startTime);
                motionForSleep.setEndTime(endTime);
                motionForSleep.setDeviceId(device.getId());
                motionForSleep.setDataNumber(motion.length);
                motionForSleep.setMetaData(motionStr);
                motionForSleep.setAlgorithmVersion(DMAlgorithmMattress.getVersion());

                motionForSleepDao.saveOrUpdateBodyMotionForSleep(motionForSleep);

            } else {
                logger.error("motion is null.");
            }
        } else {
            logger.error("optical is null");
        }

    }

    public void setMotionForSleepDao(MotionForSleepDao motionForSleepDao) {
        this.motionForSleepDao = motionForSleepDao;
    }

}

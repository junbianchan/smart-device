package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.analysis.dao.ManBehaviorDao;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.analysis.entity.ManBehavior;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.factory.ManBehaviorFacotry;
import co.darma.smartmattress.analysis.healthAlgorithms.basic.DMAlgorithmMattress;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/11/4.
 */

public class SatePacketRecordServiceImpl implements PacketAnalysisService {

    private static final Logger logger = Logger.getLogger(SatePacketRecordServiceImpl.class);

    public static final String MAN_BEHAVIOR = "manBehavior";

    private ManBehaviorDao manBehaviorDao;

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {

        ServicePacket servicePacket = (ServicePacket) input.getArgByName(StateChangeHandleService.STAT_PACKET);
        int cmd = servicePacket.getCmd();
        logger.info("handle packet ,type is " + cmd);

        ManBehavior manBehavior = ManBehaviorFacotry.newInstanceById(cmd);
        Integer userId = (Integer) output.getArgByName("userId");
        Integer deviceId;
        Device device = (Device) input.getArgByName(HistoryPacketHandleService.DEVICE);
        if (device == null) {
            logger.error("Device is null for deviceNo " + servicePacket.getDeviceNo());
            return;
        }
        deviceId = device.getId();

        manBehavior.setUserId(userId);
        manBehavior.setDeviceId(deviceId);
        manBehavior.setMarkTime(servicePacket.getTimestamp());
        manBehavior.setAlgorithmVersion(DMAlgorithmMattress.getVersion());
        manBehaviorDao.saveManBehavior(manBehavior);
        output.putArgument(MAN_BEHAVIOR, manBehavior);
    }

    public void setManBehaviorDao(ManBehaviorDao manBehaviorDao) {
        this.manBehaviorDao = manBehaviorDao;
    }
}

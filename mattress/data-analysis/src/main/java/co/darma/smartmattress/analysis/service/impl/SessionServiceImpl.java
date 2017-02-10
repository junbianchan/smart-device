package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.analysis.util.DuplicatePacketException;
import co.darma.smartmattress.entity.DeviceCookie;
import org.apache.log4j.Logger;

/**
 * 第一次上床的时候，标记在床上
 * Created by frank on 15/12/15.
 */
public class SessionServiceImpl implements PacketAnalysisService {

    private static Logger logger = Logger.getLogger(SessionServiceImpl.class);

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {

        ServicePacket servicePacket = (ServicePacket) input.getArgByName(StateChangeHandleService.STAT_PACKET);
        int cmd = servicePacket.getCmd();
        String deviceNo = servicePacket.getDeviceNo();
        DeviceCookie cookie = null;
        Long currentTime = System.currentTimeMillis();
        switch (cmd) {
            case 3:
                //离床
                cookie = QueueUtil.getDeviceCookie(deviceNo);
                if (cookie == null) {
                    cookie = new DeviceCookie(deviceNo);
                    cookie.setLastLoginTime(currentTime);
                } else {
                    logger.info(deviceNo + " handle away packet.");
                    if (!cookie.getOnBed()
                            && cookie.getLastOffBedPacketNo() == servicePacket.getPacketNo()) {
                        throw new DuplicatePacketException("Packet of device :" + deviceNo + "'s command :" + cmd + "is duplicated.");
                    }
                    //cookie.setOnBed(false);
                    //cookie.setLastOffBedPacketNo(servicePacket.getPacketNo());
                }
                cookie.setOnBed(false);
                cookie.setLastOffBedPacketNo(servicePacket.getPacketNo());
                QueueUtil.updateDeviceCookie(cookie);
                break;
            case 4:
                //上床
                cookie = QueueUtil.getDeviceCookie(deviceNo);
                if (cookie == null) {
                    cookie = new DeviceCookie(deviceNo);
                    cookie.setLastLoginTime(currentTime);
                    cookie.setOnBed(true);
                    cookie.setLastUpBedTime(servicePacket.getTimestamp());
                } else {
                    cookie.setOnBed(true);
                    cookie.setLastUpBedTime(servicePacket.getTimestamp());
                }
                QueueUtil.updateDeviceCookie(cookie);
                break;
            case 7:
                //故障
                break;
            case 8:
                logger.info(deviceNo + "handle offline packet .");
                QueueUtil.removeDeviceCookie(deviceNo);
                break;
            case -1:
                break;
            default:
                // do nothing.
                break;
        }


    }
}

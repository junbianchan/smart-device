package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.upgrade.entity.UpdateResult;
import co.darma.smartmattress.util.ByteUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于检查设备是否在线，获取升级包Ack包的探针，
 * 这个探针本身的设计是主要用于处理异步返回数据，然后通知对应的关系的发送者
 * Created by frank on 15/12/29.
 */
public class DeviceProbe implements Runnable {

    private static Map<String, PacketSender> observers = new ConcurrentHashMap<String, PacketSender>();

    private volatile Boolean runnable = false;

    private Logger logger = Logger.getLogger(DeviceProbe.class);

    public DeviceProbe() {
    }


    public void register(String deviceNo, PacketSender sender) {
        observers.put(deviceNo, sender);
    }

    public void unRegister(String deviceNo) {
        observers.remove(deviceNo);
        runnable = false;
    }

    public Boolean getRunnable() {
        return runnable;
    }

    @Override
    public void run() {
        logger.info("Device Probe begin watching...");
        runnable = true;
        while (runnable) {
            try {
                MattressPacket updatePacket = (MattressPacket) QueueUtil.takeUpgradeResult(3 * 60);

                if (logger.isDebugEnabled()) {
                    logger.debug("updatePacket is : " + updatePacket);
                }
                if (updatePacket != null) {
                    PacketSender sender = observers.get(updatePacket.getDeviceId());
                    if (sender != null) {
                        sender.call(toUpdateResult(updatePacket));
                    } else {
                        logger.error("updatePacket is :" + updatePacket + ", has no sender");

                    }
                } else {
                    logger.error("no sender for updatePacket :" + updatePacket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private UpdateResult toUpdateResult(MattressPacket updatePacket) {

        UpdateResult result = new UpdateResult();
        result.setHead(updatePacket.getMagicNumber());
        result.setCommand(updatePacket.getCommand());
        result.setDeviceNo(updatePacket.getDeviceId());
        result.setSeq(updatePacket.getSeqNum());

        logger.info("UpdateResult :" + result);

        return result;
    }


}

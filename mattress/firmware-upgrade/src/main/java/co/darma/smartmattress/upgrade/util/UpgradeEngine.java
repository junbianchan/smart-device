package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.upgrade.entity.UpdateResult;
import co.darma.smartmattress.upgrade.entity.UpgradeJob;
import co.darma.smartmattress.upgrade.entity.UpgradePacket;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 升级住流程，每次发送一个包，然后判断返回的包是否正确,
 * 如果没有返回或者反悔不正确，那么重新发送包
 * Created by frank on 15/12/18.
 */
public class UpgradeEngine {

    public static final int MAX_TRY_TIMES = 3;

    public static final int DELAY_SECOND = 5;

    private static Logger logger = Logger.getLogger(UpgradeEngine.class);

    private static Map<String, Integer> upgradeDevice = new ConcurrentHashMap<String, Integer>();

    public String doUpdate(UpgradeJob job) {
        validate(job);

        logger.info(">>>>Device :" + job.getDevice().getDeviceNo() + " begin to update :");
        PacketSender packetSender = new DefaultPacketSender(job.getDevice().getDeviceNo());
        try {
            //还有下个包，而且没有断开异常
            while (job.hasNext()) {
                UpgradePacket updatePacket = job.next();

                //更新结果
                UpdateResult result = null;
                //没有返回,重新发包。
                int tryTimes = 0;
                //如果结果为空，或者尝试次数小于规定次数，或者返回的包序列号不对
                while (tryTimes <= MAX_TRY_TIMES
                        && (result == null ||
                        result.getCommand() != updatePacket.getCommand() ||
                        result.getSeq() != updatePacket.getSeq())) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("UpgradePacket :" + updatePacket);
                    }

                    //不行就重发
                    packetSender.send(updatePacket);
                    result = packetSender.receive(DELAY_SECOND);
                    if(logger.isDebugEnabled()) {
                        logger.debug("updateResult :" + result + " tryTimes :" + tryTimes);
                    }
                    ++tryTimes;
                }

                if (tryTimes >= MAX_TRY_TIMES) {
                    //abort. Upgrade done.
                    logger.error(">>>>Device :" + job.getDevice().getDeviceNo() +
                            "Packet " + updatePacket + " has no response. Device " + job.getDevice() + " upgrade finished.");
                    return "Timeout when receive Ack packet:" + updatePacket;
                }
                //返回了，发送下个包。需要清空下本次的返回值
                packetSender.call(null);
            }
            //结束了
            return null;

        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
            return e.getMessage();
        } finally {
            packetSender.release();
            //回收资源
            logger.debug(">>>>Device :" + job.getDevice().getDeviceNo() + "Update finisned.");
        }
    }

    /**
     * 校验参数
     *
     * @param job
     */
    private void validate(UpgradeJob job) {

        if (job == null) {
            throw new IllegalArgumentException("UpgradeJob is null.");
        }
        if (job.getDevice() == null) {
            throw new IllegalArgumentException("Device cannot be null.");
        }

        if (upgradeDevice.get(job.getDevice().getDeviceNo()) != null) {
            throw new IllegalArgumentException("Device :" + job.getDevice().getDeviceNo() + " is updating..");
        }
    }

}

package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.upgrade.entity.UpdateResult;
import co.darma.smartmattress.upgrade.entity.UpgradePacket;
import co.darma.smartmattress.upgrade.service.impl.DefaultPacketClient;
import co.darma.smartmattress.util.BeanManagementUtil;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 发送器，对应一个设备的一个发送任务
 * Created by frank on 15/12/22.
 */
public class DefaultPacketSender implements PacketSender {


    private static Logger logger = Logger.getLogger(DefaultPacketSender.class);

    private static Lock lock = new ReentrantLock();

    private static Condition NEW_PACKET = lock.newCondition();

    private UpdateResult udateResult;

    private DefaultPacketClient client;

    private String deviceNo;

    public DefaultPacketSender(String deviceNo) {
        this.deviceNo = deviceNo;
        client = BeanManagementUtil.getBeanByType(DefaultPacketClient.class);
        client.register(deviceNo, this);
    }

    @Override
    public void send(UpgradePacket updatePacket) {
        try {
            client.send(updatePacket);
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
        }
    }

    @Override
    public void release() {
        client.unRegister(deviceNo);
    }

    @Override
    public UpdateResult receive(int delaySecond) {
        int leftTime = delaySecond * 1000;
        long lastWatchTime = System.currentTimeMillis();
        long currentWatchTime;
        try {
            lock.lock();
            while (this.udateResult == null && leftTime > 0) {
                NEW_PACKET.await(leftTime, TimeUnit.MILLISECONDS);
                currentWatchTime = System.currentTimeMillis();
                leftTime -= (currentWatchTime - lastWatchTime);
                lastWatchTime = currentWatchTime;
            }
        } catch (Exception e) {
            logger.info(LogUtil.logException(e));
        } finally {
            lock.unlock();
        }
        return udateResult;
    }

    @Override
    public void call(UpdateResult updateResult) {
        setUdateResult(updateResult);
        if (updateResult != null) {
            wakeReceivedThread();
        }

    }

    /**
     * 唤醒收取线程
     */
    private void wakeReceivedThread() {
        try {
            lock.lock();
            NEW_PACKET.signalAll();
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
        } finally {
            lock.unlock();
        }
    }

    public synchronized void setUdateResult(UpdateResult udateResult) {
        this.udateResult = udateResult;
    }

    public void setClient(DefaultPacketClient client) {
        this.client = client;
    }
}

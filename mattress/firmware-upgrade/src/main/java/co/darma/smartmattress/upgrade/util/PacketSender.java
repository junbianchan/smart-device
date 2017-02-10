package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.upgrade.entity.UpdateResult;
import co.darma.smartmattress.upgrade.entity.UpgradePacket;

/**
 * 升级包发送器;
 * 这一层是属于引擎层。
 * 在PacketSender这一层，并不关心是否需要建立连接等业务.
 * Created by frank on 15/12/18.
 */
public interface PacketSender {

    /**
     * 发送包
     *
     * @param updatePacket
     */
    public void send(UpgradePacket updatePacket);

    /**
     * 释放资源
     */
    public void release();

    /**
     * 接受包，并指定了固定的时间
     *
     * @param delaySecond
     * @return
     */
    public UpdateResult receive(int delaySecond);

    /**
     * 这是一个回调函数，由于某些通讯方式，可能会是异步通讯的，因此设置这个回调函数。
     * 如果没有的话，那么直接复写send、receive和release即可。
     *
     * @param updateResult
     */
    public void call(UpdateResult updateResult);

}

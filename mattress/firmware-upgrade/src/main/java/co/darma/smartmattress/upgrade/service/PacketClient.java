package co.darma.smartmattress.upgrade.service;

import co.darma.smartmattress.upgrade.entity.UpgradePacket;
import co.darma.smartmattress.upgrade.util.PacketSender;

/**
 * PacketSender 和 PacketClient 很类似，但是，PacketClient是属于封装了data-acceptor提供的网络通讯的一层
 * ，但是PacketSender是属于UpgradeEngine引擎的一部分。
 * Created by frank on 15/12/29.
 */
public interface PacketClient {

    public void register(String deviceNo, PacketSender sender);

    public void unRegister(String deviceNo);

    public void send(UpgradePacket updatePacket);

    public boolean isDeviceOnline(String deviceNo);
}

package co.darma.smartmattress.upgrade.service.impl;

import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.service.DeviceOnlineService;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.upgrade.entity.UpgradePacket;
import co.darma.smartmattress.upgrade.exception.NetworkException;
import co.darma.smartmattress.upgrade.service.PacketClient;
import co.darma.smartmattress.upgrade.util.DeviceProbe;
import co.darma.smartmattress.upgrade.util.PacketSender;

/**
 * 发送升级包客户端.
 * 这一层封装了data-acceptor提供的接口，现在是用直接调用的方式，以后可能需要换用框架。
 * Created by frank on 15/12/28.
 */
public class DefaultPacketClient implements PacketClient {

    private DeviceProbe deviceProbe;

    /**
     * 用于发送升级包
     * #{@link co.darma.smartmattress.service.impl.UpgradePacketDownHandler}
     */
    private PacketHandler handler;

    /**
     * 用于检测设备是否在线
     */
    private DeviceOnlineService deviceOnlineService;

    public void register(String deviceNo, PacketSender sender) {
        if (isDeviceOnline(deviceNo)) {
            deviceProbe.register(deviceNo, sender);
            if (!deviceProbe.getRunnable()) {
                new Thread(deviceProbe).start();
            }
        } else {
            throw new NetworkException("Device [" + deviceNo + "], is not online.");
        }
    }

    public void unRegister(String deviceNo) {
        deviceProbe.unRegister(deviceNo);
    }

    public void send(UpgradePacket updatePacket) {
        if (updatePacket != null) {
            MattressPacket packet = new MattressPacket();

            packet.setMagicNumber(updatePacket.getHead());
            packet.setLength(updatePacket.getLength());
            packet.setCommand((updatePacket.getCommand()));
            packet.setSeqNum(updatePacket.getSeq());
            packet.setData(updatePacket.getContent());
            packet.setChecksum(updatePacket.getCheckSum());
            packet.setDeviceId(updatePacket.getDeviceNo());
            PacketContext context = new PacketContext(packet);
            handler.handle(context);
        }
    }

    public boolean isDeviceOnline(String deviceNo) {
        return deviceOnlineService.isDeviceOnline(deviceNo);
    }

    public void setDeviceProbe(DeviceProbe deviceProbe) {
        this.deviceProbe = deviceProbe;
    }

    public void setHandler(PacketHandler handler) {
        this.handler = handler;
    }

    public void setDeviceOnlineService(DeviceOnlineService deviceOnlineService) {
        this.deviceOnlineService = deviceOnlineService;
    }
}

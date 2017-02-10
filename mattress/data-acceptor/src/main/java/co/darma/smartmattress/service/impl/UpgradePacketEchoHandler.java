package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/12/28.
 */
public class UpgradePacketEchoHandler implements PacketHandler {

    private Logger logger = Logger.getLogger(UpgradePacketEchoHandler.class);

    private ChannelCenter channelCenter = ChannelCenter.getChannelCenter();

    @Override
    public void handle(PacketContext context) {
        try {
            logger.info("Return packet is :" + context.getPacket());

            QueueUtil.putUpgradeResultPacket(context.getPacket());

            //TODO 这里有问题
            if (context.getPacket().getCommand() == 26 || context.getPacket().getCommand() == 29) {
                channelCenter.addChannel(context.getPacket().getDeviceId(), context.getContext().channel());
            }
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
        }
    }
}

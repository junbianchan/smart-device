package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.service.PacketHandler;
import org.apache.log4j.Logger;

/**
 * 离线包处理，这个暂时不处理
 * Created by frank on 15/10/29.
 */
public class OffLinePacketHandler implements PacketHandler {

    private Logger logger = Logger.getLogger(OffLinePacketHandler.class);

    @Override
    public void handle(PacketContext context) {
        logger.info("Offline packet ..." + context.getPacket());
    }
}

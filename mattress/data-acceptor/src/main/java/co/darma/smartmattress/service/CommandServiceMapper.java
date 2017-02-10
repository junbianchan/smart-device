package co.darma.smartmattress.service;

import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.util.ByteUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by frank on 15/10/29.
 */
public class CommandServiceMapper {

    private Map<Integer, PacketHandler> handlerMap;

    private static Logger logger = Logger.getLogger(CommandServiceMapper.class);

    public void mapperAndDeal(int command, PacketContext context) {

        if (logger.isDebugEnabled()) {
            logger.debug("Begin to handle command " + command
                    + ",packetTime:" + context.getPacket().getTimestamp()
                    + ", deviceNo: " + context.getPacket().getDeviceId());
        }

        if (MapUtils.isNotEmpty(handlerMap) && context != null) {
            PacketHandler actualHandler = handlerMap.get(command);
            if (actualHandler != null) {
                actualHandler.handle(context);
            } else {
                logger.error("Unknown command :" + command);
            }
        } else {
            logger.error("Unknown handle for command " + command);
        }

    }


    public void setHandlerMap(Map<Integer, PacketHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

}

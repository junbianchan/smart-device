package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by frank on 15/12/15.
 */
public class FaultPacketHandler implements PacketHandler {

    private Logger logger = Logger.getLogger(FaultPacketHandler.class);

    @Override
    public void handle(PacketContext context) {

        try {
            logger.debug("Begin to handle FaultPacket :" + context.getPacket());
            QueueUtil.addStatePacket(context.getPacket());
            //清理掉
            List<Element> cacheElements = QueueUtil.popHisotryElement(context.getPacket().getDeviceId());
            //新增任务
            if (cacheElements != null) {
                QueueUtil.putHistoryElementsToQueue(cacheElements);
            }
        } catch (SystemException e) {
            logger.error(LogUtil.logException(e));
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
        }


    }
}

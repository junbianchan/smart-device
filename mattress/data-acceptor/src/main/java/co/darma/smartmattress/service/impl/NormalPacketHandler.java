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
 * 正常在线数据处理
 * * Created by frank on 15/10/29.
 */
public class NormalPacketHandler implements PacketHandler {

    private static Logger logger = Logger.getLogger(NormalPacketHandler.class);

    public void handle(PacketContext context) {
        try {
            List<Element> cacheElements = QueueUtil.cacheHistoryElement(
                    context.getPacket().getDeviceId(),
                    context.getPacket()
            );
            //新增任务
            if (cacheElements != null) {
                QueueUtil.putHistoryElementsToQueue(cacheElements);
            }
        } catch (SystemException e) {
            logger.error(LogUtil.logException(e));
        } catch (Exception e) {
            logger.error(LogUtil.traceException(e));
        }

    }
}

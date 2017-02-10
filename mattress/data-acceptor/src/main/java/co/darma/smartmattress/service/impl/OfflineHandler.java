package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.security.spec.ECField;
import java.util.List;

/**
 * 床垫离线的时候，会触发一个包，表示离线
 * Created by frank on 16/1/11.
 */
public class OfflineHandler implements PacketHandler {

    private static Logger logger = Logger.getLogger(OfflineHandler.class);

    @Override
    public void handle(PacketContext context) {

        try {
            logger.debug("Begin to handle OffLine Packet :" + context.getPacket());
            //断网推送
            QueueUtil.addStatePacket(context.getPacket());
            //触发计算
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

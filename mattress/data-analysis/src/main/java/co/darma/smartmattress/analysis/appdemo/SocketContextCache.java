package co.darma.smartmattress.analysis.appdemo;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by frank on 15/11/5.
 */
public class SocketContextCache {

    private static final Logger logger = Logger.getLogger(SocketContextCache.class);

    private static Map<String, ChannelHandlerContext> contextMap = new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static void saveContext(String deviceNo, ChannelHandlerContext context) {
        logger.info("begin to save context for device :" + deviceNo);
        if (StringUtils.isNotBlank(deviceNo)) {
            contextMap.put(deviceNo, context);
        }
    }

    public static void removeContext(ChannelHandlerContext context) {
        System.out.println("context = [" + context + "]");
        try {
            Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = contextMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
                if (entry.getValue() == context) {
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static ChannelHandlerContext getContextByDeviceNo(String devcieNo) {
        if (StringUtils.isEmpty(devcieNo)) {
            return null;
        }
        logger.info("get Context for deviceNo :" + devcieNo + ",contains or not:" + contextMap.containsKey(devcieNo));
        return contextMap.get(devcieNo);
    }
}

package co.darma.smartmattress.parser.tool;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.internal.ConcurrentSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来存储Netty Channel的工具类
 * <p/>
 * Created by frank on 15/12/28.
 */
public class ChannelCenter {

    private final ConcurrentHashMap<String, Channel> deviceChannels = new ConcurrentHashMap<String, Channel>();

    private final static ChannelCenter INSTANCE = new ChannelCenter();

    private static Logger logger = Logger.getLogger(ChannelCenter.class);

    private ChannelCenter() {
    }

    public static ChannelCenter getChannelCenter() {
        return INSTANCE;
    }

    /**
     * 新增
     *
     * @param deviceNo
     * @param channel
     */
    public void addChannel(String deviceNo, Channel channel) {
        if (!StringUtils.isEmpty(deviceNo) && channel != null) {
            deviceChannels.put(deviceNo, channel);
        }
    }


    public Channel getChannel(String deviceNo) {
        return deviceChannels.get(deviceNo);
    }

    /**
     * 删除元素,并且返回key
     *
     * @param channel
     */
    public String removeChannel(Channel channel) {

        if(logger.isDebugEnabled()) {
            logger.debug("Channel:" + channel + ",deviceChannels " + deviceChannels);
        }
        Iterator<Map.Entry<String, Channel>> iterator = deviceChannels.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Channel> entry = iterator.next();
            if (entry.getValue().equals(channel)) {
                iterator.remove();
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 是否包含设备对应的Channel
     *
     * @param deviceId
     * @return
     */
    public boolean contain(String deviceId) {
        return deviceChannels.containsKey(deviceId);
    }


    @Override
    public String toString() {
        return "ChannelCenter{" +
                "deviceChannels=" + deviceChannels +
                '}';
    }
}

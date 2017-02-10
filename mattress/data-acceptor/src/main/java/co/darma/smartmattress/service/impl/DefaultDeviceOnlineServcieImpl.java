package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.service.DeviceOnlineService;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/12/29.
 */
public class DefaultDeviceOnlineServcieImpl implements DeviceOnlineService {

    private ChannelCenter channelCenter = ChannelCenter.getChannelCenter();

    private Logger logger = Logger.getLogger(DefaultDeviceOnlineServcieImpl.class);

    @Override
    public boolean isDeviceOnline(String deviceNo) {

        if (channelCenter.getChannel(deviceNo) != null) {
            return true;
        }
        logger.info("device channdel is null " + deviceNo);
        return false;
    }
}

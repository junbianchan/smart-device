package co.darma.smartmattress.analysis.appdemo;

import co.darma.smartmattress.analysis.entity.PushContextEntity;
import co.darma.smartmattress.analysis.spi.PushToClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;

/**
 * Created by frank on 15/11/5.
 */
public class PushPacketToClient implements PushToClient {

    public static final String NEW_LINE = "\r\n";

    private static Logger logger = Logger.getLogger(PushPacketToClient.class);

    public boolean pushStatePacket(PushContextEntity message) {

        logger.info("message pushing..." + message);

        String deviceNo = message.getDeviceNo();
        ChannelHandlerContext context = SocketContextCache.getContextByDeviceNo(deviceNo);

        if (context == null) {
            logger.error("context is null");
            return false;
        }
        String message2Client = ErrorBuilder.buildResultByContext(message);

        if (StringUtils.isNotBlank(message2Client)) {
            logger.info("begin to push message to client :" + message2Client);
            byte[] bytes = (message2Client + NEW_LINE).getBytes(Charset.forName("UTF-8"));
            ByteBuf buf = context.alloc().buffer(bytes.length);
            buf.writeBytes(bytes);
            context.writeAndFlush(buf);
        }
        return true;
    }


}

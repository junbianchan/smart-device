package co.darma.smartmattress.parser.connect;

import co.darma.smartmattress.constant.PacketConstant;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.parser.util.PacketPaser;
import co.darma.smartmattress.service.PacketHandleService;
import co.darma.smartmattress.util.ByteUtils;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.TooLongFrameException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;


/**
 * 处理下发的消息
 * Created by frank on 15/9/23.
 */
@ChannelHandler.Sharable
public class MattressSocketServerHandler extends SimpleChannelInboundHandler<byte[]> {

    private static Logger logger = Logger.getLogger(MattressSocketServerHandler.class);

    public static final long AT_LEAST_TIME = 150000000;

    private static PacketHandleService handleService = ContextLoader.getCurrentWebApplicationContext().getBean(PacketHandleService.class);

    private ChannelCenter channelCenter = ChannelCenter.getChannelCenter();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {

//      logger.info("data is :" + ByteUtils.hexToString(msg));
        MattressPacket packet = PacketPaser.parserToGetPacket(msg);

        if (packet == null) {
            logger.error("Byte format error ,which is " + ByteUtils.hexToString(msg));
            return;
        }

        if (packet.getTimestamp() < AT_LEAST_TIME && packet.getCommand() != PacketConstant.SYNC_TIME_CMD) {
            packet.changeCmdTypeTo(PacketConstant.SYNC_TIME_SHADOW_CMD);
        }

        PacketContext context = new PacketContext(packet);
        context.setSrcByte(msg);
        context.setContext(ctx);

        handleService.handle(context);
        return;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("exceptionCaught ctx = [" + ctx + "], cause = [" + cause.getMessage() + "]");

        if (cause instanceof TooLongFrameException) {
            //由于粘包导致的错误，采用鸵鸟解决方式，直接关闭socket,等待客户端自动重新连接
            //这里为了防止系统出发channelInactive函数，导致误会成离线了，会提前把channel删除
            //channel删除了，那么意味着{channelInactive}无法知道对应的设备，无法通知上层wifi断开
            channelCenter.removeChannel(ctx.channel());
        }
        cause.printStackTrace();
        ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Socket closed." + ctx);
        String deviceNo = channelCenter.removeChannel(ctx.channel());
        logger.info("device No:" + deviceNo);
        if (!StringUtils.isEmpty(deviceNo)) {
            MattressPacket packet = new MattressPacket();
            packet.setDeviceId(deviceNo);
            packet.setCommand(PacketConstant.OFFLINE_CMD);
            packet.setTimestamp(System.currentTimeMillis() / 1000);
            PacketContext context = new PacketContext(packet);
            handleService.handle(context);
        }
    }

}

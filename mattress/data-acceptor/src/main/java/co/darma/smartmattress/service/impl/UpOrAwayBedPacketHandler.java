package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.constant.PacketConstant;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.util.ByteUtils;
import co.darma.smartmattress.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank on 15/10/29.
 */
public class UpOrAwayBedPacketHandler implements PacketHandler {

    public static final int AWAY = 3;

    private static Logger logger = Logger.getLogger(UpOrAwayBedPacketHandler.class);

    private static ExecutorService fixThreadThreadPool = Executors.newFixedThreadPool(4);

    private ChannelCenter channelCenter = ChannelCenter.getChannelCenter();

    private static byte[] LENGTH = {0x00, 0xA};

    private static int length = 10;

    @Override
    public void handle(PacketContext context) {

        logger.info("UpOrAway packet " + context.getPacket());
        try {

            QueueUtil.addStatePacket(context.getPacket());
            //离开床
            if (context.getPacket().getCommand() == AWAY) {
                //清理掉
                List<Element> cacheElements = QueueUtil.popHisotryElement(context.getPacket().getDeviceId());
                //新增任务
                if (cacheElements != null) {
                    QueueUtil.putHistoryElementsToQueue(cacheElements);
                }
            }
            channelCenter.addChannel(context.getPacket().getDeviceId(), context.getContext().channel());
        } catch (SystemException e) {
            logger.error(LogUtil.traceException(e));
        } catch (Exception e) {
            logger.error(LogUtil.traceException(e));
        } finally {
            try {
                echoBack(context);
            } catch (Exception e) {
                logger.error(LogUtil.traceException(e));
            }
        }
    }

    /**
     * 告诉床垫，已经收到离线包
     *
     * @param context
     */
    private void echoBack(final PacketContext context) {

        fixThreadThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                Long currentTime = System.currentTimeMillis() / 1000;

                byte[] currentTimeByte = ByteUtils.longTo4ByteArray(currentTime);
                byte[] finalArray = new byte[length];
                // head
                finalArray[0] = PacketConstant.HEARD;
                // length
                System.arraycopy(LENGTH, 0, finalArray, 1, 2);
                // cmd
                finalArray[3] = PacketConstant.OFFLINE_BACK_CMD;
                // time
                System.arraycopy(currentTimeByte, 0, finalArray, 4, currentTimeByte.length);

                // checksum
                byte[] checkSum = {(byte) 0xFF, (byte) 0xFF};// ByteUtils.int2byteArray(CrcAlgorithm.dm_crc16(0, finalArray, 8));
                System.arraycopy(checkSum, 0, finalArray, 8, 2);

                ChannelHandlerContext handleContext = context.getContext();
                ByteBuf buf = handleContext.alloc().buffer(finalArray.length);
                buf.writeBytes(finalArray);
                handleContext.writeAndFlush(buf);
            }
        });
    }
}

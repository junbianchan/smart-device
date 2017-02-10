package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.constant.PacketConstant;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.util.ByteUtils;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank on 15/11/5.
 */
public class SynchronizedTimeHandler implements PacketHandler {

    private static byte[] LENGTH = {0x00, 0xA};

    private static int length = 10;

    private static Logger logger = Logger.getLogger(SynchronizedTimeHandler.class);

    private ChannelCenter channelCenter = ChannelCenter.getChannelCenter();

    private static ExecutorService fixThreadThreadPool = Executors.newFixedThreadPool(4);

    @Override
    public void handle(PacketContext context) {
        try {
            logger.debug("Begin to handle Synchronized Packet :" + context.getPacket());
            ChannelHandlerContext channelHandlerContext = context.getContext();
            if (context.getPacket() != null) {
                //放到队列中，用于升级程序判断是否需要进行升级
                switch (context.getPacket().getCommand()) {
                    case PacketConstant.SYNC_TIME_CMD:
                        sendSyncPacket(channelHandlerContext);
                        break;
                    case PacketConstant.SYNC_TIME_ACK_CMD:
                        //放到队列中，判断是否需要升级
                        QueueUtil.putToUpgradeQueue(context.getPacket());
                        break;
                    case PacketConstant.SYNC_TIME_SHADOW_CMD:
                        //9的表示需要更新时间
                        sendSyncPacket(channelHandlerContext);
                        break;
                    default:
                        break;

                }
                channelCenter.addChannel(context.getPacket().getDeviceId(), context.getContext().channel());
            }
        } catch (SystemException e) {
            logger.error(LogUtil.logException(e));
        }
    }

    /**
     * 发送同步数据给客户端
     *
     * @param channelHandlerContext
     */
    public static void sendSyncPacket(final ChannelHandlerContext channelHandlerContext) {


        fixThreadThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                byte[] finalArray = new byte[length];
                // head
                finalArray[0] = PacketConstant.HEARD;
                // length
                System.arraycopy(LENGTH, 0, finalArray, 1, 2);
                // cmd
                finalArray[3] = PacketConstant.SYNC_TIME_BACK_CMD;
                // time
                Long currentTime = System.currentTimeMillis() / 1000;
                byte[] currentTimeByte = ByteUtils.longTo4ByteArray(currentTime);
                System.arraycopy(currentTimeByte, 0, finalArray, 4, currentTimeByte.length);

                // checksum
                byte[] checkSum = {(byte) 0xFF, (byte) 0xFF};// ByteUtils.int2byteArray(CrcAlgorithm.dm_crc16(0, finalArray, 8));
                System.arraycopy(checkSum, 0, finalArray, 8, 2);

                // write to client.
                ByteBuf buf = channelHandlerContext.alloc().buffer(length);
                buf.writeBytes(finalArray);
                channelHandlerContext.writeAndFlush(buf);

                logger.info("Synchronized Time to Client:" + ByteUtils.hexToString(finalArray));
            }
        });


    }

}

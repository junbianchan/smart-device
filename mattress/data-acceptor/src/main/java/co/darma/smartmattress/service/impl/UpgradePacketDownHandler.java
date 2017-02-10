package co.darma.smartmattress.service.impl;

import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.service.PacketHandler;
import co.darma.smartmattress.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

/**
 * 升级包下发
 * Created by frank on 15/12/28.
 */
public class UpgradePacketDownHandler implements PacketHandler {

    /**
     * HEAD 长度 length长度 cmd长度 seq长度 crc 长度
     */
    public static final int COMMON_LENGHT = (1 + 2 + 1 + 2 + 2);

    private ChannelCenter channelCenter = ChannelCenter.getChannelCenter();

    private static Logger logger = Logger.getLogger(UpgradePacketDownHandler.class);

    @Override
    public void handle(PacketContext context) {

        String deviceNo = context.getPacket().getDeviceId();
        Channel channel = channelCenter.getChannel(deviceNo);

        if (channel != null) {
            //下升级包
            byte magicNumber = context.getPacket().getMagicNumber();
            int packetLength = context.getPacket().getLength();
            int command = context.getPacket().getCommand();
            int seq = context.getPacket().getSeqNum();
            byte[] data = context.getPacket().getData();
            int checkSum = context.getPacket().getChecksum();

            byte[] finalArray = new byte[packetLength];
            // head
            finalArray[0] = magicNumber;
            // length
            System.arraycopy(ByteUtils.int2byteArray(packetLength), 2, finalArray, 1, 2);
            // cmd
            finalArray[3] = ByteUtils.int2byteArray(command)[3];
            //Seq
            System.arraycopy(ByteUtils.int2byteArray(seq), 2, finalArray, 4, 2);
            //data
            if (data != null) {
                int dataSize = packetLength - COMMON_LENGHT;
                System.arraycopy(data, 0, finalArray, 6, dataSize);
            }
            //checksum
            System.arraycopy(ByteUtils.int2byteArray(checkSum), 2, finalArray, packetLength - 2, 2);

            ByteBuf buf = channel.alloc().buffer(finalArray.length);
            buf.writeBytes(finalArray);

            logger.info("packet to client :" + ByteUtils.hexToString(finalArray));
            channel.writeAndFlush(buf);
        }

    }


}

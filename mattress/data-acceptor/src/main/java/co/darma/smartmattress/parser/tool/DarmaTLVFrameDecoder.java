package co.darma.smartmattress.parser.tool;

import co.darma.smartmattress.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.nio.ByteOrder;
import java.util.List;

/**
 * 解析 Tag + length + Value这种报文
 * Created by frank on 16/1/18.
 */
public class DarmaTLVFrameDecoder extends ByteToMessageDecoder {

    private final ByteOrder byteOrder;

    private byte[] tag;

    private int lengthFieldOffset;

    private int lengthFieldLength;

    private int lengthFieldEndOffset;

    private static Logger logger = Logger.getLogger(DarmaTLVFrameDecoder.class);

    public DarmaTLVFrameDecoder(byte tag, int lengthFieldLength) {
        this(ByteOrder.BIG_ENDIAN, new byte[]{tag}, lengthFieldLength);
    }

    public DarmaTLVFrameDecoder(ByteOrder byteOrder, byte[] tag, int lengthFieldLength) {

        if (tag == null) {
            throw new IllegalArgumentException(
                    "tag cannot be null: " + tag);
        }

        this.byteOrder = byteOrder;
        this.tag = tag;
        this.lengthFieldOffset = tag.length;
        this.lengthFieldLength = lengthFieldLength;
        lengthFieldEndOffset = tag.length + lengthFieldLength;
    }


    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        long frameLength;
        switch (length) {
            case 1:
                frameLength = buf.getUnsignedByte(offset);
                break;
            case 2:
                frameLength = buf.getUnsignedShort(offset);
                break;
            case 3:
                frameLength = buf.getUnsignedMedium(offset);
                break;
            case 4:
                frameLength = buf.getUnsignedInt(offset);
                break;
            case 8:
                frameLength = buf.getLong(offset);
                break;
            default:
                throw new DecoderException(
                        "unsupported lengthFieldLength: " + lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
        }
        return frameLength;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }


    private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        if (in.readableBytes() < lengthFieldEndOffset) {
            return null;
        }

        if (discardUnuselessByte(in) == 0) {
            return null;
        }

        int actualLengthFieldOffset = in.readerIndex() + lengthFieldOffset;
        long frameLength = getUnadjustedFrameLength(in, actualLengthFieldOffset, lengthFieldLength, byteOrder);

        if (frameLength < 0) {
            in.skipBytes(lengthFieldEndOffset);
            throw new CorruptedFrameException(
                    "negative pre-adjustment length field: " + frameLength);
        }

        int lengthAdjustment = 0 - (this.tag.length + lengthFieldLength);
        frameLength += lengthAdjustment + lengthFieldEndOffset;

        // never overflows because it's less than maxFrameLength
        int frameLengthInt = (int) frameLength;
        if (in.readableBytes() < frameLengthInt) {
            return null;
        }

        // extract frame
        int readerIndex = in.readerIndex();
        ByteBuf frame = extractFrame(ctx, in, readerIndex, frameLengthInt);
        in.readerIndex(readerIndex + frameLengthInt);
        return frame;

    }

    /**
     * 忽略掉报文头之前的包
     *
     * @param in
     * @return
     */
    private int discardUnuselessByte(ByteBuf in) {

        byte bytetmp;
        int equalsNum = 0;
        int index = in.readerIndex();

        StringBuffer sb = new StringBuffer();
        byte t;
        for (int tagIndex = 0; tagIndex < tag.length; ) {
            t = tag[tagIndex];
            while (in.isReadable()) {
                bytetmp = in.getByte(index);
                if (bytetmp != t) {
                    //没有用，丢弃掉
                    sb.append(Integer.toHexString(in.readByte() & 0xFF));
                    index++;
                } else {
                    equalsNum++;
                    index++;
                    break;
                }
            }
            //因为当前的byte没有匹配到,退回到第一个字符的匹配
            if (in.isReadable() && equalsNum < tag.length) {
                equalsNum = 0;
                tagIndex = 0;
                continue;
            }
            //因为没有可读的了，停止匹配.
            if (!in.isReadable() && equalsNum < tag.length) {
                if (StringUtils.isNotEmpty(sb)) {
                    logger.info("discard bytes ares :" + sb);
                }
                return 0;
            }
            ++tagIndex;
        }
        if (StringUtils.isNotEmpty(sb)) {
            logger.info("discard bytes are :" + sb);
        }
        //完整匹配到
        if (equalsNum == tag.length) {
            return 1;
        }
        //没有匹配到
        return 0;
    }

    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return buffer.slice(index, length).retain();
    }

}

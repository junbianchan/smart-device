package co.darma.smartmattress.parser.connect;

import co.darma.smartmattress.parser.tool.DarmaTLVFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.Arrays;


/**
 * Created by frank on 15/9/23.
 * netty框架 ，加入一些解析Handler
 */
public class MattressSocketServerInitialzer extends ChannelInitializer<SocketChannel> {

    private static final MattressSocketServerHandler SERVER_HANDLER = new MattressSocketServerHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(8192, 1, 2, -3, 0));
        pipeline.addLast(new DarmaTLVFrameDecoder((byte)0xFE, 2));
        pipeline.addLast(new ByteArrayDecoder());
        pipeline.addLast(SERVER_HANDLER);
    }
}

package co.darma.smartmattress.analysis.appdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by frank on 15/11/5.
 */
public class PushSocketServerInitialzer extends ChannelInitializer<SocketChannel> {

    private static final PushSocketServerHandler SERVER_HANDLER = new PushSocketServerHandler();

    private static final StringDecoder DECODER = new StringDecoder();

    private static final StringEncoder ENCODER = new StringEncoder();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        pipeline.addLast(SERVER_HANDLER);

    }
}

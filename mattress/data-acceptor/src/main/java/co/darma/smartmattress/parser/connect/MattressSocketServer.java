package co.darma.smartmattress.parser.connect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/9/23.
 */
public class MattressSocketServer extends Thread {

    private static int PORT = 17000;

    private static Logger logger = Logger.getLogger(MattressSocketServer.class);

    public MattressSocketServer() {

    }

    @Override
    public void run() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MattressSocketServerInitialzer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            b.bind(PORT).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}

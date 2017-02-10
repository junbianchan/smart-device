package co.darma.smartmattress.analysis.appdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by frank on 15/11/4.
 */
public class DemoSocketServer extends Thread {

    private static int PORT = 17001;

    private static Logger logger = Logger.getLogger(DemoSocketServer.class);

    @Override
    public void run() {
        logger.info("Begin to linster on 17001 ");

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new PushSocketServerInitialzer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            b.bind(PORT).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {

        DemoSocketServer server = new DemoSocketServer();
        server.setDaemon(true);
        server.start();

        byte[] input = new byte[5];
        try {
            while ((-1 != System.in.read(input))) {
                String inpuStr = new String(input);
                if (inpuStr.trim().equals("bye")) {
                    System.exit(1);
                    return;

                } else {
                    System.out.println("inpuStr = [" + inpuStr + "]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

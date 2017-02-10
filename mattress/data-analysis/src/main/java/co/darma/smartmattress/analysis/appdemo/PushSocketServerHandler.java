package co.darma.smartmattress.analysis.appdemo;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.dao.ManBehaviorDao;
import co.darma.smartmattress.analysis.dao.impl.ManBehaviorDaoImpl;
import co.darma.smartmattress.analysis.entity.ManBehavior;
import co.darma.smartmattress.analysis.entity.PushContextEntity;
import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.dao.impl.DeviceDaoImpl;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceCookie;
import co.darma.smartmattress.util.BeanManagementUtil;
import co.darma.smartmattress.util.LogUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by frank on 15/11/5.
 */
@ChannelHandler.Sharable
public class PushSocketServerHandler extends SimpleChannelInboundHandler<String> {

    public static final String REGEX = ";";
    private Logger logger = Logger.getLogger(PushSocketServerHandler.class);

    private ManBehaviorDao manBehaviorDao;

    private DeviceDao deviceDao;

    private AccessTokenManager manager;


    public PushSocketServerHandler() {
        manBehaviorDao = BeanManagementUtil.getBeanByType(ManBehaviorDaoImpl.class);
        deviceDao = BeanManagementUtil.getBeanByType(DeviceDaoImpl.class);
        manager = BeanManagementUtil.getBeanByType(AccessTokenManager.class);
    }


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws InterruptedException { // (1)
        logger.info("ctx = [" + ctx + "]");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("ctx = [" + ctx + "], msg = [" + msg + "]");

        try {
            if (StringUtils.isNotBlank(msg)) {

                String[] packet = msg.split(REGEX);
                if (packet.length < 5) {
                    String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.CLIENT_ERROR, "Argument Format Error.");
                    ctx.writeAndFlush(errorMessage + "\r\n");
                    return;
                }
                if (!StringUtils.equalsIgnoreCase(packet[0], "Darma")) {
                    String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.CLIENT_ERROR, "Packet Head Should be Darma.");
                    ctx.writeAndFlush(errorMessage + "\r\n");
                    return;
                }

                String cmd = packet[1];

                if (StringUtils.equalsIgnoreCase("Cmd:Login", cmd)) {

                    String deviceNoStr = packet[2];
                    String[] devcieNoArray = deviceNoStr.split(":");
                    if (devcieNoArray.length != 2) {
                        String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.ARGUMENT_ILLEAGEL, "[DeviceNo] is invalid.");
                        ctx.writeAndFlush(errorMessage + "\r\n");
                        return;
                    }
                    String deviceNo = devcieNoArray[1];

                    Device device = deviceDao.queryDeviceByDeviceNo(deviceNo);

                    if (device != null) {

                        String userNameStr = packet[3];
                        String userName = userNameStr.substring(userNameStr.indexOf(":") + 1);


                        String accessTokenStr = packet[4];

                        if (StringUtils.isNoneEmpty(accessTokenStr)) {

                            String[] tmp = accessTokenStr.split(":");
                            if (tmp.length > 1) {
                                String accessToken = tmp[1];
                                AccessContext context = manager.authByToken(accessToken);
                                logger.info("PushSocketServerHandler access Token  is :" + context);
                                if (context != null) {
                                    logger.info("Client login context is :" + context);
                                    SocketContextCache.saveContext(deviceNo, ctx);
                                    ctx.writeAndFlush(ErrorBuilder.buildSucess(deviceNo, userName) + "\r\n");
                                    buildAndSendState(ctx, device);
                                    return;
                                }
                            }
                        }
                        logger.error("Error token :" + accessTokenStr);
                        String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.ACCESS_DENIED, "[AccessToken] is null or invalid.");
                        ctx.writeAndFlush(errorMessage + "\r\n").addListener(ChannelFutureListener.CLOSE);
                        return;
                    } else {
                        String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.ARGUMENT_ILLEAGEL, "[DeviceNo] is not exist.");
                        ctx.writeAndFlush(errorMessage + "\r\n").addListener(ChannelFutureListener.CLOSE);
                        logger.error("Device Id is not exist." + deviceNo);
                    }
                    return;
                } else if (StringUtils.equalsIgnoreCase("Cmd:Logout", cmd)) {
                    SocketContextCache.removeContext(ctx);
                    return;
                } else {
                    String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.ARGUMENT_ILLEAGEL, "[Cmd] should be Login or Logout.");
                    ctx.writeAndFlush(errorMessage + "\r\n").addListener(ChannelFutureListener.CLOSE);
                    return;
                }

            }
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
            String errorMessage = ErrorBuilder.buildErrorMessage(ErrorBuilder.SERVER_ERROR, "Server Error.");
            ctx.writeAndFlush(errorMessage + "\r\n").addListener(ChannelFutureListener.CLOSE);
            SocketContextCache.removeContext(ctx);
            return;
        }

    }

    /**
     * 发送是否用户在床
     *
     * @param ctx
     * @param device
     */
    private void buildAndSendState(ChannelHandlerContext ctx, Device device) {

        DeviceCookie cookie = QueueUtil.getDeviceCookie(device.getDeviceNo());

        try {
            if (cookie == null) {
                //离线过
                Long limitTime = (System.currentTimeMillis() / 1000) - 24 * 60 * 60;
                ManBehavior behavior = manBehaviorDao.queryLastBehavior(device.getId());
                String type;
                Long time;
                if (behavior != null && behavior.getMarkTime() > limitTime) {
                    type = behavior.getType().getId().toString();
                    time = behavior.getMarkTime();
                } else {
                    type = "3";
                    time = System.currentTimeMillis() / 1000;
                }
                PushContextEntity message = new PushContextEntity();
                message.setDeviceNo(device.getDeviceNo());
                message.setTimeStamp(time);
                message.setType(type);
                String finalMessage = ErrorBuilder.buildResultByContext(message);
                logger.info("Push Device message :" + finalMessage);
                ctx.writeAndFlush(finalMessage);

            } else {
                //在线
                PushContextEntity message = new PushContextEntity();
                message.setDeviceNo(device.getDeviceNo());
                message.setTimeStamp(cookie.getLastUpBedTime());
                message.setType(cookie.getOnBed() ? "4" : "3");
                String finalMessage = ErrorBuilder.buildResultByContext(message);
                logger.info("Push Device message :" + finalMessage);
                ctx.writeAndFlush(finalMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("exceptionCaught ctx = [" + ctx + "], cause = [" + cause + "]");
        cause.printStackTrace();
        ctx.close();
        SocketContextCache.removeContext(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Inactive ctx = [" + ctx + "]");
        SocketContextCache.removeContext(ctx);
    }

    public void setManBehaviorDao(ManBehaviorDao manBehaviorDao) {
        this.manBehaviorDao = manBehaviorDao;
    }
}

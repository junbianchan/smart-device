package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.analysis.entity.ManBehavior;
import co.darma.smartmattress.analysis.entity.PushContextEntity;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.analysis.spi.PushToClient;
import co.darma.smartmattress.dao.UserDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.UserInfo;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/11/4.
 */
public class PushPacketServiceImpl implements PacketAnalysisService {


    private static Logger logger = Logger.getLogger(PushPacketServiceImpl.class);

    private PushToClient pushClient;

    private UserDao userDao;

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {
        PushContextEntity message = new PushContextEntity();
        Device device = (Device) input.getArgByName(HistoryPacketHandleService.DEVICE);
        if (device == null) {
            logger.error("device is null ,cannot push the ");
            return;
        }
        String deviceNo = device.getDeviceNo();
        ManBehavior manBehavior = (ManBehavior) output.getArgByName(SatePacketRecordServiceImpl.MAN_BEHAVIOR);
        if (manBehavior != null) {
            message.setDeviceNo(deviceNo);
            message.setTimeStamp(manBehavior.getMarkTime());
            message.setType(manBehavior.getType().getId() == null ? "" : manBehavior.getType().getId().toString());

            Integer userId = (Integer) output.getArgByName(ManDeviceRelationService.USER_ID);
            if (userId != null) {
                UserInfo user = userDao.queryUserInfoById(userId);
                if (user != null) {
                    message.setUserName(user.getUserName());
                } else {
                    logger.error("There is no user information for userId :" + userId);
                }
            }
            pushClient.pushStatePacket(message);
        } else {
            logger.error("manBehavior is null");
        }


    }


    public void setPushClient(PushToClient pushClient) {
        this.pushClient = pushClient;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

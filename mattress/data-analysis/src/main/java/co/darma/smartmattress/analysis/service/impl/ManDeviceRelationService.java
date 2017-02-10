package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.dao.DeviceManRelationDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceManRelation;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 查询包里面设备和人的绑定关系
 * Created by frank on 15/10/28.
 */
public class ManDeviceRelationService implements PacketAnalysisService {

    private static Logger logger = Logger.getLogger(ManDeviceRelationService.class);

    public static final String USER_ID = "userId";

    public static final String DEVICE = "device";

    private DeviceDao deviceDao;

    private DeviceManRelationDao deviceManRelationDao;

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {

        List<ServicePacket> packetList = (List<ServicePacket>) input.getArgByName(DefaultHealthPacketAnalysisService.PACKET_LIST);

        ServicePacket packet = null;

        if (CollectionUtils.isNotEmpty(packetList)) {

            packet = packetList.get(0);
        } else {
            packet = (ServicePacket) input.getArgByName(StateChangeHandleService.STAT_PACKET);
        }

        if (packet == null) {
            logger.error("There is no packet.");
            return;
        }

        String deviceNo = packet.getDeviceNo();
        Device device = deviceDao.queryDeviceByDeviceNo(deviceNo);
        output.putArgument(DEVICE, device);

        if (device == null) {
            logger.error("Device Id is null ,the packet is invalid.");
            return;
        }

        List<DeviceManRelation> relationList = deviceManRelationDao.queryHeartByMan(device.getId());
        if (CollectionUtils.isNotEmpty(relationList)) {
            Integer userId = relationList.get(0).getUserId();
            output.putArgument(USER_ID, userId);
        } else {
            logger.error("Cannot get User id for device No.:" + deviceNo);
        }


    }

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public void setDeviceManRelationDao(DeviceManRelationDao deviceManRelationDao) {
        this.deviceManRelationDao = deviceManRelationDao;
    }
}

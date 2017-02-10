package co.darma.smartmattress.analysis.handle;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.engine.ServicesEngine;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.service.Service;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by frank on 15/11/4.
 */
public class StateChangeHandleService extends Service {


    public static final String STAT_PACKET = "stateChagePacket";

    private Logger logger = Logger.getLogger(StateChangeHandleService.class);

    private Map<Integer, ServicesEngine> engineMap;

    private DeviceDao deviceDao;

    @Override
    public void execute() {

        try {
            Element element = QueueUtil.takeStatePacket();
            ServicePacket packet = new ServicePacket(element);
            ServiceContext input = new ServiceContext();
            input.putArgument(STAT_PACKET, packet);

            String deviceNo = packet.getDeviceNo();

            Device device = deviceDao.queryDeviceByDeviceNo(deviceNo);
            if (device == null || device.getProject() == null) {
                logger.error("Device " + device + " or project  is null ,the packet is invalid.");
                return;
            }
            ServicesEngine engine = engineMap.get(device.getProject().getProjectId());
            input.putArgument(HistoryPacketHandleService.DEVICE, device);
            long startTime = System.currentTimeMillis();
            engine.process(input);
            logger.info(deviceNo + "state packet handle total cost:" + (System.currentTimeMillis() - startTime) + " millis seconds.");

        } catch (SystemException e) {
            logger.error(LogUtil.logException(e));
        }
    }

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public void setEngineMap(Map<Integer, ServicesEngine> engineMap) {
        this.engineMap = engineMap;
    }
}

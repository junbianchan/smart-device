package co.darma.smartmattress.analysis.handle;

import co.darma.smartmattress.analysis.service.impl.DefaultHealthPacketAnalysisService;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.engine.ServicesEngine;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.service.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/10/13.
 */
public class HistoryPacketHandleService extends Service {

    private Logger logger = Logger.getLogger(HistoryPacketHandleService.class);

    private Map<Integer, ServicesEngine> projectToEngine;

    private DeviceDao deviceDao;

    public static final String DEVICE = "device";

    @Override
    public void execute() {

        try {
            List<Element> elements = QueueUtil.takeHistoryElementFromQueue();//阻塞获取
            List<ServicePacket> servicePacketsList = new LinkedList<ServicePacket>();
            if (CollectionUtils.isNotEmpty(elements)) {
                for (Element e : elements) {
                    servicePacketsList.add(new ServicePacket(e));
                }
            } else {
                logger.error("Get the packet is empty");
                return;
            }
            ServiceContext input = new ServiceContext();
            input.putArgument(DefaultHealthPacketAnalysisService.PACKET_LIST, servicePacketsList);

            ServicePacket packet = servicePacketsList.get(0);
            String deviceNo = packet.getDeviceNo();

            Device device = deviceDao.queryDeviceByDeviceNo(deviceNo);
            if (device == null) {
                logger.error("Device " + deviceNo + " is null ,the packet is invalid.");
                return;
            }
            logger.info(device.getDeviceNo() + " Start handle the packet num is :" + elements.size());

            input.putArgument(DEVICE, device);
            if (device.getProject() != null) {
                ServicesEngine engine = projectToEngine.get(device.getProject().getProjectId());
                if (engine != null) {
                    long startTime = System.currentTimeMillis();
                    engine.process(input);
                    logger.info(deviceNo + " total cost:" + (System.currentTimeMillis() - startTime) + " millis seconds.");
                    return;
                }
            }
            logger.error(device.getDeviceNo() + ":Error cannot find engine for project :" + device.getProject());
        } catch (SystemException e) {
            logger.error(e);
        }
    }

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public void setProjectToEngine(Map<Integer, ServicesEngine> projectToEngine) {
        this.projectToEngine = projectToEngine;
    }
}

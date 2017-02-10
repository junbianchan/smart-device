package co.darma.smartmattress.analysis.engine;

import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by frank on 15/10/23.
 */
public class ServicesEngine {

    private List<PacketAnalysisService> serviceList;

    private static Logger logger = Logger.getLogger(ServicesEngine.class);

    public ServiceContext process(ServiceContext input) {

        if (ServiceContext.isEmpty(input)) {
            return input;
        }
        ServiceContext output = new ServiceContext();

        for (PacketAnalysisService service : serviceList) {
            try {
                long startTime = System.currentTimeMillis();
                service.analysis(input, output);
                Device device = (Device) input.getArgByName("device");
                if (device != null) {
                    logger.info(service.getClass().getName() + " handle " + device.getDeviceNo()
                            + " total cost: " + (System.currentTimeMillis() - startTime) + " millis seconds.");
                } else {
                    logger.error("Device is null.");
                }
            } catch (RuntimeException e) {
                logger.error("Exception where analysis the packet :" + LogUtil.logException(e));
                return output;
            }
        }

        return output;
    }

    public List<PacketAnalysisService> getServiceList() {

        return serviceList;
    }

    public void setServiceList(List<PacketAnalysisService> serviceList) {
        this.serviceList = serviceList;
    }
}

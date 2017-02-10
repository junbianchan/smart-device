package co.darma.smartmattress.analysis;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.analysis.entity.HealthData;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.service.impl.DefaultHealthPacketAnalysisService;
import co.darma.smartmattress.analysis.service.impl.ManDeviceRelationService;
import co.darma.smartmattress.analysis.service.impl.MeddoHealthDataService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/5.
 */
public class MeddoHealthDataServiceTest extends BaseTestCase {

    public void testSaveData() {
        MeddoHealthDataService meddoHealthDataService = getContext().getBean(MeddoHealthDataService.class);


        ServiceContext input = new ServiceContext();

        List<HealthData> healthDataList = new LinkedList<HealthData>();
        Device device = new Device();
        device.setId(10);
        device.setDeviceNo("test");
        for (int i = 70; i < 129; ++i) {
            HealthData data = new HealthData();
            data.setMarkTime(i * 1L);
            data.setDevice(device);
            data.setBreathValue(i);
            data.setHeartRate(i);
            data.setAlgorithmVersion("1.0");
            healthDataList.add(data);
        }
        input.putArgument(DefaultHealthPacketAnalysisService.DATA_BEGIN_INDEX, 119);
        input.putArgument(DefaultHealthPacketAnalysisService.HEALTH_DATA, healthDataList);
        input.putArgument(ManDeviceRelationService.DEVICE, device);
        meddoHealthDataService.analysis(input, input);
        input.putArgument(DefaultHealthPacketAnalysisService.HEALTH_DATA, null);
        meddoHealthDataService.analysis(input, input);

    }
}

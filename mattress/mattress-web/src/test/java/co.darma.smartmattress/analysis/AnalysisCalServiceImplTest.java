package co.darma.smartmattress.analysis;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.analysis.healthAlgorithms.basic.DMAlgorithmMattress;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.analysis.service.impl.AnalysisCalServiceImpl;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceCookie;
import mockit.Mock;
import mockit.MockUp;

/**
 * Created by frank on 16/1/6.
 */
public class AnalysisCalServiceImplTest extends BaseTestCase {


    private void mock() {
        MockUp mu3 = new MockUp<QueueUtil>() {

            @Mock
            public DeviceCookie getDeviceCookie(String deviceNo) {
                DeviceCookie cookie = new DeviceCookie("0d8a3f6d7ac6");
                cookie.setLastUpBedTime(1452080000L);
                return cookie;
            }
        };
        MockUp mu4 = new MockUp<DMAlgorithmMattress>() {

            @Mock
            public int[] sleepStageAnalysis(final int[] motionRecord) {

                int size = (motionRecord.length / 60) + 1;
                int[] result = new int[size];

                for (int i = 0; i < size; ++i) {
                    result[i] = 2;
                }

                for(int i = size - 3;i < size;++i){
                    result[i] = 3;
                }
                return result;
            }
        };

    }

    public void testAnalysis() {
        mock();


        PacketAnalysisService service = getContext().getBean(AnalysisCalServiceImpl.class);

        ServicePacket servicePacket = new ServicePacket();
        servicePacket.setCmd(3);
        servicePacket.setTimestamp(1452089000L);
        Device d = new Device();
        d.setId(22);
        ServiceContext input = new ServiceContext();
        input.putArgument(HistoryPacketHandleService.DEVICE, d);
        input.putArgument(StateChangeHandleService.STAT_PACKET, servicePacket);
        service.analysis(input, input);
    }

}

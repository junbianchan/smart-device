package co.darma.smartmattress.wsclient;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.analysis.entity.PushContextEntity;
import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.service.PushToMeddoService;
import co.darma.smartmattress.util.BeanManagementUtil;
import mockit.Mock;
import mockit.MockUp;

/**
 * Created by frank on 15/11/20.
 */
public class PushToMeddoServiceTest extends BaseTestCase {

    public void testpush() throws InterruptedException {

        PushToMeddoService service =
                getContext().getBean(PushToMeddoService.class);

        PushContextEntity message = new PushContextEntity();

        message.setTimeStamp(System.currentTimeMillis() / 1000);
        message.setType("3");
        message.setDeviceNo("f7c428730feb");
        service.pushStatePacket(message);

        Thread.sleep(10 * 1000);

    }
}

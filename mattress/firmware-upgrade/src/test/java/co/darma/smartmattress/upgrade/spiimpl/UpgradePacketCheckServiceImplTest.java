package co.darma.smartmattress.upgrade.spiimpl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.upgrade.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frank on 15/12/31.
 */
public class UpgradePacketCheckServiceImplTest extends BaseTest {


    @Test
    public void testExecute() throws Exception {

        MattressPacket packet = new MattressPacket();

        packet.setDeviceId("0d8a3f6d7ac6");
        byte[] data = new byte[]{0x00, 0x09};
        packet.setData(data);

        QueueUtil.putToUpgradeQueue(packet);

        UpgradePacketCheckServiceImpl service = getContext().getBean(UpgradePacketCheckServiceImpl.class);

        service.start();

        Thread.sleep(2 * 1000);
    }
}
package co.darma.smartmattress.upgrade.service.impl;

import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.upgrade.BaseTest;
import co.darma.smartmattress.upgrade.entity.Firmware;
import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import org.junit.Test;

/**
 * Created by frank on 15/12/31.
 */
public class UpgradeRequstServiceImplTest extends BaseTest {

    UpgradeRequstServiceImpl service = getContext().getBean(UpgradeRequstServiceImpl.class);

    @Test
    public void testGetUpgradeRequest() throws Exception {

        UpgradeRequest request = service.getUpgradeRequest(null);
        System.out.println(request);
    }

    @Test
    public void testAddNewUpgradeRequest() throws Exception {

        UpgradeRequest request = new UpgradeRequest();
        Device d = new Device();
        d.setId(19);
        d.setDeviceNo("0d8a3f6d7ac6");
        request.setDevice(d);
        request.setPlanUpdateTime(System.currentTimeMillis());
        Firmware firmware = new Firmware();
        firmware.setFirmwareId(1L);
        request.setSourceFirmware(firmware);
        request.setTargetFirmware(firmware);
        service.addNewUpgradeRequest(request);
    }

    @Test
    public void testSaveUpgradeRequestRecord() throws Exception {

    }

    @Test
    public void testDeleteUpgradeRequst() throws Exception {
        UpgradeRequest request = new UpgradeRequest();
        request.setId(4L);
        service.deleteUpgradeRequst(request);
    }

    @Test
    public void testDeviceHashUpgradeRequestOrNot() throws Exception {

    }
}
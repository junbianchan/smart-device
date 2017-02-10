package co.darma.smartmattress.upgrade.service.impl;

import co.darma.smartmattress.upgrade.BaseTest;
import co.darma.smartmattress.upgrade.service.VersionController;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frank on 15/12/31.
 */
public class VersionControllerImplTest extends BaseTest {

    @Test
    public void testCheckDeviceNeedUpdateOrNot() throws Exception {
        VersionController controller = getContext().getBean(VersionControllerImpl.class);
        System.out.println(controller.checkDeviceNeedUpdateOrNot("0d8a3f6d7ac6", 0.9));
    }

    @Test
    public void testGetDeviceFirmware() throws Exception {

        VersionController controller = getContext().getBean(VersionControllerImpl.class);

        System.out.println(controller.getDeviceFirmware("0d8a3f6d7ac6"));
    }

    @Test
    public void testGetLastFirmwareware() throws Exception {

        VersionController controller = getContext().getBean(VersionControllerImpl.class);

        System.out.println(controller.getLastFirmwareware(1));
    }

}
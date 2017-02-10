package co.darma.smartmattress.upgrade.dao.impl;

import co.darma.smartmattress.upgrade.BaseTest;
import co.darma.smartmattress.upgrade.dao.DeviceWithFirmwareDao;
import co.darma.smartmattress.upgrade.entity.DeviceWithFirmware;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frank on 15/12/31.
 */
public class DeviceWithFirmwareDaoImplTest extends BaseTest {

    @Test
    public void testQueryDeviceByDeviceNo() throws Exception {

        DeviceWithFirmwareDao dao = getContext().getBean(DeviceWithFirmwareDaoImpl.class);

        System.out.println(dao.queryDeviceByDeviceNo("0d8a3f6d7ac6"));
    }

    @Test
    public void testUpdateDevice() throws Exception {

        DeviceWithFirmwareDao dao = getContext().getBean(DeviceWithFirmwareDaoImpl.class);

        DeviceWithFirmware deviceWithFirmware = dao.queryDeviceByDeviceNo("0d8a3f6d7ac6");

        deviceWithFirmware.getFirmware().setFirmwareId(2L);

        dao.updateDeviceFirmware(deviceWithFirmware);
    }


}
package co.darma.smartmattress.upgrade.dao.impl;

import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.Project;
import co.darma.smartmattress.upgrade.BaseTest;
import co.darma.smartmattress.upgrade.dao.UpgradeRequestDao;
import co.darma.smartmattress.upgrade.entity.Firmware;
import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import co.darma.smartmattress.upgrade.entity.UpgradeRequestRecord;

/**
 * Created by frank on 15/12/30.
 */
public class UpgradeRequestDaoImplTest extends BaseTest {

    @org.junit.Test
    public void testGetUpgradeRequest() throws Exception {
        UpgradeRequestDao dao = getContext().getBean(UpgradeRequestDao.class);
        System.out.println(dao.getUpgradeRequest(null));
    }

    @org.junit.Test
    public void testAddNewUpgradeRequest() throws Exception {


        UpgradeRequestDao dao = getContext().getBean(UpgradeRequestDao.class);
        UpgradeRequest request = new UpgradeRequest();
        Device d = new Device();
        d.setId(26);
        Project project = new Project();
        d.setProject(project);
        request.setDevice(d);
        request.setPlanUpdateTime(System.currentTimeMillis());
        Firmware firmware = new Firmware();
        firmware.setFirmwareId(1L);
        request.setSourceFirmware(firmware);
        request.setTargetFirmware(firmware);

        dao.addNewUpgradeRequest(request);

    }

    @org.junit.Test
    public void testSaveUpgradeRequestRecord() throws Exception {

        UpgradeRequestDao dao = getContext().getBean(UpgradeRequestDao.class);

        UpgradeRequestRecord request = new UpgradeRequestRecord();
        Device d = new Device();
        d.setId(26);
        Project project = new Project();
        d.setProject(project);
        request.setDevice(d);
        request.setPlanUpdateTime(System.currentTimeMillis());

        Firmware firmware = new Firmware();
        firmware.setFirmwareId(1L);
        request.setSourceFirmware(firmware);
        request.setTargetFirmware(firmware);
        request.setActualUpdateTime(System.currentTimeMillis());
        request.setUpdateResult(true);
        request.setUpdateMessage("sucess");

        dao.saveUpgradeRequestRecord(request);

    }

    @org.junit.Test
    public void testDeleteUpgradeRequstById() throws Exception {

        UpgradeRequestDao dao = getContext().getBean(UpgradeRequestDao.class);
        dao.deleteUpgradeRequstById(1L);

    }

    @org.junit.Test
    public void testDeviceHashUpgradeRequestOrNot() throws Exception {
        UpgradeRequestDao dao = getContext().getBean(UpgradeRequestDao.class);
        System.out.println(dao.deviceHashUpgradeRequestOrNot(23, 1L));
    }
}
package co.darma.smartmattress.upgrade.dao.impl;

import co.darma.smartmattress.upgrade.BaseTest;
import co.darma.smartmattress.upgrade.dao.FirmwareDao;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frank on 15/12/31.
 */
public class FirmwareDaoImplTest extends BaseTest {

    @Test
    public void testQueryFirmwareByProjectId() throws Exception {

    }

    @Test
    public void testQueryFirmwareById() throws Exception {

        FirmwareDao dao = getContext().getBean(FirmwareDaoImpl.class);
        dao.queryFirmwareById(1L);
    }

    @Test
    public void testQueryFirmwarebyProjectAndVersion() {

        FirmwareDao dao = getContext().getBean(FirmwareDaoImpl.class);

        System.out.println(dao.queryFirmwarebyProjectAndVersion(1, 1.0));

    }
}
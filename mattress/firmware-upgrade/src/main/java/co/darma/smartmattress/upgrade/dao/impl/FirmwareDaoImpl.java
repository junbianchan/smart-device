package co.darma.smartmattress.upgrade.dao.impl;

import co.darma.smartmattress.upgrade.dao.BaseDao;
import co.darma.smartmattress.upgrade.dao.FirmwareDao;
import co.darma.smartmattress.upgrade.entity.Firmware;
import co.darma.smartmattress.util.ArgumentHandleUtil;

import java.util.List;

/**
 * Created by frank on 15/12/31.
 */
public class FirmwareDaoImpl extends BaseDao implements FirmwareDao {

    @Override
    public List<Firmware> queryLastestFirmwareByProjectId(Integer projectId) {
        return getDataAcessor().queryList("queryLastestFirmwareByProjectId", projectId);
    }

    @Override
    public Firmware queryFirmwareById(Long firmwareId) {
        return (Firmware) getDataAcessor().queryObjectByObject("queryFirmwareById", firmwareId);
    }

    @Override
    public Firmware queryFirmwarebyProjectAndVersion(Integer projectId, Double version) {
        return (Firmware) getDataAcessor().queryObjectByObject("queryFirmwareByProAndVer",
                ArgumentHandleUtil.buildArgument("projectId", projectId, "version", version));
    }
}

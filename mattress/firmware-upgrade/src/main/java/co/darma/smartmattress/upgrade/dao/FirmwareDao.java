package co.darma.smartmattress.upgrade.dao;

import co.darma.smartmattress.upgrade.entity.Firmware;

import java.util.List;

/**
 * Created by frank on 15/12/31.
 */
public interface FirmwareDao {

    public List<Firmware> queryLastestFirmwareByProjectId(Integer projectId);

    public Firmware queryFirmwareById(Long firmwareId);

    public Firmware queryFirmwarebyProjectAndVersion(Integer projectId, Double version);
}

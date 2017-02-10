package co.darma.smartmattress.dao.impl;

import co.darma.smartmattress.dao.DeviceManRelationDao;
import co.darma.smartmattress.entity.DeviceManRelation;
import co.darma.smartmattress.database.DataAccess;

import java.util.List;

/**
 * Created by frank on 15/10/28.
 */

//@Repository(value = "deviceManRelationDao")
public class DeviceManRelationDaoImpl implements DeviceManRelationDao {

    private DataAccess dataAcessor;

    @Override
    public boolean insertDeviceManRelathion(DeviceManRelation deviceManRelation) {
        dataAcessor.saveOrUpdateObject("co.darma.smartmattress.insertDeviceManRelation", deviceManRelation);
        return false;
    }

    @Override
    public void updateDeviceManRelathionBatch(List<DeviceManRelation> deviceManRelationList) {
        dataAcessor.batchSaveObject("co.darma.smartmattress.updateDeviceRelationBatch", deviceManRelationList);
    }

    @Override
    public List<DeviceManRelation> queryHeartByMan(Integer deviceId) {
        return dataAcessor.queryList("co.darma.smartmattress.queryDeviceRelationList", deviceId);
    }

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}

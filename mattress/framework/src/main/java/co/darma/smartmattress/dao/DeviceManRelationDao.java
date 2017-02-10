package co.darma.smartmattress.dao;

import co.darma.smartmattress.entity.DeviceManRelation;

import java.util.List;

/**
 * Created by frank on 15/10/28.
 */
public interface DeviceManRelationDao {

    public boolean insertDeviceManRelathion(DeviceManRelation deviceManRelation);

    public void updateDeviceManRelathionBatch(List<DeviceManRelation> deviceManRelationList);

    public List<DeviceManRelation> queryHeartByMan(Integer deviceId);
}


package co.darma.smartmattress.dao.impl;

import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.database.DataAccess;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/10/26.
 */
public class DeviceDaoImpl implements DeviceDao {

    private DataAccess dataAcessor;

    @Override
    public Device queryDeviceByDeviceNo(String deviceNo) {
        if (StringUtils.isNotEmpty(deviceNo)) {
            Map value= new HashMap<String,String>();
            value.put("deviceNo",deviceNo);
            List<Device> deviceList = dataAcessor.queryList("co.darma.smartmattress.queryDeviceByNo", value);
            if (CollectionUtils.isNotEmpty(deviceList)) {
                return deviceList.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean insertDevice(Device device) {
        if (device != null) {
            return dataAcessor.saveOrUpdateObject("co.darma.smartmattress.insertNewDevice", device);
        }
        return false;
    }

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}

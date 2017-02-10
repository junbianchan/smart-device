package co.darma.smartmattress.service;

/**
 * 用于检测设备是否在线
 * Created by frank on 15/12/29.
 */
public interface DeviceOnlineService {

    /**
     * 判断设备是否在线
     *
     * @param deviceNo
     * @return
     */
    public boolean isDeviceOnline(String deviceNo);
}

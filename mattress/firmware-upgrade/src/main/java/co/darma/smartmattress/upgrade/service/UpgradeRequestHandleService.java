package co.darma.smartmattress.upgrade.service;

import co.darma.smartmattress.upgrade.entity.Firmware;

/**
 * Created by frank on 15/12/24.
 */
public interface UpgradeRequestHandleService {

    public String createUpgradeRequest(String deviceNo, Firmware lastFirmware);
}

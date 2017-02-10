package co.darma.smartmattress.upgrade.service.impl;

import co.darma.smartmattress.upgrade.dao.UpgradeRequestDao;
import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import co.darma.smartmattress.upgrade.entity.UpgradeRequestRecord;
import co.darma.smartmattress.upgrade.service.UpgradeRequstService;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by frank on 15/12/29.
 */
public class UpgradeRequstServiceImpl implements UpgradeRequstService {

    private UpgradeRequestDao upgradeRequestDao;

    @Override
    public UpgradeRequest getUpgradeRequest(Integer projectId) {
        UpgradeRequest request = upgradeRequestDao.getUpgradeRequest(projectId);
        return request;
    }

    @Override
    public void addNewUpgradeRequest(UpgradeRequest request) {
        try {
            upgradeRequestDao.addNewUpgradeRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUpgradeRequestRecord(UpgradeRequest request, boolean updateResult, String updateMessage) {
        UpgradeRequestRecord requestRecord = new UpgradeRequestRecord(request);
        requestRecord.setUpdateResult(updateResult);
        requestRecord.setUpdateMessage(updateMessage);
        requestRecord.setActualUpdateTime(System.currentTimeMillis());
        requestRecord.setDevice(request.getDevice());
        requestRecord.setPlanUpdateTime(request.getPlanUpdateTime());
        upgradeRequestDao.saveUpgradeRequestRecord(requestRecord);
    }

    @Override
    public void deleteUpgradeRequst(UpgradeRequest request) {
        if (request != null && null != request.getId()) {
            upgradeRequestDao.deleteUpgradeRequstById(request.getId());
        }
    }

    @Override
    public boolean deviceHashUpgradeRequestOrNot(Integer deviceId, Long firmwareId) {
        return upgradeRequestDao.deviceHashUpgradeRequestOrNot(deviceId, firmwareId);
    }

    public void setUpgradeRequestDao(UpgradeRequestDao upgradeRequestDao) {
        this.upgradeRequestDao = upgradeRequestDao;
    }
}

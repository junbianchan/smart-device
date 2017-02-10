package co.darma.smartmattress.upgrade.dao.impl;

import co.darma.smartmattress.upgrade.dao.BaseDao;
import co.darma.smartmattress.upgrade.dao.UpgradeRequestDao;
import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import co.darma.smartmattress.upgrade.entity.UpgradeRequestRecord;
import co.darma.smartmattress.util.ArgumentHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/12/29.
 */
public class UpgradeRequestDaoImpl extends BaseDao implements UpgradeRequestDao {

    @Override
    public UpgradeRequest getUpgradeRequest(Integer projectId) {
        List<UpgradeRequest> upgradeRequestList;
        if (projectId == null) {
            upgradeRequestList = getDataAcessor().queryList("queryUpgradeRequest", null);
        } else {
            upgradeRequestList = getDataAcessor().queryList("queryUpgradeRequestByProjectId", projectId);
        }
        if (CollectionUtils.isNotEmpty(upgradeRequestList)) {
            return upgradeRequestList.get(0);
        }
        return null;
    }

    @Override
    public void addNewUpgradeRequest(UpgradeRequest request) {
        getDataAcessor().saveOrUpdateObject("saveUpgraderRequest", request);
    }

    @Override
    public void saveUpgradeRequestRecord(UpgradeRequestRecord requestRecord) {
        getDataAcessor().saveOrUpdateObject("saveUpradeRecord", requestRecord);
    }

    @Override
    public void deleteUpgradeRequstById(Long id) {
        getDataAcessor().deleteObject("deleteUprequest", id);
    }

    @Override
    public boolean deviceHashUpgradeRequestOrNot(Integer deviceId, Long firmwareId) {
        Map map = ArgumentHandleUtil.buildArgument("deviceId", deviceId, "firmwareId", firmwareId);
        return getDataAcessor().existOrNot(IBATIS_PROFIX + "hasUpgradeRequestOrNot", map);
    }
}

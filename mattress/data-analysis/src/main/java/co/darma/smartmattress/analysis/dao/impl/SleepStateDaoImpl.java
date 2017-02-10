package co.darma.smartmattress.analysis.dao.impl;

import co.darma.smartmattress.analysis.dao.SleepStateDao;
import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.database.DataAccess;
import co.darma.smartmattress.util.ArgumentHandleUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by frank on 16/1/4.
 */
public class SleepStateDaoImpl implements SleepStateDao {

    private DataAccess dataAcessor;

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }

    @Override
    public List<SleepState> querySleepState(Integer deviceId, Long lastUpdateTime) {
        return dataAcessor.queryList("querySleepState",
                ArgumentHandleUtil.buildArgument("deviceId", deviceId, "lastUpdateTime", lastUpdateTime));
    }

    @Override
    public void saveOrUpdateSleepSatte(List<SleepState> sleepStates) {
        if (CollectionUtils.isNotEmpty(sleepStates)) {
            dataAcessor.batchSaveObject("batchInsertSleepState", sleepStates);
        }
    }
}

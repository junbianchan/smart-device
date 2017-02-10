package co.darma.smartmattress.analysis.dao.impl;

import co.darma.smartmattress.analysis.dao.MotionForSleepDao;
import co.darma.smartmattress.analysis.entity.BodyMotionForSleep;
import co.darma.smartmattress.database.DataAccess;
import co.darma.smartmattress.util.ArgumentHandleUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by frank on 16/1/5.
 */
public class MotionForSleepDaoImpl implements MotionForSleepDao {

    private DataAccess dataAcessor;

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }

    @Override
    public void saveOrUpdateBodyMotionForSleep(BodyMotionForSleep bodyMotionForSleep) {

        if (bodyMotionForSleep != null) {
            dataAcessor.saveOrUpdateObject("insertBodyMotionForSleep", bodyMotionForSleep);
        }
    }

    @Override
    public List<BodyMotionForSleep> queryBodyMotionForSleep(Integer deviceId, Long startTime, Long endTime) {
        if (deviceId != null && startTime != null && endTime != null) {
            return dataAcessor.queryList("queryBodyMotionForSleep", ArgumentHandleUtil.buildArgument("deviceId", deviceId, "startTime", startTime, "endTime", endTime));
        }
        return null;
    }
}

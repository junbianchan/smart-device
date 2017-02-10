package co.darma.smartmattress.analysis.dao;

import co.darma.smartmattress.analysis.entity.BodyMotion;
import co.darma.smartmattress.analysis.entity.BodyMotionForSleep;

import java.util.List;

/**
 * Created by frank on 16/1/5.
 */
public interface MotionForSleepDao {

    public void saveOrUpdateBodyMotionForSleep(BodyMotionForSleep bodyMotionForSleep);

    public List<BodyMotionForSleep> queryBodyMotionForSleep(Integer deviceId, Long startTime, Long endTime);

}

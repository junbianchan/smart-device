package co.darma.smartmattress.analysis.dao;

import co.darma.smartmattress.analysis.entity.BodyMotion;

import java.util.List;

/**
 * Created by frank on 15/10/23.
 */
public interface BodyMotionDao {

    public void saveOrUpdateBodyMotionList(List<BodyMotion> bodyMotionList);

    public List<BodyMotion> queryBodyMotionWithTime(Integer projectId, Long startTime, Long endTime);

    public BodyMotion queryBodyMotionWithDeviceIdAndTime(Integer deviceId, Long startTime, Long endTime);

}

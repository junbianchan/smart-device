package co.darma.smartmattress.analysis.dao;

import co.darma.smartmattress.analysis.entity.MeddoHealthData;

import java.util.List;

/**
 * Created by frank on 15/11/25.
 */
public interface MeddoHealthDataDao {

    public void saveMeddoHealthData(List<MeddoHealthData> datasList);

    public void saveMeddoHealthData(MeddoHealthData data);

    public List<MeddoHealthData> queryMeddoHealthDataWithTime(Integer projectId, Long startTime, Long endTime);

    public MeddoHealthData queryLastestHealthDataByDevice(Integer deviceId, Long startTime);

    public List<MeddoHealthData> queryHealthDataByDevice(Integer deviceId, Long startTime, Long endTime);

}

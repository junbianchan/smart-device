package co.darma.smartmattress.analysis.dao;

import co.darma.smartmattress.analysis.entity.SleepState;

import java.util.List;

/**
 * Created by frank on 16/1/4.
 */
public interface SleepStateDao {

    public List<SleepState> querySleepState(Integer deviceId, Long lastUpdateTime);

    public void saveOrUpdateSleepSatte(List<SleepState> sleepStates);
}

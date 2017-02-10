package co.darma.smartmattress.analysis.dao;

import co.darma.smartmattress.analysis.entity.ManBehavior;

/**
 * Created by frank on 15/11/4.
 */
public interface ManBehaviorDao {

    public void saveManBehavior(ManBehavior behavior);

    public ManBehavior queryLastestManBehaviorByDevice(Integer deviceId, Long limitTime);

    public ManBehavior queryLastBehavior(Integer deviceId);

}

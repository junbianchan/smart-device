package co.darma.daos;

import co.darma.models.data.BehaviorRecord;
import co.darma.models.data.SedentaryRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/9.
 */
public interface BehaviorDao {

    public List<BehaviorRecord> queryBehaviorRecordByLastUpdateTime(Long memberId,Long lastUpdateTime) throws SQLException;

    public int updateBehaviorRecords(List<BehaviorRecord> records) throws SQLException;

    List<BehaviorRecord> queryBehaviorRecordByTime(Long memberId, Long startTime, Long endTime);
}

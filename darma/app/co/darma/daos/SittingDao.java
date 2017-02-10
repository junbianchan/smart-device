package co.darma.daos;

import co.darma.models.data.SedentaryRecord;
import co.darma.models.data.SittingRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/9.
 */
public interface SittingDao {

    public List<SittingRecord> querySittingRecordByTime(Long memberId, Long startTime, Long endTime);

    public List<SittingRecord> querySittingRecordByLastUpdateTime(Long memberId,Long lastUpdateTime) throws SQLException;

    public int updateSittingRecords(List<SittingRecord> records) throws SQLException;
}

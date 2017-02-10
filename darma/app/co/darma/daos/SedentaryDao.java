package co.darma.daos;

import co.darma.models.data.PhysicalRecord;
import co.darma.models.data.SedentaryRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/9.
 */
public interface SedentaryDao {

    public List<SedentaryRecord> querySedentaryRecordByLastUpdateTime(Long memberId,Long lastUpdateTime) throws SQLException;

    public int updateSedentaryRecords(List<SedentaryRecord> records) throws SQLException;

    List<SedentaryRecord> querySedentaryRecordByTime(Long memberId, Long startTime, Long endTime);
}

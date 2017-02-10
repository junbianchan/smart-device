package co.darma.daos;

import co.darma.models.data.PhysicalRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public interface PhysicalRecordDao {

    public List<PhysicalRecord> queryPhysicalByTime(Long memberId, Long startTime, Long endTime);

    public int updatePhysicalRecord(PhysicalRecord records) throws SQLException;

    public List<PhysicalRecord> queryBehaviorRecordByLastUpdateTime(Long memberId, Long lastUpdateTime) throws SQLException;

    public int updatePhysicalRecords(List<PhysicalRecord> records) throws SQLException;

}

package co.darma.services;

import co.darma.models.data.PhysicalRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public interface PhysicalService {

    public List<PhysicalRecord> queryRecordsbyTime(Long memberId, Long startTime, Long endTime);

    public List<PhysicalRecord> queryAllLastestRecord(Long memberId, Long lastUpdateTime);

    public void updatePhysicalRecord(PhysicalRecord physicalRecord);

    public void updatePhysicalRecordList(List<PhysicalRecord> physicalRecord);
}

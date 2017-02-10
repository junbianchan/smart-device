package co.darma.services;

import co.darma.models.data.PhysicalRecord;
import co.darma.models.data.SedentaryRecord;

import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public interface SedentaryService {

    public List<SedentaryRecord> queryAllLastestRecord(Long memberId, Long lastUpdateTime);

    public void updateSedentaryRecordList(List<SedentaryRecord> physicalRecord);

    List<SedentaryRecord> queryRecordsByTime(Long memberId, Long startTime, Long endTime);
}

package co.darma.services;

import co.darma.models.data.SedentaryRecord;
import co.darma.models.data.SittingRecord;

import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public interface SittingService {

    public List<SittingRecord> queryRecordsByTime(Long memberId,Long startTime, Long endTime);

    public List<SittingRecord> queryAllLastestRecord(Long memberId, Long lastUpdateTime);

    public void updateSittingRecordList(List<SittingRecord> physicalRecord);

}

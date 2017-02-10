package co.darma.services;

import co.darma.controllers.SedentaryController;
import co.darma.models.data.BehaviorRecord;
import co.darma.models.data.SittingRecord;

import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public interface BehaviorService {

    public List<BehaviorRecord> queryAllLastestRecord(Long memberId, Long lastUpdateTime);

    public void updateBehaviorRecordList(List<BehaviorRecord> behaviorRecords);

    List<BehaviorRecord> queryRecordsByTime(Long memberId, Long startTime, Long endTime);

}

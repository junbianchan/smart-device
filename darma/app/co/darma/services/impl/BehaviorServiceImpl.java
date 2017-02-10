package co.darma.services.impl;

import co.darma.daos.BehaviorDao;
import co.darma.daos.implspringjdbc.BehaviorDaoImpl;
import co.darma.exceptions.DatabaseException;
import co.darma.models.data.BehaviorRecord;
import co.darma.models.data.PhysicalRecord;
import co.darma.models.data.SittingRecord;
import co.darma.services.BehaviorService;
import co.darma.services.SedentaryService;
import org.apache.commons.collections.CollectionUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public class BehaviorServiceImpl implements BehaviorService {

    public static final BehaviorService INSTANCE = new BehaviorServiceImpl();

    public static BehaviorDao behaviorDao = BehaviorDaoImpl.INSTANCE;

    @Override
    public List<BehaviorRecord> queryAllLastestRecord(Long memeberId, Long lastUpdateTime) {
        try {
            return behaviorDao.queryBehaviorRecordByLastUpdateTime(memeberId,lastUpdateTime);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updateBehaviorRecordList(List<BehaviorRecord> behaviorRecords) {
        try {
            if (CollectionUtils.isNotEmpty(behaviorRecords)) {
                Long currentTime = System.currentTimeMillis();
                BehaviorRecord.refreshLastUpdateTime(behaviorRecords, currentTime);
            }
            behaviorDao.updateBehaviorRecords(behaviorRecords);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<BehaviorRecord> queryRecordsByTime(Long memberId, Long startTime, Long endTime) {
        return behaviorDao.queryBehaviorRecordByTime(memberId, startTime, endTime);
    }
}

package co.darma.services.impl;

import co.darma.daos.SittingDao;
import co.darma.daos.implspringjdbc.SittingDaoImpl;
import co.darma.exceptions.DatabaseException;
import co.darma.models.data.BehaviorRecord;
import co.darma.models.data.SedentaryRecord;
import co.darma.models.data.SittingChange;
import co.darma.models.data.SittingRecord;
import co.darma.services.SittingService;
import org.apache.commons.collections.CollectionUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public class SittingServiceImpl implements SittingService {

    public static final SittingService INSTANCE = new SittingServiceImpl();

    private SittingDao sittingDao = SittingDaoImpl.INSTANCE;


    @Override
    public List<SittingRecord> queryRecordsByTime(Long memberId, Long startTime, Long endTime) {
        return sittingDao.querySittingRecordByTime(memberId, startTime, endTime);
    }

    @Override
    public List<SittingRecord> queryAllLastestRecord(Long memberId,Long lastUpdateTime) {
        try {
            return sittingDao.querySittingRecordByLastUpdateTime(memberId,lastUpdateTime);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updateSittingRecordList(List<SittingRecord> sittingRecords) {

        try {
            if (CollectionUtils.isNotEmpty(sittingRecords)) {
                Long currentTime = System.currentTimeMillis();
                SittingRecord.refreshLastUpdateTime(sittingRecords, currentTime);
            }
            sittingDao.updateSittingRecords(sittingRecords);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}

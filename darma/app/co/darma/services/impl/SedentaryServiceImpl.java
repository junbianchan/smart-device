package co.darma.services.impl;

import co.darma.daos.SedentaryDao;
import co.darma.daos.implspringjdbc.SedentaryDaoImpl;
import co.darma.exceptions.DatabaseException;
import co.darma.models.data.PhysicalRecord;
import co.darma.models.data.SedentaryRecord;
import co.darma.services.SedentaryService;
import org.apache.commons.collections.CollectionUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public class SedentaryServiceImpl implements SedentaryService {

    public static final SedentaryService INSTANCE = new SedentaryServiceImpl();

    private static SedentaryDao sedentaryDao = SedentaryDaoImpl.INSTANCE;

    @Override
    public List<SedentaryRecord> queryAllLastestRecord(Long memberId, Long lastUpdateTime) {
        try {
            return sedentaryDao.querySedentaryRecordByLastUpdateTime(memberId,lastUpdateTime);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updateSedentaryRecordList(List<SedentaryRecord> sedentaryRecords) {
        try {
            if (CollectionUtils.isNotEmpty(sedentaryRecords)) {
                Long currentTime = System.currentTimeMillis();
                SedentaryRecord.refreshLastUpdateTime(sedentaryRecords, currentTime);
            }
            sedentaryDao.updateSedentaryRecords(sedentaryRecords);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<SedentaryRecord> queryRecordsByTime(Long memberId, Long startTime, Long endTime) {
        return sedentaryDao.querySedentaryRecordByTime(memberId,startTime, endTime);
    }
}

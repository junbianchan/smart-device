package co.darma.services.impl;

import co.darma.daos.PhysicalRecordDao;
import co.darma.daos.implspringjdbc.PhysicalRecordDaoImpl;
import co.darma.exceptions.DatabaseException;
import co.darma.models.data.PhysicalRecord;
import co.darma.services.PhysicalService;
import org.apache.commons.collections.CollectionUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public class PhysicalServiceImpl implements PhysicalService {

    public static final PhysicalService INSTANCE = new PhysicalServiceImpl();

    private PhysicalRecordDao physicalRecordDao = PhysicalRecordDaoImpl.INSTANCE;


    @Override
    public List<PhysicalRecord> queryRecordsbyTime(Long memberId, Long startTime, Long endTime) {
        return physicalRecordDao.queryPhysicalByTime(memberId, startTime, endTime);

    }

    @Override
    public List<PhysicalRecord> queryAllLastestRecord(Long memberId, Long lastUpdateTime) {
        try {
            return physicalRecordDao.queryBehaviorRecordByLastUpdateTime(memberId, lastUpdateTime);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updatePhysicalRecord(PhysicalRecord physicalRecord) {
        try {
            physicalRecord.setLastUpdateTime(System.currentTimeMillis());
            physicalRecordDao.updatePhysicalRecord(physicalRecord);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updatePhysicalRecordList(List<PhysicalRecord> physicalRecords) {
        try {
            if (CollectionUtils.isNotEmpty(physicalRecords)) {
                Long currentTime = System.currentTimeMillis();
                PhysicalRecord.refreshLastUpdateTime(physicalRecords, currentTime);
            }
            physicalRecordDao.updatePhysicalRecords(physicalRecords);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


}

package co.darma.daos.implspringjdbc.rowmappers;

import co.darma.models.data.BehaviorRecord;
import co.darma.models.data.PhysicalRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by frank on 15/12/9.
 */
public class BehaviorRecordMapper implements RowMapper<BehaviorRecord> {


    @Override
    public BehaviorRecord mapRow(ResultSet resultSet, int i) throws SQLException {
        BehaviorRecord record = new BehaviorRecord();
        record.setMemberId((Long) resultSet.getLong("member_id"));
        record.setMarkTime((Long) resultSet.getLong("start_time"));
        record.setStandTimes((Integer) resultSet.getInt("stand_times"));
        record.setOpenAppTimes((Integer) resultSet.getInt("open_app_times"));
        record.setLastStretchTime((Long) resultSet.getLong("last_stretch_times"));
        record.setFinishStretchTimes((Integer) resultSet.getInt("finish_stretch_times"));
        record.setSawStretchTimes((Integer) resultSet.getInt("saw_stretch_times"));
        record.setLastUpdateTime((Long) resultSet.getLong("last_update_time"));
        return record;
    }
}

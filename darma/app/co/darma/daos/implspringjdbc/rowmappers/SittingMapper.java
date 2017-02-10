package co.darma.daos.implspringjdbc.rowmappers;

import co.darma.models.data.SedentaryRecord;
import co.darma.models.data.SittingChange;
import co.darma.models.data.SittingRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by frank on 15/12/9.
 */
public class SittingMapper implements RowMapper<SittingRecord> {
    @Override
    public SittingRecord mapRow(ResultSet resultSet, int i) throws SQLException {

        SittingRecord record = new SittingRecord();

        record.setMemberId(resultSet.getLong("member_id"));
        record.setStartTime(resultSet.getLong("start_time"));
        record.setEndTime(resultSet.getLong("end_time"));
        record.setLastUpdateTime(resultSet.getLong("last_update_time"));
        record.setPostureType(resultSet.getInt("posture_type"));
        return record;

    }
}

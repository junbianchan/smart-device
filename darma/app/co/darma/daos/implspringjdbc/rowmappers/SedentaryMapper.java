package co.darma.daos.implspringjdbc.rowmappers;

import co.darma.models.data.PhysicalRecord;
import co.darma.models.data.SedentaryRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by frank on 15/12/9.
 */
public class SedentaryMapper implements RowMapper<SedentaryRecord> {
    @Override
    public SedentaryRecord mapRow(ResultSet resultSet, int i) throws SQLException {

        SedentaryRecord record = new SedentaryRecord();

        record.setMemberId((Long) resultSet.getLong("member_id"));
        record.setStartTime((Long)resultSet.getLong("start_time"));
        record.setEndTime((Long)resultSet.getLong("end_time"));
        record.setUnSitTime((Integer)resultSet.getInt("unsit_time"));

        return record;
    }
}

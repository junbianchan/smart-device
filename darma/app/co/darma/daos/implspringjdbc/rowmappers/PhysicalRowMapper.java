package co.darma.daos.implspringjdbc.rowmappers;

import co.darma.models.data.MemberProfile;
import co.darma.models.data.PhysicalRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by frank on 15/12/9.
 */
public class PhysicalRowMapper implements RowMapper<PhysicalRecord> {

    @Override
    public PhysicalRecord mapRow(ResultSet resultSet, int i) throws SQLException {

        PhysicalRecord record = new PhysicalRecord();
        record.setMemberId(resultSet.getLong("member_id"));
        record.setStartTime(resultSet.getLong("start_time"));
        record.setHeartRate(resultSet.getInt("heart_rate"));
        record.setHeartRateWeight(resultSet.getInt("heart_rate_weight"));
        record.setRespirationValue(resultSet.getInt("respiration_value"));
        record.setRespirationWeight(resultSet.getInt("respiration_weight"));
        record.setStress(resultSet.getInt("stress"));
        record.setStressWeight(resultSet.getInt("stress_weight"));
        return record;
    }
}

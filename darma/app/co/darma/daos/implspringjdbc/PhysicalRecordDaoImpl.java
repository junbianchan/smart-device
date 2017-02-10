package co.darma.daos.implspringjdbc;

import co.darma.constant.DatabaseConstant;
import co.darma.daos.PhysicalRecordDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.PhysicalRowMapper;
import co.darma.models.data.PhysicalRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import play.Logger;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public class PhysicalRecordDaoImpl implements PhysicalRecordDao {

    public static PhysicalRecordDao INSTANCE = new PhysicalRecordDaoImpl();

    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    private static final String UPDATE_SQL_FIRST_HALF = "INSERT INTO t_physical_records (member_id ,start_time ,heart_rate ,heart_rate_weight, respiration_value, respiration_weight,stress ,stress_weight ,last_update_time) ";

    private static final String UPDATE_SQL_SECOND_HALF =
            " on DUPLICATE KEY UPDATE " +
                    "member_id = VALUES(member_id) " +
                    ",start_time = VALUES(start_time) " +
                    ",heart_rate = VALUES(heart_rate) " +
                    ",heart_rate_weight = VALUES(heart_rate_weight) " +
                    ",respiration_value = VALUES(respiration_value) " +
                    ",respiration_weight = VALUES(respiration_weight) " +
                    ",stress = VALUES(stress) " +
                    ",stress_weight = VALUES(stress_weight) " +
                    ",last_update_time = VALUES(last_update_time) ";


    private static final String ARGUMENT = " (?,?,?,?,?,?,?,?,?) ";

    private static final String SINGLE_UPDATE_SQL = UPDATE_SQL_FIRST_HALF
            + DatabaseConstant.VALUES
            + ARGUMENT
            + UPDATE_SQL_SECOND_HALF;


    @Override
    public List<PhysicalRecord> queryPhysicalByTime(Long memberId, Long startTime, Long endTime) {
        String sql = " select member_id ,start_time ,heart_rate ,heart_rate_weight, respiration_value, respiration_weight,stress ,stress_weight " +
                " from t_physical_records t where t.member_id = ? and t.start_time >= ? and t.start_time <= ?";

        return t.query(sql, new Object[]{memberId, startTime, endTime}, new PhysicalRowMapper());

    }

    @Override
    public int updatePhysicalRecord(PhysicalRecord physicalRecord) throws SQLException {

        Object[] params = new Object[]{
                physicalRecord.getMemberId(),
                physicalRecord.getStartTime(),
                physicalRecord.getHeartRate(),
                physicalRecord.getHeartRateWeight(),
                physicalRecord.getRespirationValue(),
                physicalRecord.getRespirationWeight(),
                physicalRecord.getStress(),
                physicalRecord.getStressWeight(),
                physicalRecord.getLastUpdateTime()
        };
        return t.update(SINGLE_UPDATE_SQL, params);
    }

    @Override
    public List<PhysicalRecord> queryBehaviorRecordByLastUpdateTime(Long memberId, Long lastUpdateTime) throws SQLException {
        String sql = "select member_id ,start_time ,heart_rate ,heart_rate_weight, respiration_value, respiration_weight,stress ,stress_weight " +
                " from t_physical_records t where t.member_id = ? and t.last_update_time > ?";
        Object[] params = new Object[]{memberId,lastUpdateTime};
        return t.query(sql, params, new PhysicalRowMapper());
    }

    @Override
    public int updatePhysicalRecords(List<PhysicalRecord> records) throws SQLException {
        StringBuffer batch_update_sql = new StringBuffer();

        batch_update_sql.append(UPDATE_SQL_FIRST_HALF);
        batch_update_sql.append(DatabaseConstant.VALUES);
        for (PhysicalRecord record : records) {
            batch_update_sql.append(ARGUMENT).append(",");
        }

        batch_update_sql.deleteCharAt(batch_update_sql.length() - 1);
        batch_update_sql.append(UPDATE_SQL_SECOND_HALF);

        Object[] params = new Object[records.size() * 9];

        PhysicalRecord r = null;
        for (int i = 0; i < records.size(); ++i) {
            r = records.get(i);
            params[i * 9 + 0] = r.getMemberId();
            params[i * 9 + 1] = r.getStartTime();
            params[i * 9 + 2] = r.getHeartRate();
            params[i * 9 + 3] = r.getHeartRateWeight();
            params[i * 9 + 4] = r.getRespirationValue();
            params[i * 9 + 5] = r.getRespirationWeight();
            params[i * 9 + 6] = r.getStress();
            params[i * 9 + 7] = r.getStressWeight();
            params[i * 9 + 8] = r.getLastUpdateTime();
        }

//        for (int i = 0; i < params.length; ++i) {
//            Logger.info("i is :" + i + "param[i]:" + params[i]);
//        }
//        Logger.info("physical update sql :" + batch_update_sql);
        return t.update(batch_update_sql.toString(), params);
    }

//    public static void main(String[] args) throws SQLException {
//        PhysicalRecordDaoImpl dao = new PhysicalRecordDaoImpl();
//        List<PhysicalRecord> records = new LinkedList<PhysicalRecord>();
//        PhysicalRecord record = new PhysicalRecord(null);
//        record.setMemberId(123);
//        record.setStartTime(1449651361);
//        record.setHeartRate(78);
//        record.setHeartRateWeight(60);
//        record.setRespirationValue(12);
//        record.setRespirationWeight(60);
//        record.setStress(12);
//        record.setStressWeight(60);
//        records.add(record);
//        dao.updatePhysicalRecords(records);
//    }
}

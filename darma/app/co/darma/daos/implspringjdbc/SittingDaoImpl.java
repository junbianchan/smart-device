package co.darma.daos.implspringjdbc;

import co.darma.constant.DatabaseConstant;
import co.darma.daos.SittingDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.SittingMapper;
import co.darma.models.data.SittingRecord;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/9.
 */
public class SittingDaoImpl implements SittingDao {


    public static final SittingDao INSTANCE = new SittingDaoImpl();

    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    private static final String UPDATE_SQL_FIRST_HALF =
            "INSERT INTO t_sitting_records (member_id ,start_time , end_time,posture_type, last_update_time) ";

    private static final String UPDATE_SQL_SECOND_HALF =
            " on DUPLICATE KEY UPDATE " +
                    "member_id = VALUES(member_id) " +
                    ",start_time = VALUES(start_time) " +
                    ",end_time = VALUES(end_time) " +
                    ",posture_type = VALUES(posture_type) " +
                    ",last_update_time = VALUES(last_update_time) ";

    private static final String ARGUMENT = " (?,?,?,?,?) ";


    @Override
    public List<SittingRecord> querySittingRecordByTime(Long memberId, Long startTime, Long endTime) {
        String sql = "select member_id ,start_time , end_time,posture_type, last_update_time from t_sitting_records t where t.member_id = ? and  t.start_time >= ? " +
                " and t.start_time <= ?";
        return t.query(sql, new Object[]{memberId, startTime, endTime}, new SittingMapper());
    }

    @Override
    public List<SittingRecord> querySittingRecordByLastUpdateTime(Long memberId, Long lastUpdateTime) throws SQLException {

        String sql = "select member_id ,start_time , end_time,posture_type, last_update_time from t_sitting_records t where t.member_id = ? and t.last_update_time > ? ";
        return t.query(sql, new Object[]{memberId, lastUpdateTime}, new SittingMapper());
    }

    @Override
    public int updateSittingRecords(List<SittingRecord> records) throws SQLException {
        StringBuffer batch_update_sql = new StringBuffer();

        batch_update_sql.append(UPDATE_SQL_FIRST_HALF);
        batch_update_sql.append(DatabaseConstant.VALUES);
        for (SittingRecord record : records) {
            batch_update_sql.append(ARGUMENT).append(",");
        }
        batch_update_sql.deleteCharAt(batch_update_sql.length() - 1);
        batch_update_sql.append(UPDATE_SQL_SECOND_HALF);

        Object[] params = new Object[records.size() * 5];

        SittingRecord r = null;
        for (int i = 0; i < records.size(); ++i) {
            r = records.get(i);

            params[i * 5 + 0] = r.getMemberId();
            params[i * 5 + 1] = r.getStartTime();
            params[i * 5 + 2] = r.getEndTime();
            params[i * 5 + 3] = r.getPostureType();
            params[i * 5 + 4] = r.getLastUpdateTime();
        }
        return t.update(batch_update_sql.toString(), params);
    }
}

package co.darma.daos.implspringjdbc;

import co.darma.constant.DatabaseConstant;
import co.darma.daos.SedentaryDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.PhysicalRowMapper;
import co.darma.daos.implspringjdbc.rowmappers.SedentaryMapper;
import co.darma.models.data.PhysicalRecord;
import co.darma.models.data.SedentaryRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import play.Logger;


import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/9.
 */
public class SedentaryDaoImpl implements SedentaryDao {

    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    private static final String UPDATE_SQL_FIRST_HALF = "INSERT INTO t_sedentary_records (member_id ,start_time , end_time, unsit_time, last_update_time) ";

    private static final String UPDATE_SQL_SECOND_HALF =
            " on DUPLICATE KEY UPDATE " +
                    "member_id = VALUES(member_id) " +
                    ",start_time = VALUES(start_time) " +
                    ",end_time = VALUES(end_time) " +
                    ",unsit_time = VALUES(unsit_time) " +
                    ",last_update_time = VALUES(last_update_time) ";


    private static final String ARGUMENT = " (?,?,?,?,?) ";


    public static final SedentaryDao INSTANCE = new SedentaryDaoImpl();

    @Override
    public List<SedentaryRecord> querySedentaryRecordByLastUpdateTime(Long memberId,Long lastUpdateTime) throws SQLException {


        String sql = "select member_id ,start_time , end_time, unsit_time from t_sedentary_records t where t.member_id = ? and t.last_update_time > ? ";
        return t.query(sql, new Object[]{memberId,lastUpdateTime}, new SedentaryMapper());
    }

    @Override
    public int updateSedentaryRecords(List<SedentaryRecord> records) throws SQLException {

        StringBuffer batch_update_sql = new StringBuffer();

        batch_update_sql.append(UPDATE_SQL_FIRST_HALF);
        batch_update_sql.append(DatabaseConstant.VALUES);
        for (SedentaryRecord record : records) {
            batch_update_sql.append(ARGUMENT).append(",");
        }
        batch_update_sql.deleteCharAt(batch_update_sql.length() - 1);
        batch_update_sql.append(UPDATE_SQL_SECOND_HALF);

        Object[] params = new Object[records.size() * 5];

        SedentaryRecord r = null;
        for (int i = 0; i < records.size(); ++i) {
            r = records.get(i);
            Logger.info("record is :" + r);

            params[i * 5 + 0] = r.getMemberId();
            params[i * 5 + 1] = r.getStartTime();
            params[i * 5 + 2] = r.getEndTime();
            params[i * 5 + 3] = r.getUnSitTime();
            params[i * 5 + 4] = r.getLastUpdateTime();
        }
        return t.update(batch_update_sql.toString(), params);
    }

    @Override
    public List<SedentaryRecord> querySedentaryRecordByTime(Long memberId, Long startTime, Long endTime) {
        String sql = "select member_id ,start_time , end_time, unsit_time from t_sedentary_records t where t.member_id = ? and t.start_time >= ? " +
                "and t.start_time <= ? ";
        return t.query(sql, new Object[]{memberId, startTime, endTime}, new SedentaryMapper());
    }
}

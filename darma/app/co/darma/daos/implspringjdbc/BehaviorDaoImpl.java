package co.darma.daos.implspringjdbc;

import co.darma.constant.DatabaseConstant;
import co.darma.daos.BehaviorDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.BehaviorRecordMapper;
import co.darma.models.data.BehaviorRecord;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by frank on 15/12/9.
 */
public class BehaviorDaoImpl implements BehaviorDao {


    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    private static final String UPDATE_SQL_FIRST_HALF = "INSERT INTO t_behavior_records" +
            " (member_id ,start_time , stand_times, finish_stretch_times,last_stretch_times,open_app_times,saw_stretch_times, last_update_time," +
            "connecttime ,remindsitstand ,remindsitnotstand ,sendstretchcount ,tapmain ,tapexercise ,tapdailyreport ,tapvitalsign ,tapsl ,taphr ,tapbr ,tapsetting ,tapgoal ) ";

    private static final String UPDATE_SQL_SECOND_HALF =
            " on DUPLICATE KEY UPDATE " +
                    "member_id = VALUES(member_id) " +
                    ",start_time = VALUES(start_time) " +
                    ",stand_times = VALUES(stand_times) " +
                    ",finish_stretch_times = VALUES(finish_stretch_times) " +
                    ",last_stretch_times = VALUES(last_stretch_times) " +
                    ",open_app_times = VALUES(open_app_times) " +
                    ",saw_stretch_times = VALUES(saw_stretch_times) " +
                    ",last_update_time = VALUES(last_update_time) ," +
                    "connecttime = VAlUES(connecttime)," +
                    "remindsitstand = VAlUES(remindsitstand)," +
                    "remindsitnotstand = VAlUES(remindsitnotstand)," +
                    "sendstretchcount = VAlUES(sendstretchcount)," +
                    "tapmain = VAlUES(tapmain)," +
                    "tapexercise = VAlUES(tapexercise)," +
                    "tapdailyreport = VAlUES(tapdailyreport)," +
                    "tapvitalsign = VAlUES(tapvitalsign)," +
                    "tapsl = VAlUES(tapsl)," +
                    "taphr = VAlUES(taphr)," +
                    "tapbr = VAlUES(tapbr)," +
                    "tapsetting  = VAlUES(tapsetting)," +
                    "tapgoal= VAlUES(tapgoal) ";


    private static final String ARGUMENT = " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


    public static final BehaviorDao INSTANCE = new BehaviorDaoImpl();

    @Override
    public List<BehaviorRecord> queryBehaviorRecordByLastUpdateTime(Long memberId, Long lastUpdateTime) throws SQLException {

        String sql = "select member_id ," +
                "start_time , " +
                "stand_times, " +
                "finish_stretch_times," +
                "last_stretch_times," +
                "open_app_times," +
                "saw_stretch_times," +
                " last_update_time" +
                " from t_behavior_records t where t.member_id = ? and t.last_update_time > ? ";
        return t.query(sql, new Object[]{memberId, lastUpdateTime}, new BehaviorRecordMapper());

    }

    @Override
    public int updateBehaviorRecords(List<BehaviorRecord> records) throws SQLException {
        StringBuffer batch_update_sql = new StringBuffer();

        batch_update_sql.append(UPDATE_SQL_FIRST_HALF);
        batch_update_sql.append(DatabaseConstant.VALUES);
        for (BehaviorRecord record : records) {
            batch_update_sql.append(ARGUMENT).append(" ,");
        }
        batch_update_sql.deleteCharAt(batch_update_sql.length() - 1);
        batch_update_sql.append(UPDATE_SQL_SECOND_HALF);

        Object[] params = new Object[records.size() * 21];

        BehaviorRecord r = null;
        for (int i = 0; i < records.size(); ++i) {
            r = records.get(i);
            params[i * 21 + 0] = r.getMemberId();
            params[i * 21 + 1] = r.getMarkTime();
            params[i * 21 + 2] = r.getStandTimes();
            params[i * 21 + 3] = r.getFinishStretchTimes();
            params[i * 21 + 4] = r.getLastStretchTime();
            params[i * 21 + 5] = r.getOpenAppTimes();
            params[i * 21 + 6] = r.getSawStretchTimes();
            params[i * 21 + 7] = r.getLastUpdateTime();
            params[i * 21 + 8] = r.getConnectTime();
            params[i * 21 + 9] = r.getRemindSitStand();
            params[i * 21 + 10] = r.getRemindSitNotStand();
            params[i * 21 + 11] = r.getSendStretchCount();
            params[i * 21 + 12] = r.getTapMain();
            params[i * 21 + 13] = r.getTapExercise();
            params[i * 21 + 14] = r.getTapDailyReport();
            params[i * 21 + 15] = r.getTapVitalSign();
            params[i * 21 + 16] = r.getTapSl();
            params[i * 21 + 17] = r.getTapHr();
            params[i * 21 + 18] = r.getTapBr();
            params[i * 21 + 19] = r.getTapSetting();
            params[i * 21 + 20] = r.getTapGoal();
        }
        return t.update(batch_update_sql.toString(), params);
    }

    @Override
    public List<BehaviorRecord> queryBehaviorRecordByTime(Long memberId, Long startTime, Long endTime) {
        String sql = "select member_id ,start_time , stand_times, finish_stretch_times,last_stretch_times,open_app_times,saw_stretch_times, last_update_time" +
                " from t_behavior_records t where t.member_id = ? and t.start_time >= ? and t.start_time <=? ";
        return t.query(sql, new Object[]{memberId, startTime, endTime}, new BehaviorRecordMapper());
    }

}

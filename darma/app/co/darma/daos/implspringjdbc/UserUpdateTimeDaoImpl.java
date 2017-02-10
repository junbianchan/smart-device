package co.darma.daos.implspringjdbc;

import co.darma.daos.UserUpdateTimeDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.SittingMapper;
import co.darma.daos.implspringjdbc.rowmappers.UserUpdateTimeMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by frank on 15/12/10.
 */
public class UserUpdateTimeDaoImpl implements UserUpdateTimeDao {

    public static final UserUpdateTimeDao INSTANCE = new UserUpdateTimeDaoImpl();

    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    @Override
    public Long queryUserLastUpdateTime(long memberId) {
        String sql = "select member_id , last_update_time from v_user_udpate_time t where t.member_id = ? ";
        List<Long> times = t.query(sql, new Object[]{memberId}, new UserUpdateTimeMapper());

        if (times.size() > 0) {
            return times.get(0);
        }
        return 0L;
    }
}

package co.darma.daos.implspringjdbc.rowmappers;

import co.darma.models.data.ThirdPartyMember;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by frank on 15/12/10.
 */
public class UserUpdateTimeMapper implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getLong("last_update_time");
    }
}

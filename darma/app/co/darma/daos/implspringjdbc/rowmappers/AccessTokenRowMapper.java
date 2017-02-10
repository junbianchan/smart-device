package co.darma.daos.implspringjdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccessTokenRowMapper implements RowMapper<String>
{
	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getString("access_token");
	}
	
}

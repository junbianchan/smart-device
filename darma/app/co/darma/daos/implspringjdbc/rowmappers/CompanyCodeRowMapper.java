package co.darma.daos.implspringjdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompanyCodeRowMapper implements RowMapper<Long>
{
	@Override
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getLong("member_id");
	}
	
}

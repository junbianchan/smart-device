package co.darma.daos.implspringjdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.darma.models.data.Member;

public class MemberRowMapper implements RowMapper<Member>
{
	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member credential = new Member();
		credential.id = rs.getLong("id");
		credential.email = rs.getString("email");
		credential.password = rs.getString("password");
		return credential;
	}
	
}

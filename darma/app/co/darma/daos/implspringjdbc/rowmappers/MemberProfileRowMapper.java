package co.darma.daos.implspringjdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.darma.models.data.MemberProfile;

public class MemberProfileRowMapper implements RowMapper<MemberProfile> {
	@Override
	public MemberProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberProfile p = new MemberProfile();
		p.firstName = rs.getString("first_name");
		p.lastName = rs.getString("last_name");
		p.gender = rs.getString("gender");
		p.weight = rs.getInt("weight");
		p.height = rs.getInt("height");
		p.birthday = rs.getLong("birthday");
		p.imgLarge = rs.getString("img_large");
		p.imgMedium = rs.getString("img_medium");
		p.imgSmall = rs.getString("img_small");
		p.memberId = rs.getLong("member_id");
		p.weightkg = rs.getInt("weight_kg");
		p.weightlbs = rs.getInt("weight_lbs");
		p.heightcm = rs.getInt("height_cm");
		p.heightinch = rs.getInt("height_inc");
		return p;
	}
}

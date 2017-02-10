package co.darma.daos.implspringjdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import play.Logger;
import co.darma.exceptions.InvalidParameterException;
import co.darma.models.data.ThirdParty;
import co.darma.models.data.ThirdPartyMember;

public class ThirdPartyMemberRowMapper implements RowMapper<ThirdPartyMember> {

	@Override
	public ThirdPartyMember mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ThirdPartyMember m = new ThirdPartyMember();
		m.memberId = rs.getLong("member_id");
		m.thirdPartyId = rs.getString("third_party_id");
		String thridParty = rs.getString("third_party");
		try {
			m.thirdParty = ThirdParty.fromDB(thridParty);
			return m;
		} catch (InvalidParameterException e) {
			Logger.error(
					"Invalid value encoutered while trying to fetch third party member '"
							+ thridParty + "'", e);
			return null;
		}
	}

}

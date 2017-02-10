package co.darma.daos.implspringjdbc;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import co.darma.daos.ThirdPartyMemberDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.ThirdPartyMemberRowMapper;
import co.darma.exceptions.InvalidParameterException;
import co.darma.models.data.ThirdParty;
import co.darma.models.data.ThirdPartyMember;

public class ThirdPartyMemberDaoImpl implements ThirdPartyMemberDao {

	private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

	public static ThirdPartyMemberDao createInstance() {
		return new ThirdPartyMemberDaoImpl();
	}

	@Override
	public ThirdPartyMember getThirdPartyMember(String id, ThirdParty party)
			throws SQLException, InvalidParameterException {
		String sql = "SELECT * FROM third_party_members WHERE third_party_id = ? and third_party = ?";
		Object[] params = new Object[] { id, party.value() };
		List<ThirdPartyMember> members = t.query(sql, params,
				new ThirdPartyMemberRowMapper());
		if (members.size() == 1) {
			return members.get(0);
		}
		return null;
	}

	@Override
	public int createThirdPartyMember(Long memberId, String id, ThirdParty party)
			throws SQLException {
		String sql = "INSERT INTO third_party_members (member_id, third_party_id, third_party) VALUES (?, ?, ?)";
		Object[] params = new Object[] { memberId, id, party.value() };
		return t.update(sql, params);
	}

}

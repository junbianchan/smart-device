package co.darma.daos;

import java.sql.SQLException;

import co.darma.exceptions.InvalidParameterException;
import co.darma.models.data.ThirdParty;
import co.darma.models.data.ThirdPartyMember;

public interface ThirdPartyMemberDao {

	public ThirdPartyMember getThirdPartyMember(String id, ThirdParty party)
			throws SQLException, InvalidParameterException;

	public int createThirdPartyMember(Long memberId, String id, ThirdParty party)
			throws SQLException;

}

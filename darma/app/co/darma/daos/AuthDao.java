package co.darma.daos;

import java.sql.SQLException;

import co.darma.models.data.Member;

public interface AuthDao {
	public int createAccessToken(Long memberId, Long updateTime, String newAccessToken, String tokenTypen) throws SQLException;
	
	public int deleteAccessToken(Long memberId) throws SQLException;
	
	public int updateAccessToken(Long memberId, Long updateTime, String newAccessToken, String tokenType) throws SQLException;
	
	public Long createCredential(String email, String password) throws SQLException;
	
	public int updatePassword(Long memberId, String password) throws SQLException;
	
	public Member getMemberById(Long memberId) throws SQLException;
	
	public Member getMemberByEmail(String email) throws SQLException;
	
	public String accessTokenByMemberId(Long memberId) throws SQLException;
	
	public Long findCompanyCodeMember(String code);
	
	public void updateCompanyCodeMemberId(long memberId, String companyCode);
}

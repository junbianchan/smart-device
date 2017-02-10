package co.darma.daos.implspringjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import co.darma.daos.AuthDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.AccessTokenRowMapper;
import co.darma.daos.implspringjdbc.rowmappers.CompanyCodeRowMapper;
import co.darma.daos.implspringjdbc.rowmappers.MemberRowMapper;
import co.darma.models.data.Member;
import play.Logger;

public class AuthDaoImpl implements AuthDao {

    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    public static AuthDao createInstance() {
        return new AuthDaoImpl();
    }

    @Override
    public int createAccessToken(Long memberId, Long updateTime, String accessToken, String tokenType) {
        String sql = "INSERT INTO access_tokens (member_id,UPDATE_TIME, access_token,token_type) VALUES (?,?,?,?)";
        Object[] params = new Object[]{memberId, updateTime, accessToken, tokenType};
        return t.update(sql, params);
    }

    @Override
    public int deleteAccessToken(Long memberId) {
        String sql = "DELETE FROM access_tokens WHERE member_id = ?";
        Object[] params = new Object[]{memberId};
        return t.update(sql, params);
    }

    @Override
    public int updateAccessToken(Long memberId, Long updateTime, String newAccessToken, String tokenType) {

        String sql = "UPDATE access_tokens SET access_token = ?,UPDATE_TIME = ? WHERE member_id = ? ";
        Object[] params = null;
        if (!StringUtils.isEmpty(tokenType)) {
            sql += " and token_type = ? ";
            params = new Object[]{newAccessToken, updateTime, memberId, tokenType};
        } else {
            params = new Object[]{newAccessToken, updateTime, memberId};
        }
        Logger.info("update Token sql is :" + sql);

        return t.update(sql, params);
    }

    @Override
    public Long createCredential(String email, String password) {
        final String sql = "INSERT INTO MEMBERS (email, password) VALUES (?, ?)";
        final String emailParam = email;
        final String passwordParam = password;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        t.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(
                    Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,
                        new String[]{"id"});
                ps.setString(1, emailParam);
                ps.setString(2, passwordParam);
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int updatePassword(Long memberId, String password) {
        String sql = "UPDATE members SET password = ? WHERE id = ?";
        Object[] params = new Object[]{password, memberId};
        return t.update(sql, params);
    }

    @Override
    public Member getMemberById(Long memberId) throws SQLException {
        String sql = "SELECT * FROM members WHERE id = ?";
        Object[] params = new Object[]{memberId};
        List<Member> members = t.query(sql, params, new MemberRowMapper());
        if (members.size() == 1) {
            return members.get(0);
        }
        return null;
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        Object[] params = new Object[]{email};
        List<Member> members = t.query(sql, params, new MemberRowMapper());
        if (members.size() == 1) {
            return members.get(0);
        }
        return null;
    }

    @Override
    public String accessTokenByMemberId(Long memberId) {
        String sql = "SELECT access_token FROM access_tokens WHERE member_id = ?";
        Object[] params = new Object[]{memberId};
        List<String> tokens = t.query(sql, params, new AccessTokenRowMapper());
        if (tokens.size() == 1) {
            return tokens.get(0);
        }
        return null;
    }

    @Override
    public Long findCompanyCodeMember(String code) {
        String sql = "SELECT member_id FROM company_codes WHERE company_code = ?";
        Object[] params = new Object[]{code};
        List<Long> memberIds = t.query(sql, params, new CompanyCodeRowMapper());
        if (memberIds.size() == 1) {
            return memberIds.get(0);
        }
        return null;
    }

    @Override
    public void updateCompanyCodeMemberId(long memberId, String companyCode) {
        String sql = "UPDATE company_codes SET member_id = ? WHERE company_code = ?";
        Object[] params = new Object[]{memberId, companyCode};
        t.update(sql, params);
    }
}

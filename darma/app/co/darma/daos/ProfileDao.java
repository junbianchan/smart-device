package co.darma.daos;

import co.darma.models.data.MemberProfile;

import java.sql.SQLException;

public interface ProfileDao {

    public MemberProfile getProfileByMemberId(Long memberId) throws SQLException;

    /**
     * 保存或者更新Profile
     *
     * @param profile
     * @return
     * @throws SQLException
     */
    public int createOrUpdateProfile(MemberProfile profile) throws SQLException;

    public int updateProfile(MemberProfile profile) throws SQLException;

}

package co.darma.services;

import co.darma.models.data.MemberProfile;

public interface MemberService {
    /**
     * 创建Profile
     *
     * @param profile
     * @throws Exception
     */
    @Deprecated
    public void createProfile(MemberProfile profile) throws Exception;

    public MemberProfile getProfileByMemberId(Long memberId) throws Exception;

    public void updateProfile(MemberProfile profile) throws Exception;
}

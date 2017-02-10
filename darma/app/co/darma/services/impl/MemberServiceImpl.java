package co.darma.services.impl;

import co.darma.daos.ProfileDao;
import co.darma.daos.implspringjdbc.ProfileDaoImpl;
import co.darma.models.data.MemberProfile;
import co.darma.services.MemberService;

public class MemberServiceImpl implements MemberService {

    private ProfileDao memberDao = ProfileDaoImpl.createInstance();

    public static MemberService createInstance() {
        return new MemberServiceImpl();
    }

    @Deprecated
    @Override
    public void createProfile(MemberProfile profile) throws Exception {
        memberDao.createOrUpdateProfile(profile);
    }

    @Override
    public void updateProfile(MemberProfile profile) throws Exception {
        memberDao.createOrUpdateProfile(profile);
    }

    @Override
    public MemberProfile getProfileByMemberId(Long memberId) throws Exception {
        return memberDao.getProfileByMemberId(memberId);
    }


}

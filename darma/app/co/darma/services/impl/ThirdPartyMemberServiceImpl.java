package co.darma.services.impl;

import co.darma.common.DefaultDataGenerator;
import co.darma.daos.ThirdPartyMemberDao;
import co.darma.daos.implspringjdbc.ThirdPartyMemberDaoImpl;
import co.darma.exceptions.InvalidThirdPartyAuthTokenException;
import co.darma.models.data.MemberProfile;
import co.darma.models.data.ThirdParty;
import co.darma.models.data.ThirdPartyMember;
import co.darma.models.returns.SignInResult;
import co.darma.services.AuthService;
import co.darma.services.MemberService;
import co.darma.services.ThirdPartyAuthTokenService;
import co.darma.services.ThirdPartyMemberService;
import play.Logger;

public class ThirdPartyMemberServiceImpl implements ThirdPartyMemberService {

    private AuthService authSvc = AuthServiceImpl.createInstance();
    private ThirdPartyAuthTokenService tokenSvc = ThirdPartyAuthTOkenServiceImpl
            .createInstance();

    private MemberService memberSvc = MemberServiceImpl.createInstance();

    private ThirdPartyMemberDao dao = ThirdPartyMemberDaoImpl.createInstance();

    public static ThirdPartyMemberService createInstance() {
        return new ThirdPartyMemberServiceImpl();
    }

    @Override
    public SignInResult signIn(String thirdPartyId, ThirdParty thirdParty,
                               String authToken, String agent) throws Exception {

        if (!tokenSvc.isTokenValid(authToken, thirdPartyId, thirdParty)) {
            throw new InvalidThirdPartyAuthTokenException("Provided "
                    + thirdParty.toString() + " auth token " + authToken
                    + " is invalid");
        }
        ThirdPartyMember member = dao.getThirdPartyMember(thirdPartyId,
                thirdParty);
        // This member already exists
        Logger.info("ThirdPartyMember is :" + member);
        if (member != null) {
            String accessToken = authSvc.refreshAccessToken(member.memberId, agent);
            MemberProfile profile = memberSvc.getProfileByMemberId(member.memberId);
            //判断是否是新成员
            boolean isNewMemeber = (profile == null);
            return new SignInResult(member.memberId, accessToken, isNewMemeber);
        } else {
            String email = DefaultDataGenerator.generateRandomEmail();
            String password = DefaultDataGenerator.generateRandomPassword();
            SignInResult result = authSvc.signUp(email, password, agent);
            dao.createThirdPartyMember(result.memberId, thirdPartyId,
                    thirdParty);
            return result;
        }
    }
}

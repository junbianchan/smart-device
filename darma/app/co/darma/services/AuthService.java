package co.darma.services;

import co.darma.models.data.Member;
import co.darma.models.returns.SignInResult;

public interface AuthService {
    public Long verifyAccessToken(String accessToken) throws Exception;

    public String refreshAccessToken(Long member,String agent) throws Exception;

    public SignInResult signUp(String email, String password, String agent) throws Exception;

    public SignInResult signIn(String email, String password, String agent) throws Exception;

    public int signOut(Long memberId, String agent) throws Exception;

    public int updatePassword(Long memberId, String oldPassword, String newPassword) throws Exception;

    public void resetPassword(Long memberId, String password) throws Exception;

    public Member getMemberFromEmail(String email) throws Exception;

    public Long extractMemberIdFromPasswordResetToken(String resetToken) throws Exception;

    public Long findCompanyCode(String companyCode);

    public void updateCompanyCodeMemberId(long memberId, String companyCode);
}

package co.darma.services.impl;

import co.darma.common.EncryptionHelper;
import co.darma.daos.AuthDao;
import co.darma.daos.implspringjdbc.AuthDaoImpl;
import co.darma.exceptions.InvalidAccessTokenException;
import co.darma.exceptions.InvalidCredentialException;
import co.darma.exceptions.ExpiredPasswordResetTokenException;
import co.darma.exceptions.UserAlreadyExistException;
import co.darma.models.data.Member;
import co.darma.models.data.MemberProfile;
import co.darma.models.returns.SignInResult;
import co.darma.services.AuthService;

import co.darma.services.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private AuthDao authDao = AuthDaoImpl.createInstance();

    private MemberService memberSvc = MemberServiceImpl.createInstance();

    private static Long PASSWORD_RESET_TOKEN_VALID_TIME = 30L * 60 * 1000;

    public static AuthService createInstance() {
        return new AuthServiceImpl();
    }

    @Override
    public Long verifyAccessToken(String accessToken) throws Exception {

        if (StringUtils.isNotBlank(accessToken)) {
            Long memberId = EncryptionHelper.decryptAccessToken(accessToken);
            String storedAccessToken = authDao.accessTokenByMemberId(memberId);
            if (!StringUtils.equals(storedAccessToken, accessToken)) {
                Logger.error("token diff ");
                throw new InvalidAccessTokenException("Provided access token '"
                        + accessToken + "' is invalid");
            }
            return memberId;
        } else {
            throw new InvalidAccessTokenException("Provided access token '"
                    + accessToken + "' is invalid");
        }
    }

    @Override
    public String refreshAccessToken(Long memberId, String agent) throws Exception {
        Long currentTime = System.currentTimeMillis();
        String newAccessToken = EncryptionHelper.createAccessToken(memberId, currentTime);
        authDao.updateAccessToken(memberId, currentTime, newAccessToken, agent);
        return newAccessToken;
    }

    @Override
    public SignInResult signUp(String email, String password, String agent) throws Exception {
        Member credential = authDao.getMemberByEmail(email);
        if (credential != null) {
            throw new UserAlreadyExistException(email);
        }
        String encryptedPassword = encryptPassword(password);
        Long memberId = authDao.createCredential(email, encryptedPassword);

        Long currentTime = System.currentTimeMillis();
        String accessToken = EncryptionHelper.createAccessToken(memberId, currentTime);
        authDao.createAccessToken(memberId, currentTime, accessToken, agent);
        return new SignInResult(memberId, accessToken, true);
    }

    @Override
    public SignInResult signIn(String email, String password, String agent) throws Exception {
        Member credential = authDao.getMemberByEmail(email);
        if (credential == null
                || !verifyPassword(password, credential.password)) {
            throw new InvalidCredentialException(email);
        }
        Long currentTime = System.currentTimeMillis();
        String newAccessToken = EncryptionHelper
                .createAccessToken(credential.id, currentTime);
        authDao.updateAccessToken(credential.id, currentTime, newAccessToken, agent);

        //判断是否个人资料是否完善，如果没有完善，那么需要重新完善
        MemberProfile profile = memberSvc.getProfileByMemberId(credential.id);
        boolean isNewMember = false;
        if (profile == null) {
            isNewMember = true;
        }

        return new SignInResult(credential.id, newAccessToken, isNewMember);
    }

    @Override
    public int signOut(Long memberId, String agent) throws Exception {
        Long currentTime = System.currentTimeMillis();
        return authDao.updateAccessToken(memberId, currentTime, "", agent);
    }

    @Override
    public int updatePassword(Long memberId, String oldPassword,
                              String newPassword) throws Exception {
        Member credential = authDao.getMemberById(memberId);
        if (!verifyPassword(oldPassword, credential.password)) {
            throw new InvalidCredentialException(oldPassword);
        }
        String encryptedPass = encryptPassword(newPassword);
        return authDao.updatePassword(memberId, encryptedPass);
    }

    @Override
    public void resetPassword(Long memberId, String password) throws Exception {
        String encryptedPass = encryptPassword(password);
        authDao.updatePassword(memberId, encryptedPass);
        // refresh all token;
        clearToken(memberId);
    }

    private void clearToken(Long memberId) throws SQLException {
        Long currentTime = System.currentTimeMillis();
        authDao.updateAccessToken(memberId, currentTime, "", null);
    }

    private String encryptPassword(String clearPassword) {
        return BCrypt.hashpw(clearPassword, BCrypt.gensalt());
    }

    private boolean verifyPassword(String clearPassword,
                                   String encryptedPassword) {
        return BCrypt.checkpw(clearPassword, encryptedPassword);
    }

    @Override
    public Member getMemberFromEmail(String email) throws Exception {
        return authDao.getMemberByEmail(email);
    }

    @Override
    public Long extractMemberIdFromPasswordResetToken(String resetToken)
            throws Exception {
        Long createdTime = EncryptionHelper
                .getCreatedTimeFromPasswordResetContext(resetToken);
        Long currentTime = System.currentTimeMillis();

        if (currentTime - createdTime > PASSWORD_RESET_TOKEN_VALID_TIME) {
            throw new ExpiredPasswordResetTokenException(
                    "Password reset token has expired.");
        }
        return EncryptionHelper.getMemberIdFromPasswordResetContext(resetToken);
    }

    @Override
    public Long findCompanyCode(String code) {
        return authDao.findCompanyCodeMember(code);
    }

    @Override
    public void updateCompanyCodeMemberId(long memberId, String companyCode) {
        authDao.updateCompanyCodeMemberId(memberId, companyCode);
    }
}

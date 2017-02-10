package co.darma.common;

import play.Logger;
import play.libs.Crypto;
import co.darma.exceptions.InternalException;


public class EncryptionHelper {

    public static String createAccessToken(Long memberId, Long salt) {
        try {
            String toEncrypt = memberId + ":" + System.currentTimeMillis();
            return Crypto.encryptAES(toEncrypt);
        } catch (Exception e) {
            throw new InternalException(
                    "Failed to create access token for member " + memberId, e);
        }
    }

    public static Long decryptAccessToken(String accessToken) {
        try {
            String decrypted = Crypto.decryptAES(accessToken);
            String memberIdString = decrypted.split(":")[0];
            return Long.parseLong(memberIdString);
        } catch (Exception e) {
            Logger.error("DeEnrypt error :" + e.getMessage() + ", stack " + e.getStackTrace()[0]);
            throw new InternalException("Failed to decrypt access token "
                    + accessToken, e);
        }
    }

    public static String generatePasswordResetToken(Long memberId) {
        try {
            String toEncrypt = memberId + ":" + System.currentTimeMillis();
            return Crypto.encryptAES(toEncrypt);
        } catch (Exception e) {
            throw new InternalException(
                    "Failed to generate password reset token for member "
                            + memberId, e);
        }
    }

    public static Long getMemberIdFromPasswordResetContext(String resetToken) {
        try {
            String decrypted = Crypto.decryptAES(resetToken);
            String memberIdString = decrypted.split(":")[0];
            return Long.parseLong(memberIdString);
        } catch (Exception e) {
            throw new InternalException(
                    "Failed to extract member id from password reset token "
                            + resetToken, e);
        }
    }

    public static Long getCreatedTimeFromPasswordResetContext(String resetToken) {
        try {
            String decrypted = Crypto.decryptAES(resetToken);
            String millisString = decrypted.split(":")[1];
            return Long.parseLong(millisString);
        } catch (Exception e) {
            throw new InternalException(
                    "Failed to extract created time from password reset token "
                            + resetToken, e);
        }
    }

}

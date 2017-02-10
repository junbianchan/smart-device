package co.darma.common.utils;

import play.libs.Crypto;

public class AccessTokenHelper {
	
	public static String createAccessToken(Long memberId) {
		String toEncrypt = memberId + ":" + System.currentTimeMillis();
		return Crypto.encryptAES(toEncrypt);
	}
	
	public static Long decryptAccessToken(String accessToken) {
		String decrypted = Crypto.decryptAES(accessToken);
		String memberIdString = decrypted.split(":")[0];
		return Long.parseLong(memberIdString);
	}

}

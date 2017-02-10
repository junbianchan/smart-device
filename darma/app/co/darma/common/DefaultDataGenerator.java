package co.darma.common;

import java.util.Random;

public class DefaultDataGenerator {

	private static Random random = new Random();
	private static String DEFAULT_EMAIL_DOMAIL = "@random.darma.co";

	private static String getRandomString() {
		return String.valueOf(random.nextInt(500000));
	}

	public static String generateRandomEmail() {
		String randomString = getRandomString();
		return System.currentTimeMillis() + randomString + DEFAULT_EMAIL_DOMAIL;
	}

	public static String generateRandomPassword() {
		return getRandomString();
	}
}

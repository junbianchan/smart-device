package co.darma.services;

public interface EmailService {

	public void sendPasswordResetEmail(Long memberId, String email);
	
}

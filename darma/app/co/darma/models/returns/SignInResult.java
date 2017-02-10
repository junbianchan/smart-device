package co.darma.models.returns;

public class SignInResult {
	
	public Long memberId;
	
	public String accessToken;

	public boolean isNewMember;
	
	public SignInResult() {}
	
	public SignInResult(Long memberId, String accessToken, boolean isNewMember) {
		this.memberId = memberId;
		this.accessToken = accessToken;
		this.isNewMember = isNewMember;
	}
}

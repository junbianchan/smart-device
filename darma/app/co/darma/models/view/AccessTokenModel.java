package co.darma.models.view;

public class AccessTokenModel {
	
	public Long memberId;
	public String accessToken;
	public boolean isNewMember;
	
	public AccessTokenModel(Long memberId, String accessToken, boolean isNewMember) {
		this.memberId = memberId;
		this.accessToken = accessToken;
		this.isNewMember = isNewMember;
	}

	@Override
	public String toString() {
		return "AccessTokenModel{" +
				"memberId=" + memberId +
				", accessToken='" + accessToken + '\'' +
				", isNewMember=" + isNewMember +
				'}';
	}
}

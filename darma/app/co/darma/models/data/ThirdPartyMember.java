package co.darma.models.data;

public class ThirdPartyMember {

	public Long memberId;

	public String thirdPartyId;

	public ThirdParty thirdParty;

	public ThirdPartyMember(Long memberId, String thirdPartyId,
			ThirdParty thirdParty) {
		this.memberId = memberId;
		this.thirdPartyId = thirdPartyId;
		this.thirdParty = thirdParty;
	}

	public ThirdPartyMember() {
	}

	@Override
	public String toString() {
		return "ThirdPartyMember{" +
				"memberId=" + memberId +
				", thirdPartyId='" + thirdPartyId + '\'' +
				", thirdParty=" + thirdParty +
				'}';
	}
}

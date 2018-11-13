package translate.commerce;

import java.util.List;

public class BmCmPp {
	private String participantProfileName;
	private String profileDescription;
	private List<BmCmTransRule> bmCmTransRuleList;
	public String getParticipantProfileName() {
		return participantProfileName;
	}
	public void setParticipantProfileName(String participantProfileName) {
		this.participantProfileName = participantProfileName;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public List<BmCmTransRule> getBmCmTransRuleList() {
		return bmCmTransRuleList;
	}
	public void setBmCmTransRuleList(List<BmCmTransRule> bmCmTransRuleList) {
		this.bmCmTransRuleList = bmCmTransRuleList;
	}
	@Override
	public String toString() {
		return "BmCmPp [participantProfileName=" + participantProfileName + ", profileDescription=" + profileDescription
				+ ", bmCmTransRuleList=" + bmCmTransRuleList + "]";
	}
}

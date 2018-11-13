package translate.commerce;

import java.util.List;

public class CommerceStep {
	private String stepName;
	private String variableName;
	private String description;
	private String advancedForwardingRule;
	private List<BmCmPp> bmCmPpList;
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdvancedForwardingRule() {
		return advancedForwardingRule;
	}
	public void setAdvancedForwardingRule(String advancedForwardingRule) {
		this.advancedForwardingRule = advancedForwardingRule;
	}
	public List<BmCmPp> getBmCmPpList() {
		return bmCmPpList;
	}
	public void setBmCmPpList(List<BmCmPp> bmCmPpList) {
		this.bmCmPpList = bmCmPpList;
	}

}

package translate.commerce;

public class BmCmTransRule {
	private String transitionRule;
	private String advancedConditionofTransitionRule;
	public String getTransitionRule() {
		return transitionRule;
	}
	public void setTransitionRule(String transitionRule) {
		this.transitionRule = transitionRule;
	}
	public String getAdvancedConditionofTransitionRule() {
		return advancedConditionofTransitionRule;
	}
	public void setAdvancedConditionofTransitionRule(String advancedConditionofTransitionRule) {
		this.advancedConditionofTransitionRule = advancedConditionofTransitionRule;
	}
	@Override
	public String toString() {
		return "BmCmTransRule [transitionRule=" + transitionRule + ", advancedConditionofTransitionRule="
				+ advancedConditionofTransitionRule + "]";
	}
}

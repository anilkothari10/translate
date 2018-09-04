package translate;

public class Rule {
	private String ruleType;
	private String name;
	private String variableName;
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	@Override
	public String toString() {
		return "Rule [ruleType=" + ruleType + ", name=" + name + ", variableName=" + variableName + "]" + "\n";
	}
}

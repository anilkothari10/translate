package translate.commerce;

public class CommerceRules {
	private String name;
	private String variableName;
	private String description;
	private String ruleType;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "CommerceRules [name=" + name + ", variableName=" + variableName + ", description=" + description
				+ ", ruleType=" + ruleType + "]";
	}
	
}

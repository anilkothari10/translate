package translate;

public class Rule {
	private String ruleType;
	private String name;
	private String variableName;
	private String description;
	private String scriptText;
	public String getScriptText() {
		return scriptText;
	}
	public void setScriptText(String scriptText) {
		this.scriptText = scriptText;
	}
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
		return "Rule [ruleType=" + ruleType + ", name=" + name + ", variableName=" + variableName + ", description="
				+ description + ", scriptText=" + scriptText + "]";
	}
	
}

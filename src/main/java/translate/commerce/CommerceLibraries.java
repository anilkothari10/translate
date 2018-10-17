package translate.commerce;

public class CommerceLibraries {
	
	private String name;
	private String variableName;
	private String scriptText;
	private String description;
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
	public String getScriptText() {
		return scriptText;
	}
	public void setScriptText(String scriptText) {
		this.scriptText = scriptText;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "CommerceLibraries [name=" + name + ", variableName=" + variableName + ", scriptText=" + scriptText
				+ ", description=" + description + "]";
	}
	
}

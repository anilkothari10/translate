package translate.commerce;

public class CommerceAction {
	private String label;
	private String variableName;
	private String description;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
		return "CommerceAction [label=" + label + ", variableName=" + variableName + ", description=" + description
				+ "]" + "\n";
	}
	
}

package translate.commerce;

public class CommerceAttribute {
	
	private String label;
	private String variableName;
	private String description;
	private String dataType;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
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
		return "CommerceAttribute [label=" + label + ", variableName=" + variableName + ", description=" + description
				+ ", dataType=" + dataType + "]";
	}

}

package translate;

public class Attribute {
	private String name;
	private String variableName;
	private String dataType;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@Override
	public String toString() {
		return "Attribute [name=" + name + ", variableName=" + variableName + ", dataType=" + dataType + "]" + "\n";
	}
}

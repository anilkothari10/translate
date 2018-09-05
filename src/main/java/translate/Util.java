package translate;

public class Util {
	private String name;
	private String variableName;
	private String scriptText;
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
	@Override
	public String toString() {
		return "Util [name=" + name + ", variableName=" + variableName + ", scriptText=" + scriptText + "]" + "\n";
	}
	
}

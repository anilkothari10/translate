package translate.commerce;

public class Integration {
	private String integrationName;
	private String variableName;
	private String description;
	private String idField;
	private String endpointURL;
	private IntegrationScript integrationScript;
	public IntegrationScript getIntegrationScript() {
		return integrationScript;
	}
	public void setIntegrationScript(IntegrationScript integrationScript) {
		this.integrationScript = integrationScript;
	}
	public String getIntegrationName() {
		return integrationName;
	}
	public void setIntegrationName(String integrationName) {
		this.integrationName = integrationName;
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
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public String getEndpointURL() {
		return endpointURL;
	}
	public void setEndpointURL(String endpointURL) {
		this.endpointURL = endpointURL;
	}

}

package translate.commerce;

import java.util.List;

public class Integration {
	private String integrationName;
	private String variableName;
	private String description;
	private String idField;
	private String endpointURL;
	private List<IntegrationScript> integrationScriptList;

	public List<IntegrationScript> getIntegrationScriptList() {
		return integrationScriptList;
	}
	public void setIntegrationScriptList(List<IntegrationScript> integrationScriptList) {
		this.integrationScriptList = integrationScriptList;
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

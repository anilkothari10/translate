package translate.commerce;

public class IntegrationScript {
	private String userStoryNum;
	private String integrationScriptName;
	private String description;
	public String getUserStoryNum() {
		return userStoryNum;
	}
	public void setUserStoryNum(String userStoryNum) {
		this.userStoryNum = userStoryNum;
	}
	public String getIntegrationScriptName() {
		return integrationScriptName;
	}
	public void setIntegrationScriptName(String integrationScriptName) {
		this.integrationScriptName = integrationScriptName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "IntegrationScript [userStoryNum=" + userStoryNum + ", integrationScriptName=" + integrationScriptName
				+ ", description=" + description + "]";
	}
}

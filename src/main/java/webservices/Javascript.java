package webservices;

public class Javascript {
	private String userStoryNum;
	private String javascriptName;
	private String description;
	public String getUserStoryNum() {
		return userStoryNum;
	}
	public void setUserStoryNum(String userStoryNum) {
		this.userStoryNum = userStoryNum;
	}
	public String getJavascriptName() {
		return javascriptName;
	}
	public void setJavascriptName(String javascriptName) {
		this.javascriptName = javascriptName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Javascript [userStoryNum=" + userStoryNum + ", javascriptName=" + javascriptName + ", description="
				+ description + "]";
	}
}

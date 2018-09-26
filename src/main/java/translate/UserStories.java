package translate;

import java.util.List;

public class UserStories {
	private String userStoryNum;
	private List<Attribute> attributeList;
	private List<Rule> ruleList;
	private List<Util> utilList;

	public String getUserStoryNum() {
		return userStoryNum;
	}
	public void setUserStoryNum(String userStoryNum) {
		this.userStoryNum = userStoryNum;
	}
	public List<Attribute> getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(List<Attribute> attributeList) {
		this.attributeList = attributeList;
	}
	public List<Rule> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}
	public List<Util> getUtilList() {
		return utilList;
	}
	public void setUtilList(List<Util> utilList) {
		this.utilList = utilList;
	}
	
}

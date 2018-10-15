package translate;

import java.util.List;

import translate.commerce.ApprovalSequence;
import translate.commerce.CommerceAction;
import translate.commerce.PrinterDocument;

public class UserStories {
	private String userStoryNum;
	private List<Attribute> attributeList;
	private List<Rule> ruleList;
	private List<Util> utilList;
	private List<CommerceAction> commerceActionList;
	private List<PrinterDocument> printerDocList;
	private List<ApprovalSequence> approvalSequenceList;
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
	public List<CommerceAction> getCommerceActionList() {
		return commerceActionList;
	}
	public void setCommerceActionList(List<CommerceAction> commerceActionList) {
		this.commerceActionList = commerceActionList;
	}
	public List<PrinterDocument> getPrinterDocList() {
		return printerDocList;
	}
	public void setPrinterDocList(List<PrinterDocument> printerDocList) {
		this.printerDocList = printerDocList;
	}
	public List<ApprovalSequence> getApprovalSequenceList() {
		return approvalSequenceList;
	}
	public void setApprovalSequenceList(List<ApprovalSequence> approvalSequenceList) {
		this.approvalSequenceList = approvalSequenceList;
	}
	@Override
	public String toString() {
		return "UserStories [userStoryNum=" + userStoryNum + ", attributeList=" + attributeList + ", ruleList="
				+ ruleList + ", utilList=" + utilList + ", commerceActionList=" + commerceActionList
				+ ", printerDocList=" + printerDocList + ", approvalSequenceList=" + approvalSequenceList + "]";
	}
}

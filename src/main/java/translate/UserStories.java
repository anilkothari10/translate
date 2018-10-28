package translate;

import java.util.List;

import translate.commerce.ApprovalSequence;
import translate.commerce.CommerceAction;
import translate.commerce.CommerceAttribute;
import translate.commerce.CommerceLibraries;
import translate.commerce.CommerceRules;
import translate.commerce.CommerceStep;
import translate.commerce.CommerceIntegration;
import translate.commerce.PrinterDocument;

public class UserStories {
	private String userStoryNum;
	private List<Attribute> attributeList;
	private List<Rule> ruleList;
	private List<Util> utilList;

	private List<CommerceAttribute> commerceAttributeList;
	private List<CommerceRules> commerceRuleList;
	private List<CommerceLibraries> commerceLibrariesList;
	private List<CommerceAction> commerceActionsList;
	private List<ApprovalSequence> commerceApprovalSequencesList;
	private List<PrinterDocument> commercePrinterDocumentList;
	private List<CommerceStep> commerceStepsList;
	private List<CommerceIntegration> integrationsList;
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
	public List<CommerceAttribute> getCommerceAttributeList() {
		return commerceAttributeList;
	}
	public void setCommerceAttributeList(List<CommerceAttribute> commerceAttributeList) {
		this.commerceAttributeList = commerceAttributeList;
	}
	public List<CommerceRules> getCommerceRuleList() {
		return commerceRuleList;
	}
	public void setCommerceRuleList(List<CommerceRules> commerceRuleList) {
		this.commerceRuleList = commerceRuleList;
	}
	public List<CommerceLibraries> getCommerceLibrariesList() {
		return commerceLibrariesList;
	}
	public void setCommerceLibrariesList(List<CommerceLibraries> commerceLibrariesList) {
		this.commerceLibrariesList = commerceLibrariesList;
	}
	public List<CommerceAction> getCommerceActionsList() {
		return commerceActionsList;
	}
	public void setCommerceActionsList(List<CommerceAction> commerceActionsList) {
		this.commerceActionsList = commerceActionsList;
	}
	public List<ApprovalSequence> getCommerceApprovalSequencesList() {
		return commerceApprovalSequencesList;
	}
	public void setCommerceApprovalSequencesList(List<ApprovalSequence> commerceApprovalSequencesList) {
		this.commerceApprovalSequencesList = commerceApprovalSequencesList;
	}
	public List<PrinterDocument> getCommercePrinterDocumentList() {
		return commercePrinterDocumentList;
	}
	public void setCommercePrinterDocumentList(List<PrinterDocument> commercePrinterDocumentList) {
		this.commercePrinterDocumentList = commercePrinterDocumentList;
	}
	public List<CommerceStep> getCommerceStepsList() {
		return commerceStepsList;
	}
	public void setCommerceStepsList(List<CommerceStep> commerceStepsList) {
		this.commerceStepsList = commerceStepsList;
	}
	public List<CommerceIntegration> getIntegrationsList() {
		return integrationsList;
	}
	public void setIntegrationsList(List<CommerceIntegration> integrationsList) {
		this.integrationsList = integrationsList;
	}
	
}

package translate;

import java.util.List;

import translate.commerce.ApprovalSequence;
import translate.commerce.CommerceAction;
import translate.commerce.CommerceAttribute;
import translate.commerce.CommerceLibraries;
import translate.commerce.CommerceRules;
import translate.commerce.CommerceStep;
import translate.commerce.Integration;
import translate.commerce.PrinterDocument;
import webservices.Javascript;
import webservices.Transaction;

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
	private List<Integration> integrationsList;
	private List<Transaction> transactionList;
	private List<Javascript> javascriptList;
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
	public List<Integration> getIntegrationsList() {
		return integrationsList;
	}
	public void setIntegrationsList(List<Integration> integrationsList) {
		this.integrationsList = integrationsList;
	}
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	public List<Javascript> getJavascriptList() {
		return javascriptList;
	}
	public void setJavascriptList(List<Javascript> javasriptList) {
		this.javascriptList = javasriptList;
	}
	@Override
	public String toString() {
		return "UserStories [userStoryNum=" + userStoryNum + ", attributeList=" + attributeList + ", ruleList="
				+ ruleList + ", utilList=" + utilList + ", commerceAttributeList=" + commerceAttributeList
				+ ", commerceRuleList=" + commerceRuleList + ", commerceLibrariesList=" + commerceLibrariesList
				+ ", commerceActionsList=" + commerceActionsList + ", commerceApprovalSequencesList="
				+ commerceApprovalSequencesList + ", commercePrinterDocumentList=" + commercePrinterDocumentList
				+ ", commerceStepsList=" + commerceStepsList + ", integrationsList=" + integrationsList
				+ ", transactionList=" + transactionList + ", javascriptList=" + javascriptList + "]";
	}
}

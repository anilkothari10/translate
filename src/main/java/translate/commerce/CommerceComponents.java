package translate.commerce;

import java.util.List;

public class CommerceComponents {
	private List<ApprovalSequence> approvalSequenceList;
	private List<CommerceAction> commerceActionList;
	private List<PrinterDocument> printerDocumentList;
	public List<ApprovalSequence> getApprovalSequenceList() {
		return approvalSequenceList;
	}
	public void setApprovalSequenceList(List<ApprovalSequence> approvalSequenceList) {
		this.approvalSequenceList = approvalSequenceList;
	}
	public List<CommerceAction> getCommerceActionList() {
		return commerceActionList;
	}
	public void setCommerceActionList(List<CommerceAction> commerceActionList) {
		this.commerceActionList = commerceActionList;
	}
	public List<PrinterDocument> getPrinterDocumentList() {
		return printerDocumentList;
	}
	public void setPrinterDocumentList(List<PrinterDocument> printerDocumentList) {
		this.printerDocumentList = printerDocumentList;
	}
	@Override
	public String toString() {
		return "CommerceComponents [approvalSequenceList=" + approvalSequenceList + ", commerceActionList=" + commerceActionList
				+ ", printerDocumentList=" + printerDocumentList + "]";
	}
}

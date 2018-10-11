package translate.commerce;

public class ApprovalSequence {
	private String label;
	private String variableName;
	private String description;
	private String approver;
	private String approvalTemplate;
	private String scriptText;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApprovalTemplate() {
		return approvalTemplate;
	}
	public void setApprovalTemplate(String approvalTemplate) {
		this.approvalTemplate = approvalTemplate;
	}
	public String getScriptText() {
		return scriptText;
	}
	public void setScriptText(String scriptText) {
		this.scriptText = scriptText;
	}
	@Override
	public String toString() {
		return "ApprovalSequence [label=" + label + ", variableName=" + variableName + ", description=" + description
				+ ", approver=" + approver + ", approvalTemplate=" + approvalTemplate + ", scriptText=" + scriptText
				+ "]" +"\n";
	}
	
}

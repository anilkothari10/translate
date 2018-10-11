package translate.commerce;

public class PrinterDocument {
	private String docName;
	private String variableName;
	private String description;
	private String commerceProcessLinked;
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
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
	public String getCommerceProcessLinked() {
		return commerceProcessLinked;
	}
	public void setCommerceProcessLinked(String commerceProcessLinked) {
		this.commerceProcessLinked = commerceProcessLinked;
	}
	@Override
	public String toString() {
		return "PrinterDocument [docName=" + docName + ", variableName=" + variableName + ", description=" + description
				+ ", commerceProcessLinked=" + commerceProcessLinked + "]" +"\n";
	}
}

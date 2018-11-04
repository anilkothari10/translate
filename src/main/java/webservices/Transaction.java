package webservices;

public class Transaction {
	private String userStoryNum;
	private String transactionName;
	private String description;
	public String getUserStoryNum() {
		return userStoryNum;
	}
	public void setUserStoryNum(String userStoryNum) {
		this.userStoryNum = userStoryNum;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Transaction [userStoryNum=" + userStoryNum + ", transactionName=" + transactionName + ", description="
				+ description + "]";
	}
}

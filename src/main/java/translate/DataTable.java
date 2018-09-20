package translate;

public class DataTable {
	private String tableName;
	private String description;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "DataTable [tableName=" + tableName + ", description=" + description + "]" + "\n";
	}
}

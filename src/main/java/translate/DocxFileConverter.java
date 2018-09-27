package translate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocxFileConverter {

	public void docxFileConverter(File file, List<UserStories> userStories, List<DataTable> dataTableList , List<Users> usersList , List<Groups> groupList) throws Exception {
		FileOutputStream fos = new FileOutputStream("files/output/Sample TDD_temp.docx");
		XWPFDocument docx = new XWPFDocument();
		if (docx != null) {
				for(UserStories stories : userStories){
					XWPFRun storyNumRun = docx.createParagraph().createRun();
					storyNumRun.setBold(true);
					storyNumRun.setColor("800000");
					storyNumRun.setUnderline(UnderlinePatterns.SINGLE);
					storyNumRun.setText(stories.getUserStoryNum());
					List<Attribute> attributeList= stories.getAttributeList();
					if(attributeList != null && attributeList.size() > 0){
						XWPFRun run = docx.createParagraph().createRun();
						run.setBold(true);
						run.setColor("800000");
						run.setUnderline(UnderlinePatterns.SINGLE);
						run.setText("Configuration Attributes");
						XWPFTable utilTable = docx.createTable(attributeList.size()+1,3);
						
						XWPFTableRow headerRow = utilTable.getRow(0);
						headerRow.getCell(0).setColor("4bacc6");
						headerRow.getCell(1).setColor("4bacc6");
						headerRow.getCell(2).setColor("4bacc6");
						headerRow.getCell(0).setText("Attribute Name");
						headerRow.getCell(1).setText("Variable Name");
						headerRow.getCell(2).setText("Description");
						int count = 1;
						for(Attribute attribute : attributeList){
							if(attribute != null){
								XWPFTableRow newRow = utilTable.getRow(count++);
								newRow.getCell(0).setText(attribute.getName());
								newRow.getCell(1).setText(attribute.getVariableName());
								newRow.getCell(2).setText(attribute.getDescription());
							}
						}
					}

					docx.createParagraph();
					
					List<Rule> ruleList = stories.getRuleList();
					if(ruleList != null && ruleList.size() > 0){
						for(Rule rule : ruleList){
							if(rule != null){
								XWPFRun run = docx.createParagraph().createRun();
								run.setBold(true);
								run.setColor("800000");
								run.setUnderline(UnderlinePatterns.SINGLE);
								if(rule.getRuleType().equalsIgnoreCase("1")){
									run.setText("Recommendation rule");
								}else if(rule.getRuleType().equalsIgnoreCase("2")){
									run.setText("Constraint rule");
								}else if(rule.getRuleType().equalsIgnoreCase("11")){
									run.setText("Hiding rule");
								}else{
									run.setText(rule.getDescription());
								}

								XWPFTable ruleTable = docx.createTable(2, 3);
								XWPFTableRow headerRow = ruleTable.getRow(0);
								headerRow.getCell(0).setColor("4bacc6");
								headerRow.getCell(1).setColor("4bacc6");
								headerRow.getCell(2).setColor("4bacc6");

								headerRow.getCell(0).setText("Rule Name");
								headerRow.getCell(1).setText("Variable Name");
								headerRow.getCell(2).setText("Description");

								XWPFTableRow newRow = ruleTable.getRow(1);
								newRow.getCell(0).setText(rule.getName());
								newRow.getCell(1).setText(rule.getVariableName());
								newRow.getCell(2).setText(rule.getDescription());
							}
						}
					}

					docx.createParagraph();
					
					List<Util> utilList = stories.getUtilList();
					if(utilList != null && utilList.size() > 0){
						for(Util util : utilList){
							if(util != null){
								XWPFRun run = docx.createParagraph().createRun();
								run.setBold(true);
								run.setColor("800000");
								run.setUnderline(UnderlinePatterns.SINGLE);
								run.setText("BML Util Libraries");

								XWPFTable ruleTable = docx.createTable(2, 3);
								XWPFTableRow headerRow = ruleTable.getRow(0);
								headerRow.getCell(0).setColor("4bacc6");
								headerRow.getCell(1).setColor("4bacc6");
								headerRow.getCell(2).setColor("4bacc6");

								headerRow.getCell(0).setText("Util Name");
								headerRow.getCell(1).setText("Variable Name");
								headerRow.getCell(2).setText("Description");

								XWPFTableRow newRow = ruleTable.getRow(1);
								newRow.getCell(0).setText(util.getName());
								newRow.getCell(1).setText(util.getVariableName());
								newRow.getCell(2).setText(util.getDescription());
								
								XWPFRun scriptRunHeader = docx.createParagraph().createRun();
								scriptRunHeader.addBreak();
								scriptRunHeader.setBold(true);
								scriptRunHeader.setColor("800000");
								scriptRunHeader.setUnderline(UnderlinePatterns.SINGLE);
								scriptRunHeader.setText("Script Text");
								
								XWPFRun scriptRun  = docx.createParagraph().createRun();
								for(String scriptText : util.getScriptText().split(";")){
									scriptRun.setText(scriptText + ";");
									scriptRun.addBreak();
								}
							
								
							}
						}
					}
					docx.createParagraph().setPageBreak(true);
				}
				
				
				// Add Data Table List
				XWPFRun dataTableRun = docx.createParagraph().createRun();
				dataTableRun.setBold(true);
				dataTableRun.setColor("800000");
				dataTableRun.setUnderline(UnderlinePatterns.SINGLE);
				dataTableRun.setText("Data Table List");
				
				XWPFTable dataTable = docx.createTable(dataTableList.size() + 1,2 );
				XWPFTableRow tableHeaderRow = dataTable.getRow(0);
				tableHeaderRow.getCell(0).setColor("4bacc6");
				tableHeaderRow.getCell(1).setColor("4bacc6");

				tableHeaderRow.getCell(0).setText("Data Table Name");
				tableHeaderRow.getCell(1).setText("Columns");
				
				if(dataTable != null){
					int i = 1;
					for (DataTable dataTableTemp : dataTableList) {
						if(dataTableTemp != null){
							XWPFTableRow newRow = dataTable.getRow(i);
							newRow.getCell(0).setText(dataTableTemp.getTableName());
							newRow.getCell(1).setText(dataTableTemp.getDescription());
							i++;
						}
					}
				}
				
				docx.createParagraph().setPageBreak(true);
				
	            // Add users Lis
				
				XWPFRun usersRun = docx.createParagraph().createRun();
				usersRun.setBold(true);
				usersRun.setColor("800000");
				usersRun.setUnderline(UnderlinePatterns.SINGLE);
				usersRun.setText("Users List");
				
				XWPFTable userTable = docx.createTable(usersList.size() + 1, 2 );
				XWPFTableRow userHeaderRow = userTable.getRow(0);
				userHeaderRow.getCell(0).setColor("4bacc6");
				userHeaderRow.getCell(1).setColor("4bacc6");

				userHeaderRow.getCell(0).setText("User ID");
				userHeaderRow.getCell(1).setText("User Login Name");
				
				if(userTable != null){
					int i = 1;
					for (Users usersTemp : usersList) {
						if(usersTemp != null){
							XWPFTableRow newRow = userTable.getRow(i);
							newRow.getCell(0).setText(usersTemp.getUserId());
							newRow.getCell(1).setText(usersTemp.getUserLoginName());
							i++;
						}
					}
				}
				
				docx.createParagraph().setPageBreak(true);
				
				//add group List
				XWPFRun groupRun = docx.createParagraph().createRun();
				groupRun.setBold(true);
				groupRun.setColor("800000");
				groupRun.setUnderline(UnderlinePatterns.SINGLE);
				groupRun.setText("Groups List");
				
				XWPFTable groupTable = docx.createTable(groupList.size() + 1, 2 );
				XWPFTableRow groupHeaderRow = groupTable.getRow(0);
				groupHeaderRow .getCell(0).setColor("4bacc6");
				groupHeaderRow .getCell(1).setColor("4bacc6");

				groupHeaderRow .getCell(0).setText("Group Label");
				groupHeaderRow .getCell(1).setText("Group Name");
				
				if(groupTable != null){
					int i = 1;
					for (Groups groupTemp : groupList) {
						if(groupTemp != null){
							XWPFTableRow newRow = groupTable.getRow(i);
							newRow.getCell(0).setText(groupTemp.getGroupLabel());
							newRow.getCell(1).setText(groupTemp.getGroupName());
							i++;
						}
					}
				}
				
				
			docx.write(fos);
			fos.close();
			docx.close();
		}
	}


}

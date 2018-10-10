package translate;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

public class DocxFileConverter {

	public void docxFileConverter(File file, List<UserStories> userStories, List<DataTable> dataTableList , List<Users> usersList , List<Groups> groupList) throws Exception {
		FileOutputStream fos = new FileOutputStream("files/output/Sample TDD_temp.docx");
		XWPFDocument docx = new XWPFDocument();
		if (docx != null) {
			int storyNum = 1;
				for(UserStories stories : userStories){
					int storySubNum = 1;
					XWPFRun storyNumRun = docx.createParagraph().createRun();
					storyNumRun.setBold(true);
					storyNumRun.setColor("800000");
					storyNumRun.setUnderline(UnderlinePatterns.SINGLE);
					storyNumRun.setText(storyNum + "  " + stories.getUserStoryNum());
					
					List<Attribute> attributeList= stories.getAttributeList();
					if(attributeList != null && attributeList.size() > 0){
						XWPFParagraph paragraph = docx.createParagraph();
						XWPFRun run = paragraph.createRun();
						run.setBold(true);
						run.setColor("800000");
						run.setUnderline(UnderlinePatterns.SINGLE);
						run.setText(storyNum + "." + storySubNum++ + "  " + "Configuration Attributes");
						//run.setText("Configuration Attributes");

						XWPFTable utilTable = docx.createTable(attributeList.size()+1,4);

						XWPFTableRow headerRow = utilTable.getRow(0);
						headerRow.getCell(0).setColor("4bacc6");
						headerRow.getCell(1).setColor("4bacc6");
						headerRow.getCell(2).setColor("4bacc6");
						headerRow.getCell(3).setColor("4bacc6");
						headerRow.getCell(0).setText("Attribute Name");
						headerRow.getCell(1).setText("Variable Name");
						headerRow.getCell(2).setText("Description");
						headerRow.getCell(3).setText("Data Type");
						setTableSize(utilTable, 1600, 2500, 4100, 1000);

						int count = 1;
						for(Attribute attribute : attributeList){
							if(attribute != null){
								XWPFTableRow newRow = utilTable.getRow(count++);
								newRow.getCell(0).setText(attribute.getName());
								newRow.getCell(1).setText(attribute.getVariableName());
								newRow.getCell(2).setText(attribute.getDescription());
								newRow.getCell(3).setText(Constants.getDataTypes().get(attribute.getDataType()));
							}
						}
					}

					docx.createParagraph();
					
					List<Rule> ruleList = stories.getRuleList();
					if(ruleList != null && ruleList.size() > 0){
						
						List<Rule> recommendationRuleList = new ArrayList<Rule>();
						List<Rule> constraintRuleList = new ArrayList<Rule>();
						List<Rule> hidingRuleList = new ArrayList<Rule>();
						List<Rule> recommendationItemRuleList = new ArrayList<Rule>();
						List<Rule> configurationRuleList = new ArrayList<Rule>();
						List<Rule> pricingRuleList = new ArrayList<Rule>();
						for(Rule rule : ruleList){
							if(rule.getRuleType().equalsIgnoreCase("1")){
								recommendationRuleList.add(rule);
							}else if(rule.getRuleType().equalsIgnoreCase("2")){
								constraintRuleList.add(rule);
							}else if(rule.getRuleType().equalsIgnoreCase("11")){
								hidingRuleList.add(rule);
							}else if(rule.getRuleType().equalsIgnoreCase("9")){
								recommendationItemRuleList.add(rule);
							}else if(rule.getRuleType().equalsIgnoreCase("6")){
								configurationRuleList.add(rule);
							}else if(rule.getRuleType().equalsIgnoreCase("4")){
								pricingRuleList.add(rule);
							}
						}

						if(recommendationRuleList != null && recommendationRuleList.size() > 0){
							createRuleTable(docx, recommendationRuleList, "Recommendation Rule", storyNum, storySubNum++);
						}
						if(constraintRuleList != null && constraintRuleList.size() > 0){
							createRuleTable(docx, constraintRuleList, "Constraint Rule", storyNum, storySubNum++);
						}
						if(hidingRuleList != null && hidingRuleList.size() > 0){
							createRuleTable(docx, hidingRuleList, "Hiding Rule", storyNum, storySubNum++);
						}
						if(recommendationItemRuleList != null && recommendationItemRuleList.size() > 0){
							createRuleTable(docx, recommendationItemRuleList, "Recommended Items rule", storyNum, storySubNum++);
						}
						if(configurationRuleList != null && configurationRuleList.size() > 0){
							createRuleTable(docx, configurationRuleList, "Configuration Flow rule", storyNum, storySubNum++);
						}
						if(pricingRuleList != null && pricingRuleList.size() > 0){
							createRuleTable(docx, pricingRuleList, "Pricing rule", storyNum, storySubNum++);
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
								run.setText(storyNum + "." + storySubNum++ + "  " + "BML Util Libraries");
								//run.setText("BML Util Libraries");

								XWPFTable utilTable = docx.createTable(2, 3);
								XWPFTableRow headerRow = utilTable.getRow(0);
								headerRow.getCell(0).setColor("4bacc6");
								headerRow.getCell(1).setColor("4bacc6");
								headerRow.getCell(2).setColor("4bacc6");

								headerRow.getCell(0).setText("Util Name");
								headerRow.getCell(1).setText("Variable Name");
								headerRow.getCell(2).setText("Description");

								XWPFTableRow newRow = utilTable.getRow(1);
								newRow.getCell(0).setText(util.getName());
								newRow.getCell(1).setText(util.getVariableName());
								newRow.getCell(2).setText(util.getDescription());
								
								setTableSize(utilTable, 2600, 3000, 3600, 0);
								
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
					storyNum++;
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
				tableHeaderRow.getCell(1).setText("Data Table Columns");
				
				setTableSize(dataTable, 3000, 6200, 0, 0);
				
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
				
				setTableSize(userTable, 4600, 4600, 0, 0);
				
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
				
				setTableSize(groupTable, 4600, 4600, 0, 0);
				
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


	private void createRuleTable(XWPFDocument docx, List<Rule> ruleList, String string, int storyNum, int storySubNum) {
		if(ruleList.size() > 0){
			
			XWPFParagraph paragraph = docx.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setBold(true);
			run.setColor("800000");
			run.setUnderline(UnderlinePatterns.SINGLE);
			run.setText(storyNum + "." + storySubNum + "  " + string);
			//run.setText(string);

			XWPFTable ruleTable = docx.createTable(ruleList.size() + 1, 3);
			XWPFTableRow headerRow = ruleTable.getRow(0);
			headerRow.getCell(0).setColor("4bacc6");
			headerRow.getCell(1).setColor("4bacc6");
			headerRow.getCell(2).setColor("4bacc6");

			headerRow.getCell(0).setText("Rule Name");
			headerRow.getCell(1).setText("Variable Name");
			headerRow.getCell(2).setText("Description");
			
			setTableSize(ruleTable, 2600, 3000, 3600, 0);

			int i = 1;
			for(Rule rule : ruleList){
				if(rule != null){
					XWPFTableRow newRow = ruleTable.getRow(i);
					newRow.getCell(0).setText(rule.getName());
					newRow.getCell(1).setText(rule.getVariableName());
					newRow.getCell(2).setText(rule.getDescription());
//					XWPFRun scriptTextRun = docx.createParagraph().createRun();
//					scriptTextRun.setText("Script Text : "+rule.getScriptText());
					i++;
				}
			}
		}
		
	}

	//table size = 9200 divided into number of columns
	private void setTableSize(XWPFTable table, long row0, long row1, long row2, long row3){
		for (int i = 0; i < table.getNumberOfRows(); i++) {
	        XWPFTableRow row = table.getRow(i);
	        int numCells = row.getTableCells().size();
	        for (int j = 0; j < numCells; j++) {
	            XWPFTableCell cell = row.getCell(j);
	            CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
	            CTTcPr pr = cell.getCTTc().addNewTcPr();
	            pr.addNewNoWrap();
	            if(j == 0 && row0 > 0){
		            cellWidth.setW(BigInteger.valueOf(row0));
	            }else if(j==1 && row1 > 0){
		            cellWidth.setW(BigInteger.valueOf(row1));
	            }else if(j==2 && row2 > 0){
		            cellWidth.setW(BigInteger.valueOf(row2));
	            }else if(j==3 && row3 > 0){
	            	cellWidth.setW(BigInteger.valueOf(row3));
	            }

	        }
	    }
	}

}

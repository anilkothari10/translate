package translate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

import translate.commerce.ApprovalSequence;
import translate.commerce.CommerceAction;
import translate.commerce.CommerceAttribute;
import translate.commerce.CommerceLibraries;
import translate.commerce.CommerceRules;
import translate.commerce.PrinterDocument;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

public class DocxFileConverter {

	private static final String DATA_TYPE = "Data Type";
	private static final String RULE_NAME = "Rule Name";
	private static final String DESCRIPTION = "Description";
	private static final String VARIABLE_NAME = "Variable Name";

	private XWPFParagraph heading1Para = null;
	private XWPFParagraph heading2Para = null;
	private XWPFParagraph heading3Para = null;

	public void docxFileConverter(File file, List<UserStories> userStories, List<UserStories> commerceUserStories, List<DataTable> dataTableList , List<Users> usersList , List<Groups> groupList) throws Exception {
		File outputFile = new File("files/output/Sample TDD_temp.docx");
		FileOutputStream fos = new FileOutputStream(outputFile);

		XWPFDocument docx = new XWPFDocument(new FileInputStream("files/input/template.docx"));
		for (XWPFParagraph p : docx.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (text != null && text.contains("Heading1")) {
						p.removeRun(0);
						heading1Para = p;
						break;
					}
					if (text != null && text.contains("Heading2")) {
						p.removeRun(0);
						heading2Para = p;
						break;
					}
					if (text != null && text.contains("Heading3")) {
						p.removeRun(0);
						heading3Para = p;
						break;
					}
				}
			}
		}
		//docx.removeBodyElement(0);
		createPageHeader(docx, Constants.DELOITTE_LOGO);
		createPageNumber(docx);

		/*docxTemp.close();
		XWPFDocument docx = new XWPFDocument();*/

		// Table of contents:
		XWPFParagraph p = docx.getLastParagraph();
		CTP ctP = p.getCTP();
		CTSimpleField toc = ctP.addNewFldSimple();
		toc.setInstr("TOC \\h");
		toc.setDirty(STOnOff.TRUE);

		docx.createParagraph().setPageBreak(true);

		if (docx != null) {

			//createUserStoryTables for config.xml
			createUserStoriesTables(userStories, docx);
			//createUserStoryTables for commerce
			createUserStoriesTablesForCommerce(commerceUserStories, docx);

			// Add Data Table List

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, "Data Table List");

			int numOfColumns = 2;

			XWPFTable dataTable = docx.createTable(dataTableList.size() + 1,numOfColumns );
			XWPFTableRow tableHeaderRow = dataTable.getRow(0);

			String[] headerNames = {"Data Table Name", "Data Table Columns"};

			addHeaderNameColorBold(tableHeaderRow, headerNames ,numOfColumns);

			setTableSize(dataTable, 3000, 6200, 0, 0, 0);

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

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, "Users List");

			numOfColumns = 2;
			XWPFTable userTable = docx.createTable(usersList.size() + 1, numOfColumns );
			XWPFTableRow userHeaderRow = userTable.getRow(0);

			String[] userTableHeaderNames = {"User ID", "User Login Name"};

			addHeaderNameColorBold(userHeaderRow, userTableHeaderNames ,numOfColumns);

			setTableSize(userTable, 4600, 4600, 0, 0, 0);

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

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, "Groups List");

			numOfColumns = 2;
			XWPFTable groupTable = docx.createTable(groupList.size() + 1, numOfColumns );
			XWPFTableRow groupHeaderRow = groupTable.getRow(0);

			String[] groupTableHeaderNames = {"Group Label", "Group Name"};

			addHeaderNameColorBold(groupHeaderRow, groupTableHeaderNames ,numOfColumns);

			setTableSize(groupTable, 4600, 4600, 0, 0, 0);

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
			// Table of contents:
			/*XWPFParagraph p = docx.createParagraph();
			CTP ctP = p.getCTP();
			CTSimpleField toc = ctP.addNewFldSimple();
			toc.setInstr("TOC \\h");
			toc.setDirty(STOnOff.TRUE);*/
			docx.write(fos);
			Printer.println("Output File Path: " + outputFile.getAbsolutePath());
			fos.close();
			docx.close();
		}
	}

	private void createPageNumber(XWPFDocument docx) {
		// create footer
		XWPFHeaderFooterPolicy policy = docx.getHeaderFooterPolicy();
		CTP ctpFooter = CTP.Factory.newInstance();

		XWPFParagraph[] parsFooter;

		// add style (s.th.)
		CTPPr ctppr = ctpFooter.addNewPPr();
		CTString pst = ctppr.addNewPStyle();
		pst.setVal("style21");
		CTJc ctjc = ctppr.addNewJc();
		ctjc.setVal(STJc.RIGHT);
		ctppr.addNewRPr();

		// Add in word "Page " 
		CTR ctr = ctpFooter.addNewR();
		CTText t = ctr.addNewT();
		t.setStringValue("Page ");
		t.setSpace(Space.PRESERVE);

		// add everything from the footerXXX.xml you need
		ctr = ctpFooter.addNewR();
		ctr.addNewRPr();
		CTFldChar fch = ctr.addNewFldChar();
		fch.setFldCharType(STFldCharType.BEGIN);

		ctr = ctpFooter.addNewR();
		ctr.addNewInstrText().setStringValue(" PAGE ");

		ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

		ctpFooter.addNewR().addNewT().setStringValue("1");

		ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

		XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, docx);

		parsFooter = new XWPFParagraph[1];

		parsFooter[0] = footerParagraph;

		policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

	}

	private void createPageHeader(XWPFDocument docx, String imgFile) throws InvalidFormatException, FileNotFoundException, IOException {
		docx.createHeaderFooterPolicy();
		CTSectPr sectPr = docx.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(docx, sectPr);

		XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
		header.createParagraph();
		header.getParagraphs();
		XWPFParagraph paragraph = header.getParagraphArray(0);
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun run = paragraph.createRun();
		run = paragraph.createRun();
		run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(150), Units.toEMU(30));

	}

	private void addSectionTitle(XWPFDocument docx,XWPFParagraph heading, boolean bold, String color, UnderlinePatterns underlinePatterns,
			String title) {
		XWPFParagraph para = docx.createParagraph();
		cloneParagraph(para, heading);
		XWPFRun run = para.createRun();
		run.setBold(bold);
		run.setColor(color);
		run.setUnderline(underlinePatterns);
		run.setText(title);
		if(Constants.SECTION_TITILES.contains(title)){
			run.setFontSize(Constants.SECTION_FONT_SIZE);
		}
	}

	public static void cloneParagraph(XWPFParagraph clone, XWPFParagraph source) {
		CTPPr pPr = clone.getCTP().isSetPPr() ? clone.getCTP().getPPr() : clone.getCTP().addNewPPr();
		pPr.set(source.getCTP().getPPr());
		for (XWPFRun r : source.getRuns()) {
			XWPFRun nr = clone.createRun();
			cloneRun(nr, r);
		}
	}

	public static void cloneRun(XWPFRun clone, XWPFRun source) {
		CTRPr rPr = clone.getCTR().isSetRPr() ? clone.getCTR().getRPr() : clone.getCTR().addNewRPr();
		rPr.set(source.getCTR().getRPr());
	}

	private void createRuleTable(XWPFDocument docx, List<Rule> ruleList, String string, int storyNum, int storySubNum) {
		if(ruleList.size() > 0){

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum + " " + string);

			int numOfColumns = 3; 
			XWPFTable ruleTable = docx.createTable(ruleList.size() + 1, numOfColumns);
			XWPFTableRow headerRow = ruleTable.getRow(0);

			String[] headerNames = {RULE_NAME, VARIABLE_NAME, DESCRIPTION};

			addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

			setTableSize(ruleTable, 2600, 3000, 3600, 0, 0);

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

	private void createCommerceRuleTable(XWPFDocument docx, List<CommerceRules> ruleList, String string, int storyNum, int storySubNum) {
		if(ruleList.size() > 0){

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum + " " + string);

			int numOfColumns = 3; 
			XWPFTable ruleTable = docx.createTable(ruleList.size() + 1, numOfColumns);
			XWPFTableRow headerRow = ruleTable.getRow(0);

			String[] headerNames = {RULE_NAME, VARIABLE_NAME, DESCRIPTION};

			addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

			setTableSize(ruleTable, 2600, 3000, 3600, 0, 0);

			int i = 1;
			for(CommerceRules rule : ruleList){
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

	private void addHeaderNameColorBold(XWPFTableRow headerRow, String[] headerNames, int numOfColumns) {
		for(int i = 0; i < numOfColumns ; i++){
			headerRow.getCell(i).removeParagraph(0);
			XWPFRun row0Run = headerRow.getCell(i).addParagraph().createRun();
			row0Run.setBold(true);
			row0Run.setText(headerNames[i]);
			headerRow.getCell(i).setColor("4bacc6");
		}
	}


	/**
	 * Table size = 9200 divided into number of columns.
	 * For e.g., table with 3 columns, standard column size distribution is : 2600, 3000, 3600
	 * @param table
	 * @param row0
	 * @param row1
	 * @param row2
	 * @param row3
	 */
	private void setTableSize(XWPFTable table, long row0, long row1, long row2, long row3, long row4){
		if(row0 + row1 + row2 + row3 + row4 != 9200){
			Printer.println("Table size not equal to standard table size(9200)");
		}
		for (int i = 0; i < table.getNumberOfRows(); i++) {
			XWPFTableRow row = table.getRow(i);
			int numCells = row.getTableCells().size();
			for (int j = 0; j < numCells; j++) {
				XWPFTableCell cell = row.getCell(j);
				CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
				//				CTTcPr pr = cell.getCTTc().addNewTcPr();
				//				pr.addNewNoWrap();
				if(j == 0 && row0 > 0){
					cellWidth.setW(BigInteger.valueOf(row0));
				}else if(j==1 && row1 > 0){
					cellWidth.setW(BigInteger.valueOf(row1));
				}else if(j==2 && row2 > 0){
					cellWidth.setW(BigInteger.valueOf(row2));
				}else if(j==3 && row3 > 0){
					cellWidth.setW(BigInteger.valueOf(row3));
				}else if(j==4 && row4 > 0){
					cellWidth.setW(BigInteger.valueOf(row4));
				}

			}
		}
	}


	private void createUserStoriesTables(List<UserStories> userStories, XWPFDocument docx){

		addSectionTitle(docx,heading1Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, Constants.CONFIGURATION_SECTION);

		int storyNum = 1;
		for(UserStories stories : userStories){
			int storySubNum = 1;

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + " " + stories.getUserStoryNum());

			List<Attribute> attributeList= stories.getAttributeList();
			if(attributeList != null && attributeList.size() > 0){

				addSectionTitle(docx,heading3Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Configuration Attributes");

				int numOfColumns = 4;
				XWPFTable utilTable = docx.createTable(attributeList.size()+1,numOfColumns);

				XWPFTableRow headerRow = utilTable.getRow(0);

				String[] headerNames = {"Attribute Name", VARIABLE_NAME, DESCRIPTION, DATA_TYPE};

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(utilTable, 1600, 2500, 4100, 1000, 0);

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

						addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "BML Util Libraries");

						int numOfColumns = 3;
						XWPFTable utilTable = docx.createTable(2, numOfColumns);
						XWPFTableRow headerRow = utilTable.getRow(0);

						String[] headerNames = {"Util Name", VARIABLE_NAME, DESCRIPTION};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						XWPFTableRow newRow = utilTable.getRow(1);
						newRow.getCell(0).setText(util.getName());
						newRow.getCell(1).setText(util.getVariableName());
						newRow.getCell(2).setText(util.getDescription());

						setTableSize(utilTable, 2600, 3000, 3600, 0, 0);

						addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, "Script Text");

						XWPFRun scriptRun = docx.createParagraph().createRun();
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
	}

	private void createUserStoriesTablesForCommerce(List<UserStories> userStories, XWPFDocument docx){
		addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, Constants.COMMERCE_SECTION);
		int storyNum = 1;
		for(UserStories stories : userStories){
			int storySubNum = 1;

			addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + " " + stories.getUserStoryNum());

			List<CommerceAttribute> commerceAttributeList= stories.getCommerceAttributeList();
			if(commerceAttributeList != null && commerceAttributeList.size() > 0){

				addSectionTitle(docx,heading3Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Attributes");

				int numOfColumns = 4;
				XWPFTable commerceAttributesTable = docx.createTable(commerceAttributeList.size()+1,numOfColumns);

				XWPFTableRow headerRow = commerceAttributesTable.getRow(0);

				String[] headerNames = {"Attribute Label", VARIABLE_NAME, DESCRIPTION, DATA_TYPE};

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(commerceAttributesTable, 1600, 2500, 4100, 1000, 0);

				int count = 1;
				for(CommerceAttribute attribute : commerceAttributeList){
					if(attribute != null){
						XWPFTableRow newRow = commerceAttributesTable.getRow(count++);
						newRow.getCell(0).setText(attribute.getLabel());
						newRow.getCell(1).setText(attribute.getVariableName());
						newRow.getCell(2).setText(attribute.getDescription());
						newRow.getCell(3).setText(attribute.getDataType());
					}
				}
			}

			docx.createParagraph();
			
			List<CommerceRules> ruleList = stories.getCommerceRuleList();
			if(ruleList != null && ruleList.size() > 0){

				List<CommerceRules> constraintRuleList = new ArrayList<CommerceRules>();
				List<CommerceRules> hidingRuleList = new ArrayList<CommerceRules>();
				List<CommerceRules> validationRule = new ArrayList<CommerceRules>();
				for(CommerceRules rule : ruleList){
					if(rule.getRuleType().equalsIgnoreCase("1")){
						constraintRuleList.add(rule);
					}else if(rule.getRuleType().equalsIgnoreCase("2")){
						hidingRuleList.add(rule);
					}else if(rule.getRuleType().equalsIgnoreCase("3")){
						validationRule.add(rule);
					}
				}

				if(constraintRuleList != null && constraintRuleList.size() > 0){
					createCommerceRuleTable(docx, constraintRuleList, "Constraint Rule", storyNum, storySubNum++);
				}
				if(hidingRuleList != null && hidingRuleList.size() > 0){
					createCommerceRuleTable(docx, hidingRuleList, "Hiding Rule", storyNum, storySubNum++);
				}
				if(validationRule != null && validationRule.size() > 0){
					createCommerceRuleTable(docx, validationRule, "Validation rule", storyNum, storySubNum++);
				}
			}

			docx.createParagraph();

			List<CommerceLibraries> commerceLibraries= stories.getCommerceLibrariesList();
			if(commerceLibraries != null && commerceLibraries.size() > 0){
				for(CommerceLibraries libraries: commerceLibraries){
					if(libraries != null){

						addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Libraries");

						int numOfColumns = 3;
						XWPFTable commerceLibTable = docx.createTable(2, numOfColumns);
						XWPFTableRow headerRow = commerceLibTable.getRow(0);

						String[] headerNames = {"Library Name", VARIABLE_NAME, DESCRIPTION};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						XWPFTableRow newRow = commerceLibTable.getRow(1);
						newRow.getCell(0).setText(libraries.getName());
						newRow.getCell(1).setText(libraries.getVariableName());
						newRow.getCell(2).setText(libraries.getDescription());

						setTableSize(commerceLibTable, 2600, 3000, 3600, 0, 0);

						addSectionTitle(docx,heading3Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, "Script Text");

						XWPFRun scriptRun = docx.createParagraph().createRun();
						for(String scriptText : libraries.getScriptText().split(";")){
							scriptRun.setText(scriptText + ";");
							scriptRun.addBreak();
						}
					}
				}
			}

			docx.createParagraph();

			List<CommerceAction> commerceActionsList= stories.getCommerceActionsList();
			if(commerceActionsList != null && commerceActionsList.size() > 0){

				addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Actions");

				int numOfColumns = 3;
				XWPFTable commerceActionTable = docx.createTable(commerceActionsList.size()+1,numOfColumns);

				XWPFTableRow headerRow = commerceActionTable.getRow(0);

				String[] headerNames = {"Action", "Action Variable Name", DESCRIPTION};

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(commerceActionTable, 2600, 3000, 3600, 0, 0);

				int count = 1;
				for(CommerceAction action : commerceActionsList){
					if(action != null){
						XWPFTableRow newRow = commerceActionTable.getRow(count++);
						newRow.getCell(0).setText(action.getLabel());
						newRow.getCell(1).setText(action.getVariableName());
						newRow.getCell(2).setText(action.getDescription());
					}
				}
			}

			docx.createParagraph();

			List<ApprovalSequence> commerceApprovalSequenceList= stories.getCommerceApprovalSequencesList();
			if(commerceApprovalSequenceList != null && commerceApprovalSequenceList.size() > 0){
				for(ApprovalSequence approvalSequence : commerceApprovalSequenceList){
					if(approvalSequence != null){
						addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Approval Sequence");

						int numOfColumns = 5;
						XWPFTable commerceApprovalSequenceTable = docx.createTable(2,numOfColumns);

						XWPFTableRow headerRow = commerceApprovalSequenceTable.getRow(0);

						String[] headerNames = {"Approval Reason Name", "Approval Reason Variable Name", "Approval Reason Description", "Approver", "Approval Template"};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						setTableSize(commerceApprovalSequenceTable, 1500, 1500, 3000, 1500, 1700);

						XWPFTableRow newRow = commerceApprovalSequenceTable.getRow(1);
						newRow.getCell(0).setText(approvalSequence.getLabel());
						newRow.getCell(1).setText(approvalSequence.getVariableName());
						newRow.getCell(2).setText(approvalSequence.getDescription());
						newRow.getCell(3).setText(approvalSequence.getApprover());
						newRow.getCell(4).setText(approvalSequence.getApprovalTemplate());

						if(approvalSequence.getScriptText() != null){
							addSectionTitle(docx,heading3Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, "Advanced Condition");
							XWPFRun scriptRun = docx.createParagraph().createRun();
							for(String scriptText : approvalSequence.getScriptText().split(";")){
								scriptRun.setText(scriptText + ";");
								scriptRun.addBreak();
							}
						}
					}
				}
			}

			docx.createParagraph();

			List<PrinterDocument> commercePrinterDocuments= stories.getCommercePrinterDocumentList();
			if(commercePrinterDocuments != null && commercePrinterDocuments.size() > 0){

				addSectionTitle(docx,heading2Para, true, Constants.SECTIONTITLECOLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Printer Friendly Documents");

				int numOfColumns = 4;
				XWPFTable commercePrinterDocumentTable = docx.createTable(2, numOfColumns);
				XWPFTableRow headerRow = commercePrinterDocumentTable.getRow(0);

				String[] headerNames = {"Document Name", VARIABLE_NAME, DESCRIPTION, "Commerce Process Linked"};

				setTableSize(commercePrinterDocumentTable, 1600, 2500, 4100, 1000, 0);

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				int count = 1;
				for(PrinterDocument printerDocument: commercePrinterDocuments){
					if(printerDocument != null){
						XWPFTableRow newRow = commercePrinterDocumentTable.getRow(count++);
						newRow.getCell(0).setText(printerDocument.getDocName());
						newRow.getCell(1).setText(printerDocument.getVariableName());
						newRow.getCell(2).setText(printerDocument.getDescription());
						newRow.getCell(3).setText(printerDocument.getCommerceProcessLinked());
					}
				}
			}

			docx.createParagraph().setPageBreak(true);
			storyNum++;
		}
	}

}

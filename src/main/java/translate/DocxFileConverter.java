package translate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

import translate.commerce.ApprovalSequence;
import translate.commerce.BmCmPp;
import translate.commerce.BmCmTransRule;
import translate.commerce.CommerceAction;
import translate.commerce.CommerceAttribute;
import translate.commerce.CommerceLibraries;
import translate.commerce.CommerceRules;
import translate.commerce.CommerceStep;
import translate.commerce.Integration;
import translate.commerce.IntegrationScript;
import translate.commerce.PrinterDocument;
import utilities.FileSearch;
import webservices.Javascript;
import webservices.Transaction;

public class DocxFileConverter {

	private static final String DATA_TYPE = "Data Type";
	private static final String RULE_NAME = "Rule Name";
	private static final String DESCRIPTION = "Description";
	private static final String VARIABLE_NAME = "Variable Name";
	
	private String SECTION_TITLE_COLOR ;
	private String FONT_FAMILY ;
	private String HEADER_FONT_FAMILY ;
	private int FONT_SIZE = 0;
	private int HEADING1 = 0;
	private int HEADING2 = 0;
	private int HEADING3 = 0;
	private int NORMAL_FONT_SIZE = 0;
	private File inputFile = null;
	
	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	private Properties staticData = new Properties();

	public static void main(String[] args) throws FileNotFoundException, IOException{
		Properties prope = new Properties();
		prope.load(new FileInputStream(Constants.PROPERTIES_FILE));
		System.out.println(prope);
	}
	
	public void setProperties(){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(inputFile + "/" + Constants.PROPERTIES_FILE));
			staticData.load(new FileInputStream(inputFile + "/" + Constants.STATIC_DATA_PROPERTIES_FILE));
			SECTION_TITLE_COLOR = prop.getProperty("sectionTitleColor");
			FONT_SIZE = Integer.parseInt(prop.getProperty("fontSize"));
			NORMAL_FONT_SIZE = Integer.parseInt(prop.getProperty("normalFontSize"));
			HEADING1 = Integer.parseInt(prop.getProperty("heading1"));
			HEADING2 = Integer.parseInt(prop.getProperty("heading2"));
			HEADING3 = Integer.parseInt(prop.getProperty("heading3"));
			FONT_FAMILY = prop.getProperty("fontFamily");
			HEADER_FONT_FAMILY = prop.getProperty("headerFontFamily");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void docxFileConverter(List<UserStories> userStories, List<UserStories> commerceUserStories, List<DataTable> dataTableList ,
			List<Users> usersList , List<Groups> groupList) throws Exception {
		setProperties();
		File outputFile = new File(inputFile + "/Output_TDD.docx");
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(outputFile);
		File docxFile = FileSearch.searchFileOrDirectory(inputFile, "template.docx");
		XWPFDocument docx = new XWPFDocument(new FileInputStream(docxFile));
		createPageHeader(docx, inputFile + "/" + Constants.DELOITTE_LOGO);
		createPageNumber(docx);

		// Table of contents:
		addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, Constants.TOC);
		XWPFParagraph p = docx.createParagraph();
		CTP ctP = p.getCTP();
		CTSimpleField toc = ctP.addNewFldSimple();
		toc.setInstr("TOC \\h");
		toc.setDirty(STOnOff.TRUE);

		docx.createParagraph().setPageBreak(true);

		if (docx != null) {

			addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("documentOverview"));
			//addText(docx, staticData.getProperty("documentOverview"));
			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("section1.1"));
			//addText(docx, staticData.getProperty("section1.1"));
			addText(docx, staticData.getProperty("section1.1Text"));
			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("section1.2"));
			//addText(docx, staticData.getProperty("section1.2"));
			addText(docx, staticData.getProperty("section1.2Text"));
			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("section1.3"));
			//addText(docx, staticData.getProperty("section1.3"));
			addText(docx, staticData.getProperty("section1.3Text"));

			String keyTermsTableColumns =  staticData.getProperty("keyTermsTableColumns");
			if(keyTermsTableColumns != null){
				String[] allKeyTermsTableColumns = keyTermsTableColumns.split(",");
				if(allKeyTermsTableColumns .length > 0){
					XWPFTable keyTermsTable = docx.createTable(allKeyTermsTableColumns.length + 1, 2);
					XWPFTableRow headerRow = keyTermsTable.getRow(0);
					String[] keyTermsTableHeaderNames = {staticData.getProperty("table1Column1Title"), staticData.getProperty("table1Column2Title")};
					addHeaderNameColorBold(headerRow, keyTermsTableHeaderNames ,2);
					setTableSize(keyTermsTable, 4600, 4600, 0, 0, 0, 0);
					int rowCount = 1;
					for(String columnName : allKeyTermsTableColumns){
						XWPFTableRow keyTermsTableNewRow = keyTermsTable.getRow(rowCount++);
						keyTermsTableNewRow.getCell(0).setText(columnName.trim());
						keyTermsTableNewRow.getCell(1).setText(staticData.getProperty(columnName));
					}
				}
			}

			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("section1.4"));
			//addText(docx, staticData.getProperty("section1.4"));
			addText(docx, staticData.getProperty("section1.4Text"));
			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("section1.5"));
			//addText(docx, staticData.getProperty("section1.5"));
			addText(docx, staticData.getProperty("section1.5.1Text"));
			addText(docx, staticData.getProperty("section1.5.2Text"));
			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("section1.6"));
			//addText(docx, staticData.getProperty("section1.6"));
			addText(docx, staticData.getProperty("section1.6Text"));

			XWPFTable relatedDocumentation = docx.createTable(3, 2);
			XWPFTableRow relatedDocumentationHeaderRow = relatedDocumentation.getRow(0);
			String[] relatedDocumentationHeaderNames = {staticData.getProperty("table2Column1Title"), staticData.getProperty("table2Column2Title")};
			addHeaderNameColorBold(relatedDocumentationHeaderRow, relatedDocumentationHeaderNames ,2);
			setTableSize(relatedDocumentation, 4600, 4600, 0, 0, 0, 0);
			XWPFTableRow keyTermsTableNewRow1 = relatedDocumentation.getRow(1);
			keyTermsTableNewRow1.getCell(0).setText(staticData.getProperty("rowOneColumnOne"));
			keyTermsTableNewRow1.getCell(1).setText(staticData.getProperty("rowOneColumnTwo"));
			XWPFTableRow keyTermsTableNewRow2 = relatedDocumentation.getRow(2);
			keyTermsTableNewRow2.getCell(0).setText(staticData.getProperty("rowTwoColumnOne"));
			keyTermsTableNewRow2.getCell(1).setText(staticData.getProperty("rowTwoColumnTwo"));

			docx.createParagraph().setPageBreak(true);
			addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, staticData.getProperty("ImplementationArea"));
			//addText(docx, staticData.getProperty("ImplementationArea"));
			addText(docx, staticData.getProperty("ImplementationAreaText"));

			//createUserStoryTables for config.xml
			createUserStoriesTables(userStories, docx);
			//createUserStoryTables for commerce
			createUserStoriesTablesForCommerce(commerceUserStories, docx);
			
			createUserStoriesTablesForFileManager(commerceUserStories, docx);

			int numOfColumns;
			// Add Data Table List
			if(dataTableList.size() > 0){
				addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Data Table List");
				//addText(docx, staticData.getProperty("DataTables"));
				addText(docx, staticData.getProperty("DataTablesText"));
				numOfColumns = 2;

				XWPFTable dataTable = docx.createTable(dataTableList.size() + 1,numOfColumns );
				XWPFTableRow tableHeaderRow = dataTable.getRow(0);

				String[] headerNames = {"Data Table Name", "Data Table Columns"};

				addHeaderNameColorBold(tableHeaderRow, headerNames ,numOfColumns);

				setTableSize(dataTable, 3000, 6200, 0, 0, 0, 0);

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
			}

			// Add users Lis
			if(usersList.size() > 0){
				addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Users List");
				//addText(docx, staticData.getProperty("Users"));
				addText(docx, staticData.getProperty("UsersText"));

				numOfColumns = 2;
				XWPFTable userTable = docx.createTable(usersList.size() + 1, numOfColumns );
				XWPFTableRow userHeaderRow = userTable.getRow(0);

				String[] userTableHeaderNames = {"User ID", "User Login Name"};

				addHeaderNameColorBold(userHeaderRow, userTableHeaderNames ,numOfColumns);

				setTableSize(userTable, 4600, 4600, 0, 0, 0, 0);

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
			}

			if(groupList.size() > 0){
				addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Groups List");
				//addText(docx, staticData.getProperty("Groups"));
				addText(docx, staticData.getProperty("GroupsText"));
				numOfColumns = 2;
				XWPFTable groupTable = docx.createTable(groupList.size() + 1, numOfColumns );
				XWPFTableRow groupHeaderRow = groupTable.getRow(0);

				String[] groupTableHeaderNames = {"Group Label", "Group Name"};

				addHeaderNameColorBold(groupHeaderRow, groupTableHeaderNames ,numOfColumns);

				setTableSize(groupTable, 4600, 4600, 0, 0, 0, 0);

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
			}
			docx.write(fos);
			Printer.println("Output File Path: " + outputFile.getAbsolutePath());
			fos.close();
			docx.close();
		}
	}

	private void createUserStoriesTablesForFileManager(List<UserStories> userStories, XWPFDocument docx) {
		addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, Constants.FILE_MANAGER_SECTION);
		//addText(docx, staticData.getProperty("FileManager"));
		addText(docx, staticData.getProperty("FileManagerText"));
		int storyNum = 1;
		String webServiceFolderName = Constants.WEB_SERVICES_DIR.split("/")[2];
		String jsFolderName = Constants.JS_DIR.split("/")[2];
		for(UserStories stories : userStories){
			int storySubNum = 1;
			addSectionTitle(docx,"Heading2", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + " " + stories.getUserStoryNum());
			// Add web services transaction data
			List<Transaction> transactionList= stories.getTransactionList();
			if(transactionList != null && transactionList.size() > 0){
				for(Transaction trans : transactionList){
					addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, 
							storyNum + "." + storySubNum++ + " " + webServiceFolderName + " : " + trans.getTransactionName());
					XWPFRun run = createRun(docx.createParagraph());
					for(String str : trans.getDescription().split(Constants.LINE_DELIMITER)){
						run.setText(str);
						run.addBreak();
					}
				}
			}

			// Add Javascript files data
			List<Javascript> javascriptList= stories.getJavascriptList();
			if(javascriptList != null && javascriptList.size() > 0){
				for(Javascript trans : javascriptList){
					addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, 
							storyNum + "." + storySubNum++ + " " + jsFolderName + " : " + trans.getJavascriptName()); 
					XWPFRun run = createRun(docx.createParagraph());
					for(String str : trans.getDescription().split(Constants.LINE_DELIMITER)){
						run.setText(str);
						run.addBreak();
					}
				}
			}

			docx.createParagraph().setPageBreak(true);
			storyNum++;
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

		XWPFRun run = createRun(paragraph);
		run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(150), Units.toEMU(30));

	}

	private void addSectionTitle(XWPFDocument docx,String string, boolean bold, String color, UnderlinePatterns underlinePatterns,
			String title) {
		XWPFParagraph para = docx.createParagraph();
		XWPFRun run = createRun(para);
		if(StringUtils.isNotBlank(string)){
			para.setStyle(string);
			run.setFontFamily(HEADER_FONT_FAMILY);
			if("Heading1".equals(string)){
				run.setFontSize(HEADING1);
			}
			else if("Heading2".equals(string)){
				run.setFontSize(HEADING2);
			}
			else if("Heading3".equals(string)){
				run.setFontSize(HEADING3);
			}
		}
		else{
			run.setFontSize(FONT_SIZE);
		}
		run.setBold(bold);
		run.setColor(color);
		run.setUnderline(underlinePatterns);
		run.setText(title);
		/*if(Constants.SECTION_TITILES.contains(title)){
			run.setFontSize(Constants.SECTION_FONT_SIZE);
		}*/
	}
	
	private void addText(XWPFDocument docx, String text) {
		if(text != null && !text.isEmpty()){
			for(String line: text.split("\\n")){
				XWPFParagraph para = docx.createParagraph();
				XWPFRun run = createRun(para);
				run.setText(line);
			}

		}
	}

	private void createRuleTable(XWPFDocument docx, List<Rule> ruleList, String string, int storyNum, int storySubNum) {
		if(ruleList.size() > 0){

			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum + " " + string);

			int numOfColumns = 3; 
			XWPFTable ruleTable = docx.createTable(ruleList.size() + 1, numOfColumns);
			XWPFTableRow headerRow = ruleTable.getRow(0);

			String[] headerNames = {RULE_NAME, VARIABLE_NAME, DESCRIPTION};

			addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

			setTableSize(ruleTable, 2600, 3000, 3600, 0, 0, 0);

			int i = 1;
			for(Rule rule : ruleList){
				if(rule != null){
					XWPFTableRow newRow = ruleTable.getRow(i);
					newRow.getCell(0).setText(rule.getName());
					newRow.getCell(1).setText(rule.getVariableName());
					newRow.getCell(2).setText(rule.getDescription());
					i++;
				}
			}
		}

	}

	private void createCommerceRuleTable(XWPFDocument docx, List<CommerceRules> ruleList, String string, 
			int storyNum, int storySubNum) {
		if(ruleList.size() > 0){

			addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum + " " + string);

			int numOfColumns = 3; 
			XWPFTable ruleTable = docx.createTable(ruleList.size() + 1, numOfColumns);
			XWPFTableRow headerRow = ruleTable.getRow(0);

			String[] headerNames = {RULE_NAME, VARIABLE_NAME, DESCRIPTION};

			addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

			setTableSize(ruleTable, 2600, 3000, 3600, 0, 0, 0);

			int i = 1;
			for(CommerceRules rule : ruleList){
				if(rule != null){
					XWPFTableRow newRow = ruleTable.getRow(i);
					newRow.getCell(0).setText(rule.getName());
					newRow.getCell(1).setText(rule.getVariableName());
					newRow.getCell(2).setText(rule.getDescription());
					i++;
				}
			}
		}

	}

	private void addHeaderNameColorBold(XWPFTableRow headerRow, String[] headerNames, int numOfColumns) {
		for(int i = 0; i < numOfColumns ; i++){
			headerRow.getCell(i).removeParagraph(0);
			XWPFRun row0Run = createRun(headerRow.getCell(i).addParagraph());
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
	private void setTableSize(XWPFTable table, long row0, long row1, long row2, long row3, long row4, long row5){
		if(row0 + row1 + row2 + row3 + row4 + row5 != 9200){
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
				}else if(j==5 && row5 > 0){
					cellWidth.setW(BigInteger.valueOf(row5));
				}

			}
		}
	}


	private void createUserStoriesTables(List<UserStories> userStories, XWPFDocument docx){

		addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, Constants.CONFIGURATION_SECTION);
		addText(docx, staticData.getProperty("Configuration"));
		int storyNum = 1;
		boolean staticTextConfigurationAttributesAdded = false;
		boolean staticTextRulesAdded = false;
		boolean staticTextBMLLibrariesAdded = false;
		for(UserStories stories : userStories){
			int storySubNum = 1;

			addSectionTitle(docx,"Heading2", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + " " + stories.getUserStoryNum());

			List<Attribute> attributeList= stories.getAttributeList();
			if(attributeList != null && attributeList.size() > 0){

				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Configuration Attributes");
				if(!staticTextConfigurationAttributesAdded){
					addText(docx, staticData.getProperty("ConfigurationAttributes"));
					staticTextConfigurationAttributesAdded = true;
				}
				int numOfColumns = 4;
				XWPFTable utilTable = docx.createTable(attributeList.size()+1,numOfColumns);

				XWPFTableRow headerRow = utilTable.getRow(0);

				String[] headerNames = {"Attribute Name", VARIABLE_NAME, DESCRIPTION, DATA_TYPE};

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(utilTable, 1600, 2500, 4100, 1000, 0, 0);

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

				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " +Constants.RULES_SECTION);
				if(!staticTextRulesAdded){
					addText(docx, staticData.getProperty("Rules"));
					staticTextRulesAdded = true;
				}
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
				boolean headerAdded = false;
				for(Util util : utilList){
					if(util != null){
						if(!headerAdded){
							addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "BML Util Libraries");
							if(!staticTextBMLLibrariesAdded){
								addText(docx, staticData.getProperty("BMLLibraries"));
								staticTextBMLLibrariesAdded = true;
							}
							headerAdded = true;
						}
						else{
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "BML Util Libraries");
						}
						int numOfColumns = 3;
						XWPFTable utilTable = docx.createTable(2, numOfColumns);
						XWPFTableRow headerRow = utilTable.getRow(0);

						String[] headerNames = {"Util Name", VARIABLE_NAME, DESCRIPTION};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						XWPFTableRow newRow = utilTable.getRow(1);
						newRow.getCell(0).setText(util.getName());
						newRow.getCell(1).setText(util.getVariableName());
						newRow.getCell(2).setText(util.getDescription());

						setTableSize(utilTable, 2600, 3000, 3600, 0, 0, 0);

						addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Script");

						XWPFRun scriptRun = createRun(docx.createParagraph());
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
		addSectionTitle(docx,"Heading1", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, Constants.COMMERCE_SECTION);
		addText(docx, staticData.getProperty("Commerce"));
		int storyNum = 1;
		boolean staticTextCommerceAttributesAdded = false;
		boolean staticTextCommerceRulesAdded = false;
		boolean staticTextCommerceActionsAdded = false;
		boolean staticTextCommerceWorkflowAdded = false;
		boolean staticTextPrinterFriendlyDocAdded = false;
		boolean staticTextIntegrationAdded = false;
		for(UserStories stories : userStories){
			int storySubNum = 1;
			addSectionTitle(docx,"Heading2", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + " " + stories.getUserStoryNum());
			List<CommerceAttribute> commerceAttributeList= stories.getCommerceAttributeList();
			if(commerceAttributeList != null && commerceAttributeList.size() > 0){

				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Attributes");
				if(!staticTextCommerceAttributesAdded){
					addText(docx, staticData.getProperty("CommerceAttributes"));
					staticTextCommerceAttributesAdded = true;
				}
				int numOfColumns = 4;
				XWPFTable commerceAttributesTable = docx.createTable(commerceAttributeList.size()+1,numOfColumns);

				XWPFTableRow headerRow = commerceAttributesTable.getRow(0);

				String[] headerNames = {"Attribute Label", VARIABLE_NAME, DESCRIPTION, DATA_TYPE};

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(commerceAttributesTable, 1600, 2500, 4100, 1000, 0, 0);

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
				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " +Constants.RULES_SECTION);
				if(!staticTextCommerceRulesAdded){
					addText(docx, staticData.getProperty("CommerceRules"));
					staticTextCommerceRulesAdded = true;
				}
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
					createCommerceRuleTable(docx, validationRule, "Validation Rule", storyNum, storySubNum++);
				}
			}

			docx.createParagraph();

			List<CommerceLibraries> commerceLibraries= stories.getCommerceLibrariesList();
			if(commerceLibraries != null && commerceLibraries.size() > 0){
				boolean headerAdded = false;
				for(CommerceLibraries libraries: commerceLibraries){
					if(libraries != null){
						if(!headerAdded){
							addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Libraries");
							headerAdded = true;
						}else{
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Commerce Libraries");
						}
						int numOfColumns = 3;
						XWPFTable commerceLibTable = docx.createTable(2, numOfColumns);
						XWPFTableRow headerRow = commerceLibTable.getRow(0);

						String[] headerNames = {"Library Name", VARIABLE_NAME, DESCRIPTION};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						XWPFTableRow newRow = commerceLibTable.getRow(1);
						newRow.getCell(0).setText(libraries.getName());
						newRow.getCell(1).setText(libraries.getVariableName());
						newRow.getCell(2).setText(libraries.getDescription());

						setTableSize(commerceLibTable, 2600, 3000, 3600, 0, 0, 0);

						addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Script");

						XWPFRun scriptRun = createRun(docx.createParagraph());
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

				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Actions");
				if(!staticTextCommerceActionsAdded){
					addText(docx, staticData.getProperty("CommerceActions"));
					staticTextCommerceActionsAdded = true;
				}
				int numOfColumns = 3;
				XWPFTable commerceActionTable = docx.createTable(commerceActionsList.size()+1,numOfColumns);

				XWPFTableRow headerRow = commerceActionTable.getRow(0);

				String[] headerNames = {"Action", "Action Variable Name", DESCRIPTION};

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(commerceActionTable, 2600, 3000, 3600, 0, 0, 0);

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
				boolean headerAdded = false;
				for(ApprovalSequence approvalSequence : commerceApprovalSequenceList){
					if(approvalSequence != null){
						if(!headerAdded){
							addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Approval Sequence");
							headerAdded = true;
						}else{
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Approval Sequence");
						}
						int numOfColumns = 5;
						XWPFTable commerceApprovalSequenceTable = docx.createTable(2,numOfColumns);

						XWPFTableRow headerRow = commerceApprovalSequenceTable.getRow(0);

						String[] headerNames = {"Approval Reason Name", "Approval Reason Variable Name", "Approval Reason Description", "Approver", "Approval Template"};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						setTableSize(commerceApprovalSequenceTable, 1500, 1500, 3000, 1500, 1700, 0);

						XWPFTableRow newRow = commerceApprovalSequenceTable.getRow(1);
						newRow.getCell(0).setText(approvalSequence.getLabel());
						newRow.getCell(1).setText(approvalSequence.getVariableName());
						newRow.getCell(2).setText(approvalSequence.getDescription());
						newRow.getCell(3).setText(approvalSequence.getApprover());
						newRow.getCell(4).setText(approvalSequence.getApprovalTemplate());

						if(approvalSequence.getScriptText() != null){
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Advanced Condition");
							XWPFRun scriptRun = createRun(docx.createParagraph());
							for(String scriptText : approvalSequence.getScriptText().split(";")){
								scriptRun.setText(scriptText + ";");
								scriptRun.addBreak();
							}
						}
					}
				}
			}

			docx.createParagraph();
			
/*			List<CommerceStep> commerecStepList= stories.getCommerceStepsList();
			if(commerecStepList != null && commerecStepList.size() > 0){
				boolean headerAdded = false;
				for(CommerceStep commerceStep : commerecStepList){
					if(commerceStep != null){
						if(!headerAdded){
							addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Steps");
							addText(docx, staticData.getProperty("CommerceWorkflow"));
							headerAdded =true;
						}else{
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Commerce Steps");
						}
						int numOfColumns = 6;
						int bmCmPpRows = 1;
						for(BmCmPp bmCmPp : commerceStep.getBmCmPpList()){
							bmCmPpRows++;
							if(bmCmPp.getBmCmTransRuleList().size() > 0){
								bmCmPpRows--;
								for(@SuppressWarnings("unused") BmCmTransRule bmCmTransRule : bmCmPp.getBmCmTransRuleList()){
									bmCmPpRows++;
								}
							}

						}
						if(bmCmPpRows == 1){
							bmCmPpRows = 2;
						}
						XWPFTable commerceStepTable = docx.createTable(bmCmPpRows,numOfColumns);

						XWPFTableRow headerRow = commerceStepTable.getRow(0);

						String[] headerNames = {"Step Name", "Description", "Variable Name", "Participant Profile Name", "Profile Description", "Transition Rule"};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

						setTableSize(commerceStepTable, 1200, 3000, 1500, 1200, 1400, 900);

						String stepName =  commerceStep.getStepName();
						String description =  commerceStep.getDescription();
						String variableName =  commerceStep.getVariableName();
						String participantProfileName = null;;
						String profileDescription = null;

						int rowNum = 1;
						if(commerceStep.getBmCmPpList().size() > 0){
							for(BmCmPp bmCmPp : commerceStep.getBmCmPpList()){
								participantProfileName = bmCmPp.getParticipantProfileName();
								profileDescription = bmCmPp.getProfileDescription();
								if(bmCmPp.getBmCmTransRuleList().size() > 0){
									for(BmCmTransRule bmCmTransRule : bmCmPp.getBmCmTransRuleList()){
										XWPFTableRow newRow = commerceStepTable.getRow(rowNum++);
										newRow.getCell(0).setText(stepName);
										newRow.getCell(1).setText(description);
										newRow.getCell(2).setText(variableName);
										newRow.getCell(3).setText(participantProfileName);
										newRow.getCell(4).setText(profileDescription);
										newRow.getCell(5).setText(bmCmTransRule.getTransitionRule());
									}
								}else{
									XWPFTableRow newRow = commerceStepTable.getRow(rowNum++);
									newRow.getCell(0).setText(stepName);
									newRow.getCell(1).setText(description);
									newRow.getCell(2).setText(variableName);
									newRow.getCell(3).setText(participantProfileName);
									newRow.getCell(4).setText(profileDescription);
								}

							}
						}else{
							XWPFTableRow newRow = commerceStepTable.getRow(rowNum++);
							newRow.getCell(0).setText(stepName);
							newRow.getCell(1).setText(description);
							newRow.getCell(2).setText(variableName);
						}


						if(commerceStep.getAdvancedForwardingRule() != null){
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Advanced Forwarding Rule");
							XWPFRun scriptRun = createRun(docx.createParagraph());
							for(String scriptText : commerceStep.getAdvancedForwardingRule().split(";")){
								scriptRun.setText(scriptText + ";");
								scriptRun.addBreak();
							}
						}

						for(BmCmPp bmCmPp : commerceStep.getBmCmPpList()){
							for(BmCmTransRule bmCmTransRule : bmCmPp.getBmCmTransRuleList()){
								if(bmCmTransRule.getAdvancedConditionofTransitionRule() != null){
									addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Advanced Condition of Transition Rule:");
									addSectionTitle(docx,null, false, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, "Profile Description: " + profileDescription + ", Transition Rule Name: " + bmCmTransRule.getTransitionRule());
									addSectionTitle(docx,null, false, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, "Script:");
									XWPFRun scriptRun = createRun(docx.createParagraph());
									for(String scriptText : bmCmTransRule.getAdvancedConditionofTransitionRule().split(";")){
										scriptRun.setText(scriptText + ";");
										scriptRun.addBreak();
									}
								}
							}
						}
					}
				}
			}*/
			
			List<CommerceStep> commerecStepList= stories.getCommerceStepsList();
			if(commerecStepList != null && commerecStepList.size() > 0){
				boolean headerAdded = false;
				int bmCmPpRows = 1;
				for(CommerceStep commerceStep : commerecStepList){
					for(BmCmPp bmCmPp : commerceStep.getBmCmPpList()){
						bmCmPpRows++;
						if(bmCmPp.getBmCmTransRuleList().size() > 0){
							bmCmPpRows--;
							for(@SuppressWarnings("unused") BmCmTransRule bmCmTransRule : bmCmPp.getBmCmTransRuleList()){
								bmCmPpRows++;
							}
						}
					}
				}
				if(bmCmPpRows == 1){
					bmCmPpRows = 2;
				}
				
				
				if(!headerAdded){
					addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Commerce Steps");
					if(!staticTextCommerceWorkflowAdded){
						addText(docx, staticData.getProperty("CommerceWorkflow"));
						staticTextCommerceWorkflowAdded = true;
					}
					headerAdded =true;
				}else{
					addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Commerce Steps");
				}

				int numOfColumns = 6;
				XWPFTable commerceStepTable = docx.createTable(bmCmPpRows,numOfColumns);

						XWPFTableRow headerRow = commerceStepTable.getRow(0);

						String[] headerNames = {"Step Name", "Description", "Variable Name", "Participant Profile Name", "Profile Description", "Transition Rule"};

						addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				setTableSize(commerceStepTable, 1200, 3000, 1500, 1200, 1400, 900);
				
				int rowNum = 1;
				
				for(CommerceStep commerceStep : commerecStepList){
					if(commerceStep != null){

						String stepName =  commerceStep.getStepName();
						String description =  commerceStep.getDescription();
						String variableName =  commerceStep.getVariableName();
						String participantProfileName = null;;
						String profileDescription = null;

						if(commerceStep.getBmCmPpList().size() > 0){
							for(BmCmPp bmCmPp : commerceStep.getBmCmPpList()){
								participantProfileName = bmCmPp.getParticipantProfileName();
								profileDescription = bmCmPp.getProfileDescription();
								if(bmCmPp.getBmCmTransRuleList().size() > 0){
									for(BmCmTransRule bmCmTransRule : bmCmPp.getBmCmTransRuleList()){
										XWPFTableRow newRow = commerceStepTable.getRow(rowNum++);
										newRow.getCell(0).setText(stepName);
										newRow.getCell(1).setText(description);
										newRow.getCell(2).setText(variableName);
										newRow.getCell(3).setText(participantProfileName);
										newRow.getCell(4).setText(profileDescription);
										newRow.getCell(5).setText(bmCmTransRule.getTransitionRule());
									}
								}else{
									XWPFTableRow newRow = commerceStepTable.getRow(rowNum++);
									newRow.getCell(0).setText(stepName);
									newRow.getCell(1).setText(description);
									newRow.getCell(2).setText(variableName);
									newRow.getCell(3).setText(participantProfileName);
									newRow.getCell(4).setText(profileDescription);
								}

							}
						}else{
							XWPFTableRow newRow = commerceStepTable.getRow(rowNum++);
							newRow.getCell(0).setText(stepName);
							newRow.getCell(1).setText(description);
							newRow.getCell(2).setText(variableName);
						}


						if(commerceStep.getAdvancedForwardingRule() != null){
							addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Advanced Forwarding Rule");
							XWPFRun scriptRun = createRun(docx.createParagraph());
							for(String scriptText : commerceStep.getAdvancedForwardingRule().split(";")){
								scriptRun.setText(scriptText + ";");
								scriptRun.addBreak();
							}
						}

						for(BmCmPp bmCmPp : commerceStep.getBmCmPpList()){
							for(BmCmTransRule bmCmTransRule : bmCmPp.getBmCmTransRuleList()){
								if(bmCmTransRule.getAdvancedConditionofTransitionRule() != null){
									addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, "Advanced Condition of Transition Rule:");
									addSectionTitle(docx,null, false, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, "Profile Description: " + profileDescription + ", Transition Rule Name: " + bmCmTransRule.getTransitionRule());
									addSectionTitle(docx,null, false, SECTION_TITLE_COLOR, UnderlinePatterns.NONE, "Script:");
									XWPFRun scriptRun = createRun(docx.createParagraph());
									for(String scriptText : bmCmTransRule.getAdvancedConditionofTransitionRule().split(";")){
										scriptRun.setText(scriptText + ";");
										scriptRun.addBreak();
									}
								}
							}
						}
					}
				}
			}

			
			docx.createParagraph();

			List<PrinterDocument> commercePrinterDocuments= stories.getCommercePrinterDocumentList();
			if(commercePrinterDocuments != null && commercePrinterDocuments.size() > 0){
				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Printer Friendly Documents");
				if(!staticTextPrinterFriendlyDocAdded){
					addText(docx, staticData.getProperty("PrinterFriendlyDoc"));
					staticTextPrinterFriendlyDocAdded = true;
				}
				int numOfColumns = 4;
				XWPFTable commercePrinterDocumentTable = docx.createTable(2, numOfColumns);
				XWPFTableRow headerRow = commercePrinterDocumentTable.getRow(0);

				String[] headerNames = {"Document Name", VARIABLE_NAME, DESCRIPTION, "Commerce Process Linked"};

				setTableSize(commercePrinterDocumentTable, 1600, 2500, 4100, 1000, 0, 0);

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
			
			//Add Integration
			
			docx.createParagraph();
			List<Integration> commerceIntegrationList= stories.getIntegrationsList();
			if(commerceIntegrationList != null && commerceIntegrationList.size() > 0){
				addSectionTitle(docx,"Heading3", true, SECTION_TITLE_COLOR, UnderlinePatterns.SINGLE, storyNum + "." + storySubNum++ + " " + "Integration");
				if(!staticTextIntegrationAdded){
					addText(docx, staticData.getProperty("Integration"));
					staticTextIntegrationAdded = true;
				}
				int numOfColumns = 5;
				XWPFTable commerceIntegrationsTable = docx.createTable(2, numOfColumns);
				XWPFTableRow headerRow = commerceIntegrationsTable.getRow(0);

				String[] headerNames = {"Integration Name", VARIABLE_NAME, DESCRIPTION, "ID Field", "Endpoint URL"};

				setTableSize(commerceIntegrationsTable, 1700, 1700, 2800, 1600, 1400, 0);

				addHeaderNameColorBold(headerRow, headerNames ,numOfColumns);

				int count = 1;
				for(Integration integration: commerceIntegrationList){
					if(integration != null){
						XWPFTableRow newRow = commerceIntegrationsTable.getRow(count++);
						newRow.getCell(0).setText(integration.getIntegrationName());
						newRow.getCell(1).setText(integration.getVariableName());
						newRow.getCell(2).setText(integration.getDescription());
						newRow.getCell(3).setText(integration.getIdField());
						newRow.getCell(4).setText(integration.getEndpointURL());
					}

					if(integration.getIntegrationScriptList() != null){
						docx.createParagraph();
						addSectionTitle(docx,null, true, SECTION_TITLE_COLOR, 
								UnderlinePatterns.SINGLE, "Integration Details");
						List<IntegrationScript> integrationScriptList = integration.getIntegrationScriptList();
						Collections.sort(integrationScriptList, new Comparator<IntegrationScript>() {

							@Override
							public int compare(IntegrationScript o1, IntegrationScript o2) {
								if("SOAP Generator XSL".equalsIgnoreCase(o1.getIntegrationScriptName()) && o1.getIntegrationScriptName().equalsIgnoreCase(o2.getIntegrationScriptName())){
									return 0;
								}else if("SOAP Generator XSL".equalsIgnoreCase(o1.getIntegrationScriptName())){
									return -1;
								}else{
									return 1;
								}
							}
						});
						for(IntegrationScript integrationScript : integrationScriptList){
							addSectionTitle(docx,null, false, SECTION_TITLE_COLOR, 
									UnderlinePatterns.SINGLE, integrationScript.getIntegrationScriptName());
							XWPFRun run = createRun(docx.createParagraph());
							for(String str : integrationScript.getDescription().split(Constants.LINE_DELIMITER)){
								run.setText(str);
								run.addBreak();
							}
						}
					}
				}
			}
			
			docx.createParagraph().setPageBreak(true);
			storyNum++;
		}
	}
	
	public XWPFRun createRun(XWPFParagraph paragraph){
		XWPFRun run = paragraph.createRun();
		run.setFontFamily(FONT_FAMILY);
		run.setFontSize(NORMAL_FONT_SIZE);
		return run;
	}

}

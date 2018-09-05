package translate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocxFileConverter {

	public void docxFileConverter(File file, List<Attribute> attributeList, List<Rule> ruleList, Map<String, List<Util>> utilMap) throws Exception {
		XWPFDocument docx = openDocxFile(file);
		if (docx != null) {
			
			// Add Atrribute List
			XWPFParagraph paragraph = docx.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText("Atrribute Table",0);
			
			XWPFTable attributeTable = docx.createTable();
            XWPFTableRow attributeRow = attributeTable.getRow(0);
            attributeRow.getCell(0).setText("Name");
            attributeRow.addNewTableCell().setText("Variable Name");
            attributeRow.addNewTableCell().setText("Data Type");

            for (Attribute attribute : attributeList) {
                XWPFTableRow tempRow = attributeTable.createRow();
                tempRow.getCell(0).setText(attribute.getName());
                tempRow.getCell(1).setText(attribute.getVariableName());
                tempRow.getCell(2).setText(attribute.getDataType());
            }
            
            // Add Rule List
            paragraph = docx.createParagraph();
			run = paragraph.createRun();
			run.setText("Rule Table",0);
			
            XWPFTable ruleTable = docx.createTable();
            XWPFTableRow ruleRow = ruleTable.getRow(0);
            ruleRow.getCell(0).setText("Name");
            ruleRow.addNewTableCell().setText("Variable Name");
            ruleRow.addNewTableCell().setText("Rule Type");

            for (Rule rule : ruleList) {
                XWPFTableRow tempRow = ruleTable.createRow();
                tempRow.getCell(0).setText(rule.getName());
                tempRow.getCell(1).setText(rule.getVariableName());
                tempRow.getCell(2).setText(rule.getRuleType());
            }
            
            // Add Data Table List
            
            // Add Util Libraries map
            paragraph = docx.createParagraph();
			run = paragraph.createRun();
			run.setText("Util Libraries Table",0);
			XWPFTable utilTable = docx.createTable();
            XWPFTableRow UtilTableRow = utilTable.getRow(0);
            UtilTableRow.getCell(0).setText("Name");
            UtilTableRow.addNewTableCell().setText("Variable Name");
            UtilTableRow.addNewTableCell().setText("Script Text");
			for(String utilName : utilMap.keySet()){
	            for (Util util : utilMap.get(utilName)) {
	                XWPFTableRow tempRow = utilTable.createRow();
	                tempRow.getCell(0).setText(util.getName());
	                tempRow.getCell(1).setText(util.getVariableName());
	                tempRow.getCell(2).setText(util.getScriptText());
	            }
			}
            
			saveDocxFile(docx, Constants.SOURCE_FILE.replace("input", "output"));
		}
	}

	/* Replaces table */
	/*private long replaceTable(XWPFDocument doc) {
		XWPFTable table = null;
		long count = 0;
		for (XWPFParagraph paragraph : doc.getParagraphs()) {
			List<XWPFRun> runs = paragraph.getRuns();
			String find = "%TABLE";
			TextSegement found = paragraph.searchText(find, new PositionInParagraph());
			if ( found != null ) {
				count++;
				if ( found.getBeginRun() == found.getEndRun() ) {
					// whole search string is in one Run
					XmlCursor cursor = paragraph.getCTP().newCursor();
					table = doc.insertNewTbl(cursor);
					XWPFRun run = runs.get(found.getBeginRun());
					// Clear the "%TABLE" from doc
					String runText = run.getText(run.getTextPosition());
					String replaced = runText.replace(find, "");
					run.setText(replaced, 0);
				} else {
					// The search string spans over more than one Run
					StringBuilder b = new StringBuilder();
					for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
						XWPFRun run = runs.get(runPos);
						b.append(run.getText(run.getTextPosition()));
					}                       
					String connectedRuns = b.toString();
					XmlCursor cursor = paragraph.getCTP().newCursor();
					table = doc.insertNewTbl(cursor);
					String replaced = connectedRuns.replace(find, ""); // Clear search text

					// The first Run receives the replaced String of all connected Runs
					XWPFRun partOne = runs.get(found.getBeginRun());
					partOne.setText(replaced, 0);
					// Removing the text in the other Runs.
					for (int runPos = found.getBeginRun()+1; runPos <= found.getEndRun(); runPos++) {
						XWPFRun partNext = runs.get(runPos);
						partNext.setText("", 0);
					}
				}
			}     
		}
		return count;
	}*/

	private XWPFDocument openDocxFile(File file) throws Exception {
		XWPFDocument document = null;
		if (file != null && file.exists()) {
			document = new XWPFDocument(new FileInputStream(file));
		}
		return document;
	}

	private void saveDocxFile(XWPFDocument doc, String file) {
		try (FileOutputStream out = new FileOutputStream(file)) {
			doc.write(out);
			doc.close();
			System.out.println("Successfully created new file at location : " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

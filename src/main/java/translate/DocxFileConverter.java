package translate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocxFileConverter {

	public void docxFileConverter(File file, List<Attribute> attributeList, List<Rule> ruleList) throws Exception {
		XWPFDocument docx = openDocxFile(file);
		if (docx != null) {
			
			// Add Atrribute List
			XWPFTable attributeTable = docx.createTable();
            XWPFTableRow tableRowOne = attributeTable.getRow(0);
            tableRowOne.getCell(0).setText("Name");
            tableRowOne.addNewTableCell().setText("Variable Name");
            tableRowOne.addNewTableCell().setText("Data Type");

            for (Attribute attribute : attributeList) {
                XWPFTableRow tempRow = attributeTable.createRow();
                tempRow.getCell(0).setText(attribute.getName());
                tempRow.getCell(1).setText(attribute.getVariableName());
                tempRow.getCell(2).setText(attribute.getDataType());
            }
            
            // Add Rule List
            XWPFTable ruleTable = docx.createTable();
            XWPFTableRow tableRowTwo = ruleTable.getRow(0);
            tableRowTwo.getCell(0).setText("Name");
            tableRowTwo.addNewTableCell().setText("Variable Name");
            tableRowTwo.addNewTableCell().setText("Rule Type");

            for (Rule rule : ruleList) {
                XWPFTableRow tempRow = ruleTable.createRow();
                tempRow.getCell(0).setText(rule.getName());
                tempRow.getCell(1).setText(rule.getVariableName());
                tempRow.getCell(2).setText(rule.getRuleType());
            }
            
			saveDocxFile(docx, Constants.SOURCE_FILE.replace("input", "output"));
		}
	}

	/*private void translateParagraph(List<XWPFParagraph> paragraphs) {
		StringBuilder sb = null;
		for (XWPFParagraph paragraph : paragraphs) {
			if(StringUtils.isNotBlank(paragraph.getParagraphText())){
				System.out.println("paragraph : " + paragraph.getParagraphText());
				String sentences[] = OpenNLPDetect.getNLPDetector().getSentences(paragraph.getParagraphText());
				sb = new StringBuilder();
				for(String sentence : sentences){
					sb.append(sentence.toUpperCase());
				}
				List<XWPFRun> runs = paragraph.getRuns();
				if(runs.size() > 0){
					for(int i = runs.size()-1 ; i>0 ; i--){
						XWPFRun run = runs.get(i);
						if(StringUtils.isNotBlank(run.getText(run.getTextPosition()))){
							if(!(run instanceof XWPFHyperlinkRun ||
									run instanceof XWPFFieldRun)){
								paragraph.removeRun(i);
							}
							else{
								System.out.println("Cannot remove Hyperlink : " +run.getText(run.getTextPosition()));
							}
						}
					}
					XWPFRun run = runs.get(0);
					run.setText(sb.toString(),0);
				}
			}
		}
	}*/
	
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

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

	public void docxFileConverter(File file, List<Attribute> attributeList) throws Exception {
		XWPFDocument docx = openDocxFile(file);
		if (docx != null) {
			XWPFTable table = docx.createTable();
            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setText("Name");
            tableRowOne.addNewTableCell().setText("Variable Name");
            tableRowOne.addNewTableCell().setText("Data Type");

            for (Attribute attribute : attributeList) {
                XWPFTableRow tempRow = table.createRow();
                tempRow.getCell(0).setText(attribute.getName());
                tempRow.getCell(1).setText(attribute.getVariableName());
                tempRow.getCell(2).setText(attribute.getDataType());
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

package translate;

import java.io.File;
import java.util.List;

public class XMLConverter {

	public static void main(String[] args) throws Exception {

		File file = null;
		try {
			//Read Attr XML
			file = new File(Constants.SOURCE_ATTR_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			List<Attribute> attributeList = null;
			XMLReader xmlReader = new XMLReader();
			attributeList = xmlReader.readAttrXML(file);
			System.out.println(attributeList);
			
			//Read Rules XML
			List<Rule> ruleList = null;
			file = new File(Constants.SOURCE_RULE_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			ruleList = xmlReader.readRuleXML(file);
			System.out.println(ruleList);
			
			//Read Data table XML
			file = new File(Constants.SOURCE_FILE);
			if (!file.exists()) {
				throw new Exception("Source file not present at location : " + file.getAbsolutePath());
			}
			DocxFileConverter converter = new DocxFileConverter();
			converter.docxFileConverter(file,attributeList,ruleList);
		} catch (Exception e) {
			throw e;
		}
	}
}

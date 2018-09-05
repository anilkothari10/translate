package translate;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			
			//Read UTIL Libraries
			Map<String,List<Util>> utilMap = new HashMap<String,List<Util>>();
			List<Util> utilList = null;
			file = new File(Constants.SOURCE_UTIL_LIBRARIES_DIR);
			File[] folderArray = file.listFiles();
			if(folderArray != null && folderArray.length > 0){
				for(File utilFolder : folderArray){
					if(utilFolder.exists() && utilFolder.isDirectory()){
						//System.out.println("utilFolder : " + utilFolder);
						File[] utilFiles = utilFolder.listFiles();
						for(File utilFile : utilFiles){
							if(utilFile.exists() && utilFile.getName().contains("libfunc.xml")){
								//System.out.println("utilFile : " + utilFile);
								utilList = xmlReader.readUtilXML(utilFile);
							}
						}
						utilMap.put(utilFolder.getName().toString().trim(), utilList);
					}
				}
			}
			System.out.println(utilMap);
			file = new File(Constants.SOURCE_FILE);
			if (!file.exists()) {
				throw new Exception("Source file not present at location : " + file.getAbsolutePath());
			}
			DocxFileConverter converter = new DocxFileConverter();
			converter.docxFileConverter(file,attributeList,ruleList,utilMap);
		} catch (Exception e) {
			throw e;
		}
	}
}

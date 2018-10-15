package translate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import translate.commerce.CommerceComponents;

public class XMLConverter {

	public static void main(String[] args) throws Exception {

		Printer.DEBUG = false;
		Printer.INFO = true;
		File file = null;
		
		List<UserStories> userStoriesRules = new ArrayList<UserStories>();
		try {
			XMLReader xmlReader = new XMLReader();

			file = new File(Constants.SOURCE_RULE_ATTRIBUTE_DIR);
			File[] ruleAttributeFileList = file.listFiles();
			if(ruleAttributeFileList != null && ruleAttributeFileList.length > 0){
				Printer.println("#### Number of files in Rules_Attributes folder = " + ruleAttributeFileList.length);
				for(File ruleAttributeFile : ruleAttributeFileList){
					if (!ruleAttributeFile.exists()) {
						throw new Exception("Input file not present at location : " + ruleAttributeFile.getAbsolutePath());
					}
					
					Printer.println("#### Reading Rules from : "+ ruleAttributeFile.getName());
					
					xmlReader.readConfigRuleXML(ruleAttributeFile, "bm_config_rule", userStoriesRules);
					
					Printer.println("#### Reading Attributes from : "+ ruleAttributeFile.getName());
					
					xmlReader.readConfigAttributeXML(ruleAttributeFile, "bm_config_attr", userStoriesRules);
				}
			}

			//Read UserStories from UTIL Libraries
			file = new File(Constants.SOURCE_UTIL_LIBRARIES_DIR);
			File[] fileList = file.listFiles();
			if(fileList != null && fileList.length > 0){
				
				Printer.println("#### Reading UTIL Libraries");
				
				for(File utilFolder : fileList){
					if(utilFolder.exists() && utilFolder.isDirectory()){
						File[] utilFiles = utilFolder.listFiles();
						if(utilFiles != null && utilFiles.length > 0){
							for(File utilFile : utilFiles){
								if(utilFile.exists() && utilFile.getName().contains("libfunc.xml")){
									xmlReader.readUtilsXML(utilFile, "bm_lib_func", userStoriesRules);
								}
							}
						}
					}
				}
			}
			
			// Read Commerce xml files
			List<CommerceComponents> commerceComponents = new ArrayList<CommerceComponents>();
			//Process.xml
			file = new File(Constants.SOURCE_COMMERCE_PROCESS_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			xmlReader.readCommerceProcessXML(file, "bm_cm_action", "bm_cm_reason", commerceComponents, userStoriesRules);
			
			file = new File(Constants.SOURCE_COMMERCE_DOCEDDOCUMENT_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			xmlReader.readCommerceDocEdDocumentXML(file, "bm_doc_ed_document", commerceComponents, userStoriesRules);
			//System.out.println(commerceComponents);
			
			//Read Data table XML
			List<DataTable> dataTableList = new ArrayList<DataTable>();
			file = new File(Constants.SOURCE_DATA_TABLE_DIR);
			File[] dataTableFileList = file.listFiles();
			if(dataTableFileList != null && dataTableFileList.length > 0){
				
				Printer.println("#### Reading Data table XMLs. Number of files in Data_Tables folder: " + dataTableFileList.length);
				
				for(File dataTableFile : dataTableFileList){
					if (!dataTableFile.exists()) {
						throw new Exception("Input file not present at location : " + dataTableFile.getAbsolutePath());
					}
					
					Printer.println("#### Reading Data table from : "+ dataTableFile.getName());
					
					dataTableList.addAll(xmlReader.readDataTableXML(dataTableFile));
				}
			}

			//read user
			file = new File(Constants.SOURCE_USERS_GROUPS_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			
			Printer.println("#### Reading Users");
			
			List<Users> usersList = null;
			usersList = xmlReader.readUsersXML(file);
			
			//read Groups
			file = new File(Constants.SOURCE_USERS_GROUPS_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			
			Printer.println("#### Reading Groups");
			
			List<Groups> groupList= null;
			groupList = xmlReader.readGroupsXML(file);
			
			Printer.println(userStoriesRules.toString());

			Printer.println("#### Number of User Stories        : " + userStoriesRules.size());
			Printer.println("#### Number of Data Tables Entries : " + dataTableList.size());
			Printer.println("#### Number of Users               : " + usersList.size());
			Printer.println("#### Number of Groups              : " + groupList.size());

			DocxFileConverter converter = new DocxFileConverter();
			converter.docxFileConverter(file, userStoriesRules, commerceComponents, dataTableList, usersList, groupList);
		} catch (Exception e) {
			throw e;
		}
	}
}

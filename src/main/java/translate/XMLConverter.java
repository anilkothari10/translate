package translate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import utilities.FileSearch;

public class XMLConverter {

	public static void main(String[] args) throws Exception {

		Printer.DEBUG = false;
		Printer.INFO = true;
		File file = null;
		
		List<UserStories> userStoriesRules = new ArrayList<UserStories>();
		try {
			System.out.println("Please input the source directory:");
			Scanner scanner = new Scanner(System.in);
			String inputDir = scanner.nextLine();
			if(StringUtils.isBlank(inputDir)){
				System.out.println("Reading files from default directory...");
				inputDir = Constants.SOURCE_RULE_ATTRIBUTE_DIR;
			}
			else{
				System.out.println("Reading files from input directory...");
			}
			scanner.close();
			
			XMLReader xmlReader = new XMLReader();
			file = new File(inputDir);
			File ruleAttributeFile = FileSearch.searchFileOrDirectory(file, "config.xml");
			if (ruleAttributeFile != null) {
				Printer.println("#### Reading Rules from : "+ ruleAttributeFile.getName());
				xmlReader.readConfigRuleXML(ruleAttributeFile, "bm_config_rule", userStoriesRules);
				Printer.println("#### Reading Attributes from : "+ ruleAttributeFile.getName());
				xmlReader.readConfigAttributeXML(ruleAttributeFile, "bm_config_attr", userStoriesRules);
			}
			else{
				Printer.println("config.xml file not present");
			}

			//Read UserStories from UTIL Libraries
			file = new File(inputDir);
			File UtilDir = FileSearch.searchFileOrDirectory(file, "UTIL Libraries");
			if(UtilDir != null && UtilDir.exists()){
				Printer.println("#### Reading UTIL Libraries");
				for(File utilFolder : UtilDir.listFiles()){
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
			}else{
				Printer.println("UTIL Libraries not present");
			}
			
			//Read commerce files
			file = new File(inputDir);
			File commerceDir = FileSearch.searchFileOrDirectory(file, "Commerce");
			List<UserStories> userStoriesCommerce = readCommerceXMLs(commerceDir, xmlReader);
			
			//Read Web services files
			file = new File(inputDir);
			File webServices = FileSearch.searchFileOrDirectory(file, "WebServices");
			File[] webservicesList = webServices.listFiles();
			if(webservicesList != null && webservicesList.length > 0){
				xmlReader.readWebServicesFiles(webservicesList,userStoriesCommerce);
			}
			
			//Read JS files
			file = new File(inputDir);
			File jsFiles = FileSearch.searchFileOrDirectory(file, "Javascript");
			File[] jsList = jsFiles.listFiles();
			if(jsList != null && jsList.length > 0){
				xmlReader.readJavascriptFiles(jsList,userStoriesCommerce);
			}
			
			//Read Integration
			file = new File(inputDir);
			File integrationFiles = FileSearch.searchFileOrDirectory(file, "Integration");
			File[] integrationScriptList = integrationFiles.listFiles();
			if(integrationScriptList != null && integrationScriptList.length > 0){
				xmlReader.readIntegrationScriptFiles(integrationScriptList,userStoriesCommerce);
			}
			
			//Read Data table XML
			file = new File(inputDir);
			File dataTableFiles = FileSearch.searchFileOrDirectory(file, "Data_Tables");
			File[] dataTableFileList = dataTableFiles.listFiles();
			List<DataTable> dataTableList = new ArrayList<DataTable>();
			if(dataTableFileList != null && dataTableFileList.length > 0){
				
				Printer.println("#### Reading Data table XMLs. Number of files in Data_Tables folder: " + dataTableFileList.length);
				
				for(File dataTableFile : dataTableFileList){
					if (!dataTableFile.exists()) {
						throw new Exception("Input file not present at location : " + dataTableFile.getAbsolutePath());
					}
					
					Printer.println("#### Reading Data table from : "+ dataTableFile.getName());
					
					dataTableList.addAll(xmlReader.readDataTableXML(dataTableFile));
				}
			}else{
				Printer.println("Input files not present at location : "+ file.getAbsolutePath());
			}

			//read user and Groups
			List<Groups> groupList= new ArrayList<Groups>();
			List<Users> usersList = new ArrayList<Users>();
			file = new File(inputDir);
			File userGroupFiles = FileSearch.searchFileOrDirectory(file, "UserGroups");
			File[] userGroupFileList = userGroupFiles.listFiles();
			if(userGroupFileList != null && userGroupFileList.length > 0){
				for(File userGroupFile : userGroupFileList){
					if (!userGroupFile.exists()) {
						throw new Exception("Input file not present at location : " + file.getAbsolutePath());
					}
					//read user
					Printer.println("#### Reading Users");
					usersList = xmlReader.readUsersXML(userGroupFile);
					
					//read Groups
					Printer.println("#### Reading Groups");
					groupList = xmlReader.readGroupsXML(userGroupFile);
				}
			}else{
				Printer.println("Input files not present at location : "+ file.getAbsolutePath());
			}

			Printer.println("#### Number of Config User Stories   : " + userStoriesRules.size());
			Printer.println("#### Number of Data Tables Entries   : " + dataTableList.size());
			Printer.println("#### Number of Users                 : " + usersList.size());
			Printer.println("#### Number of Groups                : " + groupList.size());
			Printer.println("#### Number of Commerce User Stories : " + userStoriesCommerce.size());

			DocxFileConverter converter = new DocxFileConverter();
			converter.setInputFile(new File(inputDir));
			converter.docxFileConverter(userStoriesRules, userStoriesCommerce, dataTableList, usersList, groupList);
		} catch (Exception e) {
			throw e;
		}
	}
	
	

	private static List<UserStories> readCommerceXMLs(File file, XMLReader xmlReader) throws Exception{
		//Read Commerce attribute, Libraries, Rules
		List<UserStories> userStoriesCommerce = new ArrayList<UserStories>();
	
		File processDir = FileSearch.searchFileOrDirectory(file, "Process");
		File[] commerceProcessList = processDir.listFiles();
		if(commerceProcessList != null && commerceProcessList.length > 0){
			for(File commerceProcessFile : commerceProcessList){
				//Read Commerce attribute
				Printer.println("#### Reading Commerce attribute");
				xmlReader.readCommerceAttributeXML(commerceProcessFile, "bm_cm_attribute", userStoriesCommerce);
				
				//Read Commerce Libraries
				Printer.println("#### Reading Commerce Libraries");
				xmlReader.readCommerceLibrariesXML(commerceProcessFile, "bm_lib_func", userStoriesCommerce);
				
				//Read Commerce Rules
				Printer.println("#### Reading Commerce Rules");
				xmlReader.readCommerceRulesXML(commerceProcessFile, "bm_cm_rule", userStoriesCommerce);
				
				//Read Commerce Actions
				Printer.println("#### Reading Commerce Actions");
				xmlReader.readCommerceActionXML(commerceProcessFile, "bm_cm_action", userStoriesCommerce);
				
				//Read Commerce Approval Sequence
				Printer.println("#### Reading Commerce Approval Sequence");
				xmlReader.readCommerceSequenceXML(commerceProcessFile, "bm_cm_reason", userStoriesCommerce);
				
				//Read Commerce Steps
				Printer.println("#### Reading Commerce steps");
				xmlReader.readCommerceStepsXML(commerceProcessFile, "bm_cm_step", userStoriesCommerce);
				
				//Read Integration
				Printer.println("#### Reading Commerce Integration");
				xmlReader.readCommerceIntegration(commerceProcessFile, "bm_cm_integration", userStoriesCommerce);
			}
		}else{
			Printer.println("Input files not present at location : "+ file.getAbsolutePath());
		}

		//Read Commerce Printer Friendly Documents 
		File docEDDir = FileSearch.searchFileOrDirectory(file, "DocEdDocument");
		File[] commerceDocEdDocumentList = docEDDir.listFiles();
		if(commerceDocEdDocumentList != null && commerceDocEdDocumentList.length > 0){
			Printer.println("#### Reading Printer Friendly Documents");
			Printer.println("#### Number of files in Commerce/DocEdDocuments folder = " + commerceDocEdDocumentList.length);
			for(File commerceDocEdDocumentFile : commerceDocEdDocumentList){
				if (!commerceDocEdDocumentFile.exists()) {
					throw new Exception("Input file not present at location : " + commerceDocEdDocumentFile.getAbsolutePath());
				}
				Printer.println("#### Reading Printer Friendly Document from : "+ commerceDocEdDocumentFile.getName());
				
				xmlReader.readCommerceDocEdDocumentXML(commerceDocEdDocumentFile, "bm_doc_ed_document", userStoriesCommerce);
			}
		}else{
			Printer.println("Input files not present at location : "+ file.getAbsolutePath());
		}
		return userStoriesCommerce;
	}
}

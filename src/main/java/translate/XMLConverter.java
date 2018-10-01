package translate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

//			for(UserStories userStories : userStoriesRules){
//				Printer.print(userStories.getUserStoryNum() + "\t");
//				if(userStories.getAttribute != null){
//					Printer.print(userStories.getAttribute().toString() + "\t");
//				}else{
//					Printer.print("Attribute is null\t");
//				}
//				if(userStories.getRule() != null){
//					Printer.print(userStories.getRule().toString() + "\n\n\n");
//				}else{
//					Printer.print("Rule is null\n\n\n");
//				}
//			}


//			System.exit(0);
			//Read UserStories from Rules
//			file = new File(Constants.SOURCE_RULE_XML_FILE);
//			if (!file.exists()) {
//				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
//			}
//			List<UserStories> userStoriesRules = null;
//			xmlReader = new XMLReader();
//			userStoriesRules = xmlReader.readUserStoriesFromRulesXML(file, "each_record");
//			//System.out.println("User Stories Rules: "+userStoriesRules);
//			
//			
//			//Read UserStories from UTIL Libraries
//			Map<String,List<UserStories>> userStoryMap = new HashMap<String,List<UserStories>>();
//			List<UserStories> userStoriesUtils = null;
//			file = new File(Constants.SOURCE_UTIL_LIBRARIES_DIR);
//			File[] fileList = file.listFiles();
//			if(fileList != null && fileList.length > 0){
//				for(File utilFolder : fileList){
//					if(utilFolder.exists() && utilFolder.isDirectory()){
//						//System.out.println("utilFolder : " + utilFolder);
//						File[] utilFiles = utilFolder.listFiles();
//						for(File utilFile : utilFiles){
//							if(utilFile.exists() && utilFile.getName().contains("libfunc.xml")){
//								//System.out.println("utilFile : " + utilFile);
//								userStoriesUtils = xmlReader.readUserStoriesFromRulesXML(utilFile, "bm_lib_func");
//							}
//						}
//						userStoryMap.put(utilFolder.getName().toString().trim(), userStoriesUtils);
//					}
//				}
//			}
////			for(String utilName : utilMap.keySet()){
////				for (UserStories userStory : utilMap.get(utilName)) {
////					System.out.println(userStory);
////				}
////			}
//			
//			List<UserStories> userStories = new ArrayList<UserStories>();
//			userStories.addAll(userStoriesRules);
//			for(String utilName : userStoryMap.keySet()){
//				for (UserStories userStory : userStoryMap.get(utilName)) {
////					System.out.println(userStory.getUserStoryNum());
//					userStories.add(userStory);
//				}
//			}
//			
////			for(UserStories story :userStories){
////				System.out.println(story);
////			}
//			
//			
//
//			//Read Attr XML
//			file = new File(Constants.SOURCE_ATTR_XML_FILE);
//			if (!file.exists()) {
//				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
//			}
//			List<Attribute> attributeList = null;
//			xmlReader = new XMLReader();
//			attributeList = xmlReader.readAttrXML(file);
////			System.out.println(attributeList);
//			
//			//Read Rules XML
//			List<Rule> ruleList = null;
//			file = new File(Constants.SOURCE_RULE_XML_FILE);
//			if (!file.exists()) {
//				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
//			}
//			ruleList = xmlReader.readRuleXML(file, userStories);
//
//			
////			System.out.println(ruleList);
//			
//			//Read Data table XML
//			List<DataTable> dataTableList = null;
//			file = new File(Constants.SOURCE_DATA_TABLE_XML_FILE);
//			if (!file.exists()) {
//				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
//			}
//			dataTableList = xmlReader.readDataTableXML(file);
//			//System.out.println(dataTableList);
//			
//			//Read UTIL Libraries
//			Map<String,List<Util>> utilMap = new HashMap<String,List<Util>>();
//			List<Util> utilList = null;
//			file = new File(Constants.SOURCE_UTIL_LIBRARIES_DIR);
//			File[] folderArray = file.listFiles();
//			if(folderArray != null && folderArray.length > 0){
//				for(File utilFolder : folderArray){
//					if(utilFolder.exists() && utilFolder.isDirectory()){
//						//System.out.println("utilFolder : " + utilFolder);
//						File[] utilFiles = utilFolder.listFiles();
//						for(File utilFile : utilFiles){
//							if(utilFile.exists() && utilFile.getName().contains("libfunc.xml")){
//								//System.out.println("utilFile : " + utilFile);
//								utilList = xmlReader.readUtilXML(utilFile, userStories);
//							}
//						}
//						utilMap.put(utilFolder.getName().toString().trim(), utilList);
//					}
//				}
//			}
//			
////			for (UserStories userStory : userStories) {
////				System.out.println(userStory.getUserStoryNum());
////				System.out.println(userStory.getRule());
////				System.out.println(userStory.getUtil());
////			}
//			
////			
////			for(UserStories story :userStories){
////				if(!story.getUserStoryNum().isEmpty()){
////				System.out.println("Story: "+story.getUserStoryNum());
////				System.out.println("Rule: " + story.getRule());
////				System.out.println("Utils: "+story.getUtil());
////				}
////			}
//			
//			
//			//read user
//			file = new File(Constants.SOURCE_USERS_GROUPS_XML_FILE);
//			if (!file.exists()) {
//				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
//			}
//			List<Users> usersList = null;
//			usersList = xmlReader.readUsersXML(file);
////			System.out.println(usersList);
//			
//			
//			//read Groups
//			file = new File(Constants.SOURCE_USERS_GROUPS_XML_FILE);
//			if (!file.exists()) {
//				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
//			}
//			List<Groups> groupList= null;
//			groupList = xmlReader.readGroupsXML(file);
////			System.out.println(groupList);
//
//			file = new File(Constants.SOURCE_FILE);
//			if (!file.exists()) {
//				throw new Exception("Source file not present at location : " + file.getAbsolutePath());
//			}
//			DocxFileConverter converter = new DocxFileConverter();
//			converter.docxFileConverter(file,attributeList,ruleList,utilMap,dataTableList, usersList, groupList, userStories);

			Printer.println("#### Number of User Stories        : " + userStoriesRules.size());
			Printer.println("#### Number of Data Tables Entries : " + dataTableList.size());
			Printer.println("#### Number of Users               : " + usersList.size());
			Printer.println("#### Number of Groups              : " + groupList.size());

			DocxFileConverter converter = new DocxFileConverter();
			converter.docxFileConverter(file, userStoriesRules, dataTableList, usersList, groupList);
		} catch (Exception e) {
			throw e;
		}
	}
}

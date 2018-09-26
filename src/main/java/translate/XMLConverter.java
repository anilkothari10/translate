package translate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLConverter {

	public static void main(String[] args) throws Exception {

		File file = null;
		Printer printer = new Printer(true);
		List<UserStories> userStoriesRules = new ArrayList<UserStories>();
		try {
			printer.setDebug(false);
			file = new File(Constants.SOURCE_CONFIG_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			List<Rule> configRules = null;
			XMLReader xmlReader = new XMLReader();
			printer.setDebug(true);
			configRules = xmlReader.readConfigRuleXML(file, "bm_config_rule", userStoriesRules);
			printer.setDebug(false);
			for(Rule rule : configRules){
				Printer.print(rule.getName()+"\t\t");
				Printer.print(rule.getVariableName()+"\t\t");
				Printer.print(rule.getRuleType()+"\t\t");
				Printer.print(rule.getDescription() + "\n\n");
			}
			
			file = new File(Constants.SOURCE_CONFIG_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			List<Attribute> configAttribute = null;
			xmlReader = new XMLReader();
			printer.setDebug(true);
			configAttribute = xmlReader.readConfigAttributeXML(file, "bm_config_attr", userStoriesRules);
			printer.setDebug(false);

			//Read UserStories from UTIL Libraries
			file = new File(Constants.SOURCE_UTIL_LIBRARIES_DIR);
			File[] fileList = file.listFiles();
			if(fileList != null && fileList.length > 0){
				List<Util> utilList = new ArrayList<Util>();
				for(File utilFolder : fileList){
					if(utilFolder.exists() && utilFolder.isDirectory()){
						File[] utilFiles = utilFolder.listFiles();
						for(File utilFile : utilFiles){
							if(utilFile.exists() && utilFile.getName().contains("libfunc.xml")){
								//userStoriesUtils = xmlReader.readUserStoriesFromRulesXML(utilFile, "bm_lib_func");
								utilList.addAll(xmlReader.readUtilsXML(utilFile, "bm_lib_func", userStoriesRules));
							}
						}
					}
				}
			}
			
			
			//Read Data table XML
			List<DataTable> dataTableList = null;
			file = new File(Constants.SOURCE_DATA_TABLE_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			dataTableList = xmlReader.readDataTableXML(file);
			
			
			//read user
			file = new File(Constants.SOURCE_USERS_GROUPS_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			List<Users> usersList = null;
			usersList = xmlReader.readUsersXML(file);
			
			
			//read Groups
			file = new File(Constants.SOURCE_USERS_GROUPS_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			List<Groups> groupList= null;
			groupList = xmlReader.readGroupsXML(file);
			
			Printer.print("###############Attribute###############");

			for(Attribute attribute: configAttribute){
				Printer.print(attribute.getName()+"\t\t");
				Printer.print(attribute.getVariableName()+"\t\t");
				Printer.print(attribute.getDataType()+"\t\t");
				Printer.print(attribute.getDescription() + "\n\n");
			}

			printer.setDebug(true);
			Printer.print("###############Stories###############");

			Printer.print("\n"+userStoriesRules.size()+"\n");
			
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
			DocxFileConverter converter = new DocxFileConverter();
			converter.docxFileConverter(file, userStoriesRules, dataTableList, usersList, groupList);
		} catch (Exception e) {
			throw e;
		}
	}
}

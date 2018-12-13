package translate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	public static final String SOURCE_CONFIG_FILE = "files/input/config.xml";
	public static final String SOURCE_FILE = "files/input/Sample TDD.docx";
	public static final String SOURCE_ATTR_XML_FILE = "files/input/config_attr_1534771316317.xml";
	public static final String SOURCE_RULE_XML_FILE = "files/input/config_rule_1534771184976.xml";
	public static final String SOURCE_DATA_TABLE_XML_FILE = "files/input/Data Tables.xml";
	public static final String SOURCE_UTIL_LIBRARIES_DIR = "files/input/UTIL Libraries/";
	public static final String SOURCE_RULE_ATTRIBUTE_DIR = "files/input/Rules_Attributes/";
	public static final String SOURCE_DATA_TABLE_DIR = "files/input/Data_Tables/";
	public static final String SOURCE_USERS_GROUPS_XML_FILE = "files/input/UserGroups/";
	public static final String XML = "xml";
	public static final String SECTIONTITLECOLOR = "800000";
	public static final String SOURCE_COMMERCE_PROCESS_FILE = "files/input/Commerce/Process/";
	public static final String SOURCE_COMMERCE_DOCEDDOCUMENT_FILE = "files/input/Commerce/DocEdDocument/";
	public static final String CONFIGURATION_SECTION = "Configuration Section";
	public static final String COMMERCE_SECTION = "Commerce Section";
	public static final String RULES_SECTION = "Rules";
	public static final String FILE_MANAGER_SECTION = "File Manager";
	public static final String TOC = "Table of Contents";
	public static final String WEB_SERVICES_DIR = "files/input/WebServices/";
	public static final String INTEGRATION_DIR = "files/input/Integration/";
	public static final String JS_DIR = "files/input/Javascript/";
	public static final List<String> SECTION_TITILES = new ArrayList<String>(Arrays.asList(CONFIGURATION_SECTION, COMMERCE_SECTION,FILE_MANAGER_SECTION,TOC));
	public static final int SECTION_FONT_SIZE = 15;
	public static final String DELOITTE_LOGO = "files/input/Deloitte_logo.png";
	public static final String LINE_DELIMITER = "@!";
	public static final String PROPERTIES_FILE = "properties/font.properties";
	public static final String STATIC_DATA_PROPERTIES_FILE = "properties/staticText.properties";
	
	private static Map<String, String> dataTypesMap = null;
	
	public static Map<String, String> getDataTypes(){
		if(dataTypesMap == null){
			Printer.println("Initializing Data Types Map");
			dataTypesMap = new HashMap<>();
			dataTypesMap.put("1", "Text");
			dataTypesMap.put("2", "Float");
			dataTypesMap.put("3", "Integer");
			dataTypesMap.put("4", "Boolean");
			dataTypesMap.put("5", "Date");
			dataTypesMap.put("6", "HTML");
			dataTypesMap.put("7", "Currency");
			Printer.println("Data Types Map Initialized");
		}
		return dataTypesMap;
	}

}

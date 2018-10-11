package translate;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String DOC_EXT = "doc";
	public static final String DOCX_EXT = "docx";
	public static final String SOURCE_CONFIG_FILE = "files/input/config.xml";
	public static final String SOURCE_FILE = "files/input/Sample TDD.docx";
	public static final String SOURCE_ATTR_XML_FILE = "files/input/config_attr_1534771316317.xml";
	public static final String SOURCE_RULE_XML_FILE = "files/input/config_rule_1534771184976.xml";
	public static final String SOURCE_DATA_TABLE_XML_FILE = "files/input/Data Tables.xml";
	public static final String SOURCE_UTIL_LIBRARIES_DIR = "files/input/UTIL Libraries/";
	public static final String SOURCE_RULE_ATTRIBUTE_DIR = "files/input/Rules_Attributes/";
	public static final String SOURCE_DATA_TABLE_DIR = "files/input/Data_Tables/";
	public static final String SOURCE_USERS_GROUPS_XML_FILE = "files/input/user_group.xml";
	public static final String XML = "xml";
	public static final String SECTIONTITLECOLOR = "800000";
	public static final String SOURCE_COMMERCE_PROCESS_FILE = "files/input/Commerce/process.xml";
	public static final String SOURCE_COMMERCE_DOCEDDOCUMENT_FILE = "files/input/Commerce/DocEdDocument new.xml";
	
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

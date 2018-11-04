package translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import translate.commerce.ApprovalSequence;
import translate.commerce.CommerceAction;
import translate.commerce.CommerceAttribute;
import translate.commerce.CommerceLibraries;
import translate.commerce.CommerceRules;
import translate.commerce.CommerceStep;
import translate.commerce.Integration;
import translate.commerce.PrinterDocument;
import utilities.TagReader;
import webservices.Javascript;
import webservices.Transaction;

public class XMLReader {

	public List<DataTable> readDataTableXML(File file) throws SAXException, IOException, ParserConfigurationException {
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName("each_record");
		List<DataTable> dataTableList = new ArrayList<DataTable>();
		DataTable dataTable = null;
		StringBuilder str = new StringBuilder();
		List<String> tableNameList =new ArrayList<String>();
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			dataTable = new DataTable();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			str.setLength(0);
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("table_name".equals(childNode.getNodeName())){
						dataTable.setTableName(childNode.getTextContent().trim());
					}
					else{
						str.append(childNode.getNodeName().trim() + " , ");
					}
				}
			}
			if(tableNameList.contains(dataTable.getTableName())){
				dataTable = null;
				str.setLength(0);
				continue;
			}
			dataTable.setDescription(str.substring(0, str.length()-2));
			tableNameList.add(dataTable.getTableName());
			dataTableList.add(dataTable);
		}
		return dataTableList;

	}

	public List<Users> readUsersXML(File file) throws ParserConfigurationException, SAXException, IOException {
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList nList = document.getElementsByTagName("bm_company");
		List<Users> usersList = new ArrayList<Users>();
		Users user = null;
		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			NodeList elementList = eElement.getElementsByTagName("bm_user");
			if(elementList != null && elementList.getLength() > 0){
				for(int temp1 = 0; temp1 < elementList.getLength(); temp1++){
					Node elementNode = elementList.item(temp1);
					if(elementNode != null){
						Element childElement = (Element)elementNode;
						if(childElement != null)
						{
							user = new Users();
							user.setUserId(childElement.getElementsByTagName("id").item(0).getTextContent());
							user.setUserLoginName(childElement.getElementsByTagName("login").item(0).getTextContent());
							usersList.add(user);
							Printer.print("User id : "+user.getUserId() +  "\tLogin Name: " + user.getUserLoginName()+"\n");
						}
					}
				}
			}
		}
		return usersList;
	}

	public List<Groups> readGroupsXML(File file) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList nList = document.getElementsByTagName("bm_company");
		List<Groups> groupsList = new ArrayList<Groups>();
		Groups group = null;
		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			NodeList elementList = eElement.getElementsByTagName("bm_group");
			if(elementList != null && elementList.getLength() > 0){
				for(int temp1 = 0; temp1 < elementList.getLength(); temp1++){
					Node elementNode = elementList.item(temp1);
					if(elementNode != null){
						Element childElement = (Element)elementNode;
						if(childElement != null)
						{
							group = new Groups();
							NodeList nodeList = childElement.getChildNodes();
							if(nodeList != null){
								for(int i = 0; i < nodeList.getLength(); i ++){
									Node node = nodeList.item(i);
									if("name".equalsIgnoreCase(node.getNodeName())){
										group.setGroupName(node.getTextContent().trim());
									}
									if("label".equalsIgnoreCase(node.getNodeName()))
									{
										NodeList labelNodesList = node.getChildNodes();
										for(int k = 0; k < labelNodesList.getLength(); k++){
											Node nameChildNode = labelNodesList.item(k);
											if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
												if("en".equals(nameChildNode.getNodeName())){
													group.setGroupLabel(nameChildNode.getTextContent().trim());
													break;
												}
											}
										}
									}
								}
							}
							groupsList.add(group);
							Printer.print("Group Label : "+group.getGroupLabel() +  "\tGroup Name: " + group.getGroupName()+"\n");
						}
					}
				}
			}
		}
		return groupsList;
	}

	public void readConfigRuleXML(File file, String tagName, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		//List<Rule> ruleList = new ArrayList<Rule>();
		Rule rule = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			rule = new Rule();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("name".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									rule.setName(nameChildNode.getTextContent().trim());
									break;
								}
							}
						}
					}
					if("variable_name".equals(childNode.getNodeName())){
						rule.setVariableName(childNode.getTextContent().trim());

					}
					if("script_text".equals(childNode.getNodeName())){
						rule.setScriptText(childNode.getTextContent().trim());
					}
					if("rule_type".equals(childNode.getNodeName())){
						rule.setRuleType(childNode.getTextContent().trim());

					}
					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									if(nameChildNode.getTextContent().trim() != null && !nameChildNode.getTextContent().trim().isEmpty()){
										String[] temp = nameChildNode.getTextContent().trim().split(":");
										if(temp.length > 1 && temp[0].startsWith("US#")){
											String storyNum = temp[0];
											rule.setDescription(temp[1]);
											if(userStoriesRules.size() > 0){
												boolean storyAdded = false;
												for(UserStories story :userStoriesRules){
													if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
														if(story.getRuleList() == null){
															story.setRuleList(new ArrayList<Rule>());
														}
														Printer.print("adding Rule : "+rule.getName()+" to : " + story.getUserStoryNum() + " : " + rule.getDescription() + "\n");
														story.getRuleList().add(rule);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new Rule : "+rule.getName()+" to : " + storyNum + " : " + rule.getDescription() + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setRuleList(new ArrayList<Rule>());
													storyTemp.getRuleList().add(rule);
													userStoriesRules.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new Rule : "+rule.getName()+" to : " + storyNum + " : " + rule.getDescription() + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setRuleList(new ArrayList<Rule>());
												storyTemp.getRuleList().add(rule);
												userStoriesRules.add(storyTemp);
											}
										}
										break;
									}
								}
							}
						}
					}
				}
			}
			//ruleList.add(rule);
		}
		//return ruleList;
	}


	public void readConfigAttributeXML(File file, String tagName, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		//List<Attribute> attributeList = new ArrayList<Attribute>();
		Attribute attribute = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			attribute = new Attribute();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("name".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									attribute.setName(nameChildNode.getTextContent().trim());
									break;
								}
							}
						}
					}
					if("variable_name".equals(childNode.getNodeName())){
						attribute.setVariableName(childNode.getTextContent().trim());

					}
					if("data_type".equals(childNode.getNodeName())){
						attribute.setDataType(childNode.getTextContent().trim());

					}
					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									if(nameChildNode.getTextContent().trim() != null && !nameChildNode.getTextContent().trim().isEmpty()){
										String[] temp = nameChildNode.getTextContent().trim().split(":");
										if(temp.length > 1 && temp[0].startsWith("US#")){
											String storyNum = temp[0];
											attribute.setDescription(temp[1]);
											if(userStoriesRules.size() > 0){
												boolean storyAdded = false;
												for(UserStories story :userStoriesRules){
													if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
														if(story.getAttributeList() == null){
															story.setAttributeList(new ArrayList<Attribute>());
														}
														Printer.print("adding attribute : "+attribute.getName()+" to : " + story.getUserStoryNum() + " : " + attribute.getDescription() + "\n");
														story.getAttributeList().add(attribute);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new attribute : "+attribute.getName()+" to : " + storyNum + " : " + attribute.getDescription() + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setAttributeList(new ArrayList<Attribute>());
													storyTemp.getAttributeList().add(attribute);
													userStoriesRules.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new attribute : "+attribute.getName()+" to : " + storyNum + " : " + attribute.getDescription() + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setAttributeList(new ArrayList<Attribute>());
												storyTemp.getAttributeList().add(attribute);
												userStoriesRules.add(storyTemp);
											}
										}
										break;
									}
								}
							}
						}
					}
				}
			}
			//attributeList.add(attribute);
		}
		//return attributeList;
	}


	public void readUtilsXML(File file, String string, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException {
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(string);
		//List<Util> utilList = new ArrayList<Util>();
		Util util = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			util = new Util();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("name".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									util.setName(nameChildNode.getTextContent().trim());
									break;
								}
							}
						}
					}
					if("variable_name".equals(childNode.getNodeName())){
						util.setVariableName(childNode.getTextContent().trim());
					}
					if(document.getElementsByTagName("script_text").item(0).getNodeName() != null){
						util.setScriptText(document.getElementsByTagName("script_text").item(0).getTextContent().trim());
					}
					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){

									if(nameChildNode.getTextContent().trim() != null && !nameChildNode.getTextContent().trim().isEmpty()){
										String[] temp = nameChildNode.getTextContent().trim().split(":");
										if(temp.length > 1 && temp[0].startsWith("US#")){
											String storyNum = temp[0];
											util.setDescription(temp[1]);
											if(userStoriesRules.size() > 0){
												boolean storyAdded = false;
												for(UserStories story :userStoriesRules){
													if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
														if(story.getUtilList() == null){
															story.setUtilList(new ArrayList<Util>());
														}
														Printer.print("adding Util : "+util.getName()+"  to : " + story.getUserStoryNum() + " : " + util.getDescription() + "\n");
														story.getUtilList().add(util);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new Util : "+util.getName()+"  to : " + storyNum + " : " + util.getDescription() + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setUtilList(new ArrayList<Util>());
													storyTemp.getUtilList().add(util);
													userStoriesRules.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new Util : "+util.getName()+" to : " + storyNum + " : " + util.getDescription() + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setUtilList(new ArrayList<Util>());
												storyTemp.getUtilList().add(util);
												userStoriesRules.add(storyTemp);
											}
										}
										break;
									}

								}
							}
						}
					}
				}
			}
			//utilList.add(util);
		}
		//return utilList;
	}

	public void readCommerceAttributeXML(File file, String tagName, List<UserStories> userStoriesCommerce) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		CommerceAttribute attribute = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			attribute = new CommerceAttribute();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){

					if("variable_name".equals(childNode.getNodeName())){
						attribute.setVariableName(TagReader.readVariableName(childNode));
					}

					if("label".equals(childNode.getNodeName())){
						attribute.setLabel(TagReader.readLabel(childNode));
					}

					if("type_".equals(childNode.getNodeName())){
						String type = TagReader.readDataType(childNode);
						if(type != null && !type.isEmpty() && type.length() > 2 && type.startsWith("Cm")){
							type = type.substring(2, type.length());
							attribute.setDataType(type);
						}else{
							attribute.setDataType(TagReader.readDataType(childNode));
						}
					}

					if("description".equals(childNode.getNodeName())){

						String[] returnText = TagReader.readDescription(childNode, userStoriesCommerce);

						attribute.setDescription(returnText[1]);

						if("ADD".equalsIgnoreCase(returnText[2])){
							for(UserStories story :userStoriesCommerce){
								if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(returnText[0])){
									if(story.getCommerceAttributeList() == null){
										story.setCommerceAttributeList(new ArrayList<CommerceAttribute>());
									}
									Printer.print("adding Commerce Attribute: "+attribute.getLabel()+" to : " + story.getUserStoryNum() + " : " + attribute.getDescription() + "\n");
									story.getCommerceAttributeList().add(attribute);
									break;
								}
							}
						}else if("CREATE".equalsIgnoreCase(returnText[2])){
							Printer.print("Creating new Commerce Attribute: "+attribute.getLabel()+" to : " + returnText[0] + " : " + attribute.getDescription() + "\n");
							UserStories storyTemp = new UserStories();
							storyTemp.setUserStoryNum(returnText[0]);
							storyTemp.setCommerceAttributeList(new ArrayList<CommerceAttribute>());
							storyTemp.getCommerceAttributeList().add(attribute);
							userStoriesCommerce.add(storyTemp);
						}
					}
				}
			}
		}
	}
	
	public void readCommerceLibrariesXML(File file, String tagName, List<UserStories> userStoriesCommerce) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		//List<CommerceLibraries> librariesList = new ArrayList<CommerceLibraries>();
		CommerceLibraries libraries = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			libraries = new CommerceLibraries();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){

					if("name".equals(childNode.getNodeName())){
						libraries.setName(TagReader.readName(childNode));
					}

					if("variable_name".equals(childNode.getNodeName())){
						libraries.setVariableName(TagReader.readVariableName(childNode));
					}

					if("_children".equals(childNode.getNodeName())){
						NodeList childNodesList = childNode.getChildNodes();
						for(int k = 0; k < childNodesList.getLength(); k++){
							Node childrenNode = childNodesList.item(k);
							if(Node.ELEMENT_NODE == childrenNode.getNodeType()){
								if("bm_function".equals(childrenNode.getNodeName())){
									NodeList nameChildNodesList = childrenNode.getChildNodes();
									for(int m = 0; m < nameChildNodesList.getLength(); m++){
										Node nameChildNode = nameChildNodesList.item(m);
										if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
											if("script_text".equals(nameChildNode.getNodeName())){
												libraries.setScriptText(nameChildNode.getTextContent().trim());
												break;
											}
										}
									}
									
								}
							}
						}						
					}

					if("description".equals(childNode.getNodeName())){

						String[] returnText = TagReader.readDescription(childNode, userStoriesCommerce);

						libraries.setDescription(returnText[1]);

						if("ADD".equalsIgnoreCase(returnText[2])){
							for(UserStories story :userStoriesCommerce){
								if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(returnText[0])){
									if(story.getCommerceLibrariesList() == null){
										story.setCommerceLibrariesList(new ArrayList<CommerceLibraries>());
									}
									Printer.print("adding Commerce Libraries: "+libraries.getName() +" to : " + story.getUserStoryNum() + " : " + libraries.getDescription() + "\n");
									story.getCommerceLibrariesList().add(libraries);
									break;
								}
							}
						}else if("CREATE".equalsIgnoreCase(returnText[2])){
							Printer.print("Creating new Commerce Libraries: "+libraries.getName() +" to : " + returnText[0] + " : " + libraries.getDescription() + "\n");
							UserStories storyTemp = new UserStories();
							storyTemp.setUserStoryNum(returnText[0]);
							storyTemp.setCommerceLibrariesList(new ArrayList<CommerceLibraries>());
							storyTemp.getCommerceLibrariesList().add(libraries);
							userStoriesCommerce.add(storyTemp);
						}
					}
				}
			}
			//attributeList.add(attribute);
		}
	}
	
	public void readCommerceRulesXML(File file, String tagName, List<UserStories> userStoriesCommerce) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		CommerceRules rules = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			rules = new CommerceRules();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){

					if("name".equals(childNode.getNodeName())){
						rules.setName(TagReader.readName(childNode));
					}
					
					if("rule_type".equals(childNode.getNodeName())){
						rules.setRuleType(childNode.getTextContent().trim());
					}

					if("variable_name".equals(childNode.getNodeName())){
						rules.setVariableName(TagReader.readVariableName(childNode));
					}

					if("description".equals(childNode.getNodeName())){

						String[] returnText = TagReader.readDescription(childNode, userStoriesCommerce);

						rules.setDescription(returnText[1]);

						if("ADD".equalsIgnoreCase(returnText[2])){
							for(UserStories story :userStoriesCommerce){
									if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(returnText[0])){
									if(story.getCommerceRuleList() == null){
										story.setCommerceRuleList(new ArrayList<CommerceRules>());
									}
									Printer.print("adding Commerce Rule: "+rules.getName() +" to : " + story.getUserStoryNum() + " : " + rules.getDescription() + "\n");
									story.getCommerceRuleList().add(rules);
									break;
								}
							}
						}else if("CREATE".equalsIgnoreCase(returnText[2])){
							Printer.print("Creating new Commerce Rule: "+rules.getName() +" to : " + returnText[0] + " : " + rules.getDescription() + "\n");
							UserStories storyTemp = new UserStories();
							storyTemp.setUserStoryNum(returnText[0]);
							storyTemp.setCommerceRuleList(new ArrayList<CommerceRules>());
							storyTemp.getCommerceRuleList().add(rules);
							userStoriesCommerce.add(storyTemp);
						}
					}
				}
			}
		}
	}

	public void readCommerceActionXML(File file, String tagName, List<UserStories> userStoriesCommerce) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		CommerceAction action = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			action = new CommerceAction();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("variable_name".equals(childNode.getNodeName())){
						action.setVariableName(TagReader.readVariableName(childNode));
					}
					if("label".equals(childNode.getNodeName())){
						action.setLabel(TagReader.readLabel(childNode));
					}
					
					if("description".equals(childNode.getNodeName())){

						String[] returnText = TagReader.readDescription(childNode, userStoriesCommerce);

						action.setDescription(returnText[1]);

						if("ADD".equalsIgnoreCase(returnText[2])){
							for(UserStories story :userStoriesCommerce){
									if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(returnText[0])){
									if(story.getCommerceActionsList() == null){
										story.setCommerceActionsList(new ArrayList<CommerceAction>());
									}
									Printer.print("adding Commerce Action: "+action.getLabel()+" to : " + story.getUserStoryNum() + " : " + action.getDescription() + "\n");
									story.getCommerceActionsList().add(action);
									break;
								}
							}
						}else if("CREATE".equalsIgnoreCase(returnText[2])){
							Printer.print("Creating new Commerce Action: "+action.getLabel() +" to : " + returnText[0] + " : " + action.getDescription() + "\n");
							UserStories storyTemp = new UserStories();
							storyTemp.setUserStoryNum(returnText[0]);
							storyTemp.setCommerceActionsList(new ArrayList<CommerceAction>());
							storyTemp.getCommerceActionsList().add(action);
							userStoriesCommerce.add(storyTemp);
						}
					}
				}
			}
		}
	}
	
	public void readCommerceSequenceXML(File file, String tagName, List<UserStories> userStoriesCommerce) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		ApprovalSequence approvalSequence = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			approvalSequence = new ApprovalSequence();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("label".equals(childNode.getNodeName())){
						approvalSequence.setLabel(TagReader.readLabel(childNode));
					}
					if("var_name".equals(childNode.getNodeName())){
						approvalSequence.setVariableName(childNode.getTextContent().trim());
					}
					if("_children".equals(childNode.getNodeName())){
						Element nameChildNode = (Element)childNode;
						NodeList approvers = nameChildNode.getElementsByTagName("bm_cm_approver");
						for(int k = 0; k < approvers.getLength(); k++){
							Node approver = approvers.item(k);
							if(Node.ELEMENT_NODE == approver.getNodeType()){
								Element approverElement = (Element)childNode;
								NodeList approverDetails = approverElement.getElementsByTagName("bm_cm_approver_detail");
								for(int l = 0; l < approverDetails.getLength(); l++){
									Node approverDetail = approverDetails.item(k);
									ApproverLoop:
										if(Node.ELEMENT_NODE == approverDetail.getNodeType()){
											Element approverDetailElement = (Element)approverDetail;
											NodeList labels = approverDetailElement.getElementsByTagName("label");
											for(int m = 0; m < labels.getLength(); m++){
												Node label = labels.item(k);
												if(label != null && Node.ELEMENT_NODE == label.getNodeType()){
													Element labelElement = (Element)label;
													NodeList enlabel = labelElement.getElementsByTagName("en");
													approvalSequence.setApprover(enlabel.item(0).getTextContent().trim());
													break ApproverLoop;
												}
											}
										}
								}

								NodeList approvalTemplateDetails = approverElement.getElementsByTagName("bm_cm_approval_notification");
								for(int l = 0; l < approvalTemplateDetails.getLength(); l++){
									Node approvalTemplateDetail = approvalTemplateDetails.item(k);
									if(approvalTemplateDetail != null && Node.ELEMENT_NODE == approvalTemplateDetail.getNodeType()){
										Element approvalTemplateDetailElement = (Element)approvalTemplateDetail;
										NodeList temlateVarnames = approvalTemplateDetailElement.getElementsByTagName("template_varname");
										approvalSequence.setApprovalTemplate(temlateVarnames.item(0).getTextContent().trim());
									}
								}
							}
						}

						NodeList metaRules = nameChildNode.getElementsByTagName("bm_cm_rule_meta");
						for(int k = 0; k < metaRules.getLength(); k++){
							Node metaRule = metaRules.item(k);
							if(Node.ELEMENT_NODE == metaRule.getNodeType()){
								Element metaRuleElement = (Element)metaRule;
								NodeList bmFunctionDetails = metaRuleElement.getElementsByTagName("bm_function");
								for(int l = 0; l < bmFunctionDetails.getLength(); l++){
									Node bmFunction = bmFunctionDetails.item(k);
									if(bmFunction != null && Node.ELEMENT_NODE == bmFunction.getNodeType()){
										Element bmFunctionElement = (Element)bmFunction;
										NodeList scriptTexts = bmFunctionElement.getElementsByTagName("script_text");
										approvalSequence.setScriptText(scriptTexts.item(0).getTextContent().trim());
									}
								}
							}
						}
					}

					if("description".equals(childNode.getNodeName())){

						String[] returnText = TagReader.readDescription(childNode, userStoriesCommerce);

						approvalSequence.setDescription(returnText[1]);

						if("ADD".equalsIgnoreCase(returnText[2])){
							for(UserStories story :userStoriesCommerce){
									if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(returnText[0])){
									if(story.getCommerceApprovalSequencesList() == null){
										story.setCommerceApprovalSequencesList(new ArrayList<ApprovalSequence>());
									}
									Printer.print("adding Commerce Approval Sequence: "+approvalSequence.getLabel()+" to : " + story.getUserStoryNum() + " : " + approvalSequence.getDescription() + "\n");
									story.getCommerceApprovalSequencesList().add(approvalSequence);
									break;
								}
							}
						}else if("CREATE".equalsIgnoreCase(returnText[2])){
							Printer.print("Creating new Commerce Approval Sequence: "+approvalSequence.getLabel() +" to : " + returnText[0] + " : " + approvalSequence.getDescription() + "\n");
							UserStories storyTemp = new UserStories();
							storyTemp.setUserStoryNum(returnText[0]);
							storyTemp.setCommerceApprovalSequencesList(new ArrayList<ApprovalSequence>());
							storyTemp.getCommerceApprovalSequencesList().add(approvalSequence);
							userStoriesCommerce.add(storyTemp);
						}
					}
				}
			}
		}
	}

	public void readCommerceDocEdDocumentXML(File file, String tagName, List<UserStories> userStoriesCommerce) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		PrinterDocument printerDocument = new PrinterDocument();
		Node bmDocEdRecord = document.getElementsByTagName("bm_doc_ed").item(0);
		if(Node.ELEMENT_NODE == bmDocEdRecord.getNodeType()){
			Element bmDocEdRecordElement = (Element) bmDocEdRecord;
			Node varName = bmDocEdRecordElement.getElementsByTagName("varname").item(0);
			printerDocument.setCommerceProcessLinked(varName.getTextContent().trim());
		}
		
		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("name".equals(childNode.getNodeName())){
						printerDocument.setDocName(childNode.getTextContent().trim());
					}
					if("varname".equals(childNode.getNodeName())){
						printerDocument.setVariableName(childNode.getTextContent().trim());
					}
					if("description".equals(childNode.getNodeName())){

						String[] returnText = childNode.getTextContent().trim().split(":");

						printerDocument.setDescription(returnText[1]);
						
						boolean storyAdded = false;
						for(UserStories story :userStoriesCommerce){
							if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(returnText[0])){
								if(story.getCommercePrinterDocumentList() == null){
									story.setCommercePrinterDocumentList(new ArrayList<PrinterDocument>());
								}
								Printer.print("adding Commerce Printer Friendly Document: "+printerDocument.getDocName()+" to : " + story.getUserStoryNum() + " : " + printerDocument.getDescription() + "\n");
								story.getCommercePrinterDocumentList().add(printerDocument);
								storyAdded = true;
								break;
							}
						}
						if(!storyAdded){
							Printer.print("Creating new Commerce Printer Friendly Document: "+printerDocument.getDocName() +" to : " + returnText[0] + " : " + printerDocument.getDescription() + "\n");
							UserStories storyTemp = new UserStories();
							storyTemp.setUserStoryNum(returnText[0]);
							storyTemp.setCommercePrinterDocumentList(new ArrayList<PrinterDocument>());
							storyTemp.getCommercePrinterDocumentList().add(printerDocument);
							userStoriesCommerce.add(storyTemp);
						}
					}
				}
			}
		}
	}

	public void readCommerceStepsXML(File commerceProcessFile, String string, List<UserStories> userStoriesCommerce) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(commerceProcessFile);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(string);
		CommerceStep commerceStep = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			commerceStep = new CommerceStep();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("name".equals(childNode.getNodeName())){
						commerceStep.setStepName(TagReader.readName(childNode));
						Printer.print("Step Name : " + commerceStep.getStepName()+"\n");
					}
					if("variable_name".equals(childNode.getNodeName())){
						commerceStep.setVariableName(childNode.getTextContent().trim());
						Printer.print("Variable Name : " + commerceStep.getVariableName()+"\n");
					}
					if("_children".equals(childNode.getNodeName())){
						Element nameChildNode = (Element)childNode;
						NodeList participants = nameChildNode.getElementsByTagName("bm_cm_pp");
						bm_cm_pp_loop:
						for(int k = 0; k < participants.getLength(); k++){
							Node participant = participants.item(k);
							if(Node.ELEMENT_NODE == participant.getNodeType()){
								Element participantElement = (Element)participant;
								NodeList names = participantElement.getElementsByTagName("name");
								for(int m = 0; m < names.getLength(); m++){
									Node name = names.item(m);
									if(name != null && Node.ELEMENT_NODE == name.getNodeType()){
										Element labelElement = (Element)name;
										NodeList enlabel = labelElement.getElementsByTagName("en");
										commerceStep.setParticipantProfileName(enlabel.item(0).getTextContent().trim());
										Printer.print("participant profile Name : " + commerceStep.getParticipantProfileName()+"\n");
										break bm_cm_pp_loop;
									}
								}
							}
						}
					}

					if("_children".equals(childNode.getNodeName())){
						Element nameChildNode = (Element)childNode;
						NodeList participants = nameChildNode.getElementsByTagName("bm_cm_pp");
						bm_cm_pp_loop:
						for(int k = 0; k < participants.getLength(); k++){
							Node participant = participants.item(k);
							if(Node.ELEMENT_NODE == participant.getNodeType()){
								Element participantElement = (Element)participant;
								NodeList description = participantElement.getElementsByTagName("description");
								for(int m = 0; m < description.getLength(); m++){
									Node name = description.item(m);
									if(name != null && Node.ELEMENT_NODE == name.getNodeType()){
										Element labelElement = (Element)name;
										NodeList enlabel = labelElement.getElementsByTagName("en");
										String descriptionText =  enlabel.item(0).getTextContent().trim();
										if(descriptionText.startsWith("US#")){
											String[] temp = descriptionText.split(":");
											if(temp.length > 1){
												descriptionText = temp[1];
											}
										}
										commerceStep.setProfileDescription(descriptionText);
										Printer.print("Profile description: " + commerceStep.getProfileDescription()+"\n");
										break bm_cm_pp_loop;
									}
								}
							}
						}
					}

					if("_children".equals(childNode.getNodeName())){
						Element nameChildNode = (Element)childNode;
						NodeList participants = nameChildNode.getElementsByTagName("bm_cm_pp");
						bm_cm_pp_Loop:
						for(int k = 0; k < participants.getLength(); k++){
							Node participant = participants.item(k);
							if(Node.ELEMENT_NODE == participant.getNodeType()){
								Element participantElement = (Element)participant;
								NodeList children = participantElement.getElementsByTagName("_children");
								for(int m = 0; m < children.getLength(); m++){
									Node name = children.item(m);
									if(name != null && Node.ELEMENT_NODE == name.getNodeType()){
										Element nameInnerChildNode = (Element)childNode;
										NodeList nodeList = nameInnerChildNode.getElementsByTagName("bm_cm_trans_rule");
										for(int l = 0; l < nodeList.getLength(); l++){
											Node bm_cm_trans_rule = nodeList.item(k);
											if(Node.ELEMENT_NODE == bm_cm_trans_rule.getNodeType()){
												Element trans_rule = (Element)bm_cm_trans_rule;
												NodeList names = trans_rule.getElementsByTagName("name");
												for(int n = 0; n < names.getLength(); n++){
													Node trans_rule_name = names.item(m);
													if(trans_rule_name != null && Node.ELEMENT_NODE == trans_rule_name.getNodeType()){
														Element labelElement = (Element)trans_rule_name;
														NodeList enlabel = labelElement.getElementsByTagName("en");
														commerceStep.setTransitionRule(enlabel.item(0).getTextContent().trim());
														Printer.print("Transition Rule Name : " + commerceStep.getTransitionRule()+"\n");
														break bm_cm_pp_Loop;
													}
												}
											}
										}
									}
								}
							}
						}
					}
					
					if("_children".equals(childNode.getNodeName())){
						Element nameChildNode = (Element)childNode;
						NodeList participants = nameChildNode.getElementsByTagName("bm_cm_rule_meta");
						bm_cm_pp_Loop:
						for(int k = 0; k < participants.getLength(); k++){
							Node participant = participants.item(k);
							if(Node.ELEMENT_NODE == participant.getNodeType()){
								Element participantElement = (Element)participant;
								NodeList children = participantElement.getElementsByTagName("_children");
								for(int m = 0; m < children.getLength(); m++){
									Node name = children.item(m);
									if(name != null && Node.ELEMENT_NODE == name.getNodeType()){
										Element nameInnerChildNode = (Element)childNode;
										NodeList nodeList = nameInnerChildNode.getElementsByTagName("bm_function");
										for(int l = 0; l < nodeList.getLength(); l++){
											Node bmFunction = nodeList.item(k);
											if(bmFunction != null && Node.ELEMENT_NODE == bmFunction.getNodeType()){
												Element bmFunctionElement = (Element)bmFunction;
												NodeList scriptTexts = bmFunctionElement.getElementsByTagName("script_text");
												commerceStep.setAdvancedForwardingRule(scriptTexts.item(0).getTextContent().trim());
												Printer.print("Advanced Forwarding Rule : " + commerceStep.getAdvancedForwardingRule()+"\n");
												break bm_cm_pp_Loop;
											}
										}
									}
								}
							}
						}
					}

					if("_children".equals(childNode.getNodeName())){
						Element nameChildNode = (Element)childNode;
						NodeList participants = nameChildNode.getElementsByTagName("bm_cm_pp");
						bm_cm_pp_Loop:
						for(int k = 0; k < participants.getLength(); k++){
							Node participant = participants.item(k);
							if(Node.ELEMENT_NODE == participant.getNodeType()){
								Element participantElement = (Element)participant;
								NodeList children = participantElement.getElementsByTagName("_children");
								for(int m = 0; m < children.getLength(); m++){
									Node name = children.item(m);
									if(name != null && Node.ELEMENT_NODE == name.getNodeType()){
										Element nameInnerChildNode = (Element)childNode;
										NodeList nodeList = nameInnerChildNode.getElementsByTagName("bm_cm_trans_rule");
										for(int n = 0; n < nodeList.getLength(); n++){
											Node bm_cm_trans_rule = nodeList.item(n);
											if(Node.ELEMENT_NODE == bm_cm_trans_rule.getNodeType()){
												Element trans_rule = (Element)bm_cm_trans_rule;
												NodeList _children = trans_rule.getElementsByTagName("_children");
												for(int o = 0; o < _children.getLength(); o++){
													Node _child = _children.item(o);
													if(_child != null && Node.ELEMENT_NODE == _child.getNodeType()){
														Element _innerchild = (Element)_child;
														NodeList bm_function = _innerchild.getElementsByTagName("bm_function");
														for(int l = 0; l < bm_function.getLength(); l++){
															Node bmFunction = bm_function.item(k);
															if(bmFunction != null && Node.ELEMENT_NODE == bmFunction.getNodeType()){
																Element bmFunctionElement = (Element)bmFunction;
																NodeList scriptTexts = bmFunctionElement.getElementsByTagName("script_text");
																commerceStep.setAdvancedConditionofTransitionRule(scriptTexts.item(0).getTextContent().trim());
																Printer.print("Advanced Condition of Transition Rule : " + commerceStep.getAdvancedConditionofTransitionRule()+"\n");
																break bm_cm_pp_Loop;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){

									if(nameChildNode.getTextContent().trim() != null && !nameChildNode.getTextContent().trim().isEmpty()){
										String[] temp = nameChildNode.getTextContent().trim().split(":");
										if(temp.length > 1 && temp[0].startsWith("US#")){
											String storyNum = temp[0];
											commerceStep.setDescription(temp[1]);
											if(userStoriesCommerce.size() > 0){
												boolean storyAdded = false;
												for(UserStories story :userStoriesCommerce){
													if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
														if(story.getCommerceStepsList() == null){
															story.setCommerceStepsList(new ArrayList<CommerceStep>());
														}
														Printer.print("adding Commerce Step : "+commerceStep.getStepName()+"  to : " + story.getUserStoryNum() + " : " + commerceStep.getDescription() + "\n");
														story.getCommerceStepsList().add(commerceStep);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new Commerce Step : "+commerceStep.getStepName()+"  to : " + storyNum + " : " + commerceStep.getDescription() + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setCommerceStepsList(new ArrayList<CommerceStep>());
													storyTemp.getCommerceStepsList().add(commerceStep);
													userStoriesCommerce.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new Commerce Step : "+commerceStep.getStepName()+" to : " + storyNum + " : " + commerceStep.getDescription() + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setCommerceStepsList(new ArrayList<CommerceStep>());
												storyTemp.getCommerceStepsList().add(commerceStep);
												userStoriesCommerce.add(storyTemp);
											}
										}
										break;
									}

								}
							}
						}
					}

				}
			}
		}
	}

	public void readCommerceIntegration(File commerceProcessFile, String string,
			List<UserStories> userStoriesCommerce) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(commerceProcessFile);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(string);
		
		Integration integration = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			integration = new Integration();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("name".equals(childNode.getNodeName())){
						integration.setIntegrationName(TagReader.readName(childNode));
						Printer.print("Integration Name : " + integration.getIntegrationName()+"\n");
					}
					
					if("variable_name".equals(childNode.getNodeName())){
						integration.setVariableName(childNode.getTextContent().trim());
						Printer.print("Variable Name : " + integration.getVariableName()+"\n");
					}
					
					if("id_field".equals(childNode.getNodeName())){
						integration.setIdField(childNode.getTextContent().trim());
						Printer.print("id field : " + integration.getIdField()+"\n");
					}
					
					if("endpoint".equals(childNode.getNodeName())){
						integration.setEndpointURL(childNode.getTextContent().trim());
						Printer.print("Endpoint URL : " + integration.getEndpointURL()+"\n");
					}
					
					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									if(nameChildNode.getTextContent().trim() != null && !nameChildNode.getTextContent().trim().isEmpty()){
										String[] temp = nameChildNode.getTextContent().trim().split(":");
										if(temp.length > 1 && temp[0].startsWith("US#")){
											String storyNum = temp[0];
											integration.setDescription(temp[1]);
											if(userStoriesCommerce.size() > 0){
												boolean storyAdded = false;
												for(UserStories story :userStoriesCommerce){
													if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
														if(story.getIntegrationsList() == null){
															story.setIntegrationsList(new ArrayList<Integration>());
														}
														Printer.print("adding Integration Step : "+integration.getIntegrationName()+"  to : " + story.getUserStoryNum() + " : " + integration.getDescription() + "\n");
														story.getIntegrationsList().add(integration);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new Integration Step : "+integration.getIntegrationName()+"  to : " + storyNum + " : " + integration.getDescription() + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setIntegrationsList(new ArrayList<Integration>());
													storyTemp.getIntegrationsList().add(integration);
													userStoriesCommerce.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new Integration Step : "+integration.getIntegrationName()+" to : " + storyNum + " : " + integration.getDescription() + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setIntegrationsList(new ArrayList<Integration>());
												storyTemp.getIntegrationsList().add(integration);
												userStoriesCommerce.add(storyTemp);
											}
										}
										break;
									}

								}
							}
						}
					}
					
					
				}
			}
		}
	}
	
	public void readWebServicesFiles(File[] webservicesList, List<UserStories> userStoriesCommerce) throws IOException {
		Transaction transaction = null;
		String userStoryLine = null;
		BufferedReader br = null;
		for(File file : webservicesList){
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			boolean foundUserStory = false;

			while((userStoryLine = br.readLine()) != null){
				br.mark(0);
				if(userStoryLine.contains("US#")){
					transaction = new Transaction();
					int startIndex = userStoryLine.indexOf("US#") + 3;
					transaction.setUserStoryNum(userStoryLine.substring(startIndex, startIndex+4));
					transaction.setTransactionName(file.getName());
					foundUserStory = true;
					break;
				}
			}
			br.reset();
			if(foundUserStory){
				String desc = null;
				StringBuilder sb = new StringBuilder();
				while((desc = br.readLine()) != null){
					if(!desc.contains("US#")){
						sb.append(desc.trim()+";");
					}
				}
				transaction.setDescription(sb.toString());
				br.close();
				// Add to commerce user stories
				for(UserStories userStory : userStoriesCommerce){
					if(userStory.getUserStoryNum().contains(transaction.getUserStoryNum())){
						if(userStory.getTransactionList() == null){
							userStory.setTransactionList(new ArrayList<Transaction>());
						}
						userStory.getTransactionList().add(transaction);
					}
				}
				//System.out.println(userStoriesCommerce);
			}
		}
	}

	public void readJavascriptFiles(File[] jsList, List<UserStories> userStoriesCommerce) throws IOException {
		Javascript javascript = null;
		String userStoryLine = null;
		BufferedReader br =null;
		for(File file : jsList){
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			boolean foundUserStory = false;
			while((userStoryLine = br.readLine()) != null){
				br.mark(0);
				if(userStoryLine.contains("US#")){
					javascript = new Javascript();
					int startIndex = userStoryLine.indexOf("US#") + 3;
					javascript.setUserStoryNum(userStoryLine.substring(startIndex, startIndex+4));
					javascript.setJavascriptName(file.getName());
					foundUserStory = true;
					break;
				}
			}
			br.reset();
			if(foundUserStory){
				String desc = null;
				StringBuilder sb = new StringBuilder();
				while((desc = br.readLine()) != null){
					if(!desc.contains("US#")){
						sb.append(desc.trim()+";");
					}
				}
				javascript.setDescription(sb.toString());
				br.close();
				// Add to commerce user stories
				for(UserStories userStory : userStoriesCommerce){
					if(userStory.getUserStoryNum().contains(javascript.getUserStoryNum())){
						if(userStory.getJavascriptList() == null){
							userStory.setJavascriptList(new ArrayList<Javascript>());
						}
						userStory.getJavascriptList().add(javascript);
					}
				}
				//System.out.println(userStoriesCommerce);
			}
		}
	}
}

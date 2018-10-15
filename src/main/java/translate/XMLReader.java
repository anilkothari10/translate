package translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import translate.commerce.ApprovalSequence;
import translate.commerce.CommerceAction;
import translate.commerce.CommerceComponents;
import translate.commerce.PrinterDocument;

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

	public List<Rule> readConfigRuleXML(File file, String tagName, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		List<Rule> ruleList = new ArrayList<Rule>();
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
														Printer.print("adding Rule to : " + story.getUserStoryNum()+ "\n");
														story.getRuleList().add(rule);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new Rule to : " + storyNum + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setRuleList(new ArrayList<Rule>());
													storyTemp.getRuleList().add(rule);
													userStoriesRules.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new Rule to : " + storyNum + "\n");
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
			ruleList.add(rule);
		}
		return ruleList;
	}


	public List<Attribute> readConfigAttributeXML(File file, String tagName, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException{
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		List<Attribute> attributeList = new ArrayList<Attribute>();
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
														Printer.print("adding attribute to : " + story.getUserStoryNum() + "\n");
														story.getAttributeList().add(attribute);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new attribute to : " + storyNum + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setAttributeList(new ArrayList<Attribute>());
													storyTemp.getAttributeList().add(attribute);
													userStoriesRules.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new attribute to : " + storyNum+ "\n");
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
			attributeList.add(attribute);
		}
		return attributeList;
	}


	public List<Util> readUtilsXML(File file, String string, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException {
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(string);
		List<Util> utilList = new ArrayList<Util>();
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
														Printer.print("adding Util to : " + story.getUserStoryNum() + "\n");
														story.getUtilList().add(util);
														storyAdded = true;
														break;
													}
												}
												if(!storyAdded){
													Printer.print("1 creating new Util to : " + storyNum + "\n");
													UserStories storyTemp = new UserStories();
													storyTemp.setUserStoryNum(storyNum);
													storyTemp.setUtilList(new ArrayList<Util>());
													storyTemp.getUtilList().add(util);
													userStoriesRules.add(storyTemp);
												}
											}else{
												Printer.print("2 creating new Util to : " + storyNum+ "\n");
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
			utilList.add(util);
		}
		return utilList;
	}

	public void readCommerceProcessXML(File file, String actionTag, 
			String approvalTag, List<CommerceComponents> commerceComponentsList, List<UserStories> userStoriesRules) 
					throws ParserConfigurationException, SAXException, IOException {

		if(commerceComponentsList.isEmpty()){
			commerceComponentsList.add(new CommerceComponents());
		}
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		NodeList eachRecordNodeList = document.getElementsByTagName(actionTag);
		List<CommerceAction> commActList = new ArrayList<CommerceAction>();
		CommerceAction commerceAction = null;
		String userStoryNumStr = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			commerceAction = new CommerceAction();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									userStoryNumStr = nameChildNode.getTextContent().trim();
									if(StringUtils.isEmpty(userStoryNumStr)){
										continue;
									}
									String[] temp = userStoryNumStr.split(":");
									if(temp.length > 1 && temp[0].startsWith("US#")){
										String storyNum = temp[0];
										commerceAction.setDescription(temp[1]);
										if(userStoriesRules.size() > 0){
											boolean storyAdded = false;
											for(UserStories story :userStoriesRules){
												if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
													if(story.getCommerceActionList() == null){
														story.setCommerceActionList(new ArrayList<CommerceAction>());
													}
													Printer.print("adding CommerceAction to : " + story.getUserStoryNum() + "\n");
													story.getCommerceActionList().add(commerceAction);
													storyAdded = true;
													break;
												}
											}
											if(!storyAdded){
												Printer.print("1 creating new CommerceAction to : " + storyNum + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setCommerceActionList(new ArrayList<CommerceAction>());
												storyTemp.getCommerceActionList().add(commerceAction);
												userStoriesRules.add(storyTemp);
											}
										}else{
											Printer.print("2 creating new CommerceAction to : " + storyNum+ "\n");
											UserStories storyTemp = new UserStories();
											storyTemp.setUserStoryNum(storyNum);
											storyTemp.setCommerceActionList(new ArrayList<CommerceAction>());
											storyTemp.getCommerceActionList().add(commerceAction);
											userStoriesRules.add(storyTemp);
										}
									}
									break;
								}
							}
						}
					}
					if("label".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									commerceAction.setLabel(nameChildNode.getTextContent().trim());
									break;
								}
							}
						}
					}
					if("variable_name".equals(childNode.getNodeName())){
						commerceAction.setVariableName(childNode.getTextContent().trim());
					}
				}
			}
			commActList.add(commerceAction);
		}
		if(commerceComponentsList.get(0).getCommerceActionList() == null){
			commerceComponentsList.get(0).setCommerceActionList(commActList);
		}else{
			commerceComponentsList.get(0).getCommerceActionList().addAll(commActList);
		}

		NodeList approvalTagNodeList = document.getElementsByTagName(approvalTag);
		ApprovalSequence approvalSequence = null;
		List<ApprovalSequence> apprSeqList = new ArrayList<ApprovalSequence>();
		for (int i = 0; i < approvalTagNodeList.getLength(); i++){
			approvalSequence = new ApprovalSequence();
			Node eachRecordNode = approvalTagNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("description".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									userStoryNumStr = childNode.getTextContent().trim();
									if(StringUtils.isEmpty(userStoryNumStr)){
										continue;
									}
									String[] temp = userStoryNumStr.split(":");
									if(temp.length > 1 && temp[0].startsWith("US#")){
										String storyNum = temp[0];
										approvalSequence.setDescription(temp[1]);
										if(userStoriesRules.size() > 0){
											boolean storyAdded = false;
											for(UserStories story :userStoriesRules){
												if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
													if(story.getApprovalSequenceList() == null){
														story.setApprovalSequenceList(new ArrayList<ApprovalSequence>());
													}
													Printer.print("adding approvalSequence to : " + story.getUserStoryNum() + "\n");
													story.getApprovalSequenceList().add(approvalSequence);
													storyAdded = true;
													break;
												}
											}
											if(!storyAdded){
												Printer.print("1 creating new approvalSequence to : " + storyNum + "\n");
												UserStories storyTemp = new UserStories();
												storyTemp.setUserStoryNum(storyNum);
												storyTemp.setApprovalSequenceList(new ArrayList<ApprovalSequence>());
												storyTemp.getApprovalSequenceList().add(approvalSequence);
												userStoriesRules.add(storyTemp);
											}
										}else{
											Printer.print("2 creating new approvalSequence to : " + storyNum+ "\n");
											UserStories storyTemp = new UserStories();
											storyTemp.setUserStoryNum(storyNum);
											storyTemp.setApprovalSequenceList(new ArrayList<ApprovalSequence>());
											storyTemp.getApprovalSequenceList().add(approvalSequence);
											userStoriesRules.add(storyTemp);
										}
									}
									break;
								}
							}
						}
					}
					if("label".equals(childNode.getNodeName())){
						NodeList nameChildNodesList = childNode.getChildNodes();
						for(int k = 0; k < nameChildNodesList.getLength(); k++){
							Node nameChildNode = nameChildNodesList.item(k);
							if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
								if("en".equals(nameChildNode.getNodeName())){
									approvalSequence.setLabel(nameChildNode.getTextContent().trim());
									break;
								}
							}
						}
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
				}
			}
			apprSeqList.add(approvalSequence);
		}
		if(commerceComponentsList.get(0).getApprovalSequenceList() == null){
			commerceComponentsList.get(0).setApprovalSequenceList(apprSeqList);
		}else{
			commerceComponentsList.get(0).getApprovalSequenceList().addAll(apprSeqList);
		}
	}

	public void readCommerceDocEdDocumentXML(File file, String tagName, List<CommerceComponents> commerceComponents, List<UserStories> userStoriesRules) throws ParserConfigurationException, SAXException, IOException {

		if(commerceComponents.isEmpty()){
			commerceComponents.add(new CommerceComponents());
		}
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure;
		document.getDocumentElement().normalize();

		List<PrinterDocument> printDocList = new ArrayList<PrinterDocument>();
		PrinterDocument printerDoc = null;
		String commerceProcessLinked = null;
		Node bmDocEdRecord = document.getElementsByTagName("bm_doc_ed").item(0);
		if(Node.ELEMENT_NODE == bmDocEdRecord.getNodeType()){
			Element bmDocEdRecordElement = (Element) bmDocEdRecord;
			Node varName = bmDocEdRecordElement.getElementsByTagName("varname").item(0);
			commerceProcessLinked = varName.getTextContent().trim();
		}

		NodeList eachRecordNodeList = document.getElementsByTagName(tagName);
		String userStoryNumStr = null;
		for (int i = 0; i < eachRecordNodeList.getLength(); i++){
			printerDoc = new PrinterDocument();
			Node eachRecordNode = eachRecordNodeList.item(i);
			NodeList eachRecordChildNodeList = eachRecordNode.getChildNodes();
			for(int j = 0; j < eachRecordChildNodeList.getLength(); j++){
				Node childNode = eachRecordChildNodeList.item(j);
				if(Node.ELEMENT_NODE == childNode.getNodeType()){
					if("description".equals(childNode.getNodeName())){
						userStoryNumStr = childNode.getTextContent().trim();
						if(StringUtils.isEmpty(userStoryNumStr)){
							continue;
						}
						String[] temp = userStoryNumStr.split(":");
						if(temp.length > 1 && temp[0].startsWith("US#")){
							String storyNum = temp[0];
							printerDoc.setDescription(temp[1]);
							if(userStoriesRules.size() > 0){
								boolean storyAdded = false;
								for(UserStories story :userStoriesRules){
									if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
										if(story.getPrinterDocList() == null){
											story.setPrinterDocList(new ArrayList<PrinterDocument>());
										}
										Printer.print("adding PrinterDoc to : " + story.getUserStoryNum() + "\n");
										story.getPrinterDocList().add(printerDoc);
										storyAdded = true;
										break;
									}
								}
								if(!storyAdded){
									Printer.print("1 creating new PrinterDoc to : " + storyNum + "\n");
									UserStories storyTemp = new UserStories();
									storyTemp.setUserStoryNum(storyNum);
									storyTemp.setPrinterDocList(new ArrayList<PrinterDocument>());
									storyTemp.getPrinterDocList().add(printerDoc);
									userStoriesRules.add(storyTemp);
								}
							}else{
								Printer.print("2 creating new PrinterDoc to : " + storyNum+ "\n");
								UserStories storyTemp = new UserStories();
								storyTemp.setUserStoryNum(storyNum);
								storyTemp.setPrinterDocList(new ArrayList<PrinterDocument>());
								storyTemp.getPrinterDocList().add(printerDoc);
								userStoriesRules.add(storyTemp);
							}
						}
						break;
					}
					if("name".equals(childNode.getNodeName())){
						printerDoc.setDocName(childNode.getTextContent().trim());
					}
					if("varname".equals(childNode.getNodeName())){
						printerDoc.setVariableName(childNode.getTextContent().trim());
					}
				}
			}
			printerDoc.setCommerceProcessLinked(commerceProcessLinked);
			printDocList.add(printerDoc);
		}
		if(commerceComponents.get(0).getPrinterDocumentList() == null){
			commerceComponents.get(0).setPrinterDocumentList(printDocList);
		}else{
			commerceComponents.get(0).getPrinterDocumentList().addAll(printDocList);
		}
	}
}

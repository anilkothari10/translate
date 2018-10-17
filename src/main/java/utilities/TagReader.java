package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import translate.UserStories;

public class TagReader {
	/**
	 * For Tag <variable_name>
	 * @param childNode
	 * @return
	 */
	public static String readVariableName(Node  childNode){
			return childNode.getTextContent().trim();
	}
	
	/**
	 * For tag <label> -> <en>   
	 * @param childNode
	 * @return
	 */
	public static String readLabel(Node  childNode){
		String returnText = null;
		NodeList nameChildNodesList = childNode.getChildNodes();
		for(int k = 0; k < nameChildNodesList.getLength(); k++){
			Node nameChildNode = nameChildNodesList.item(k);
			if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
				if("en".equals(nameChildNode.getNodeName())){
					returnText = nameChildNode.getTextContent().trim();
					break;
				}
			}
		}
		return returnText;
	}
	
	/**
	 * For tag <description> -> <en>
	 * String[0] = user story num (US#****)
	 * String[1] = description without user story num
	 * String[2] = ADD or CREATE
	 * @param childNode
	 * @param userStoriesCommerce
	 * @return
	 */
	public static String[] readDescription(Node childNode, List<UserStories> userStories){
		String[] returnText = new String[3];
		NodeList nameChildNodesList = childNode.getChildNodes();
		for(int k = 0; k < nameChildNodesList.getLength(); k++){
			Node nameChildNode = nameChildNodesList.item(k);
			if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
				if("en".equals(nameChildNode.getNodeName())){
					if(nameChildNode.getTextContent().trim() != null && !nameChildNode.getTextContent().trim().isEmpty()){
						String[] temp = nameChildNode.getTextContent().trim().split(":");
						if(temp.length > 1 && temp[0].startsWith("US#")){
							returnText[0] = temp[0];
							String storyNum = temp[0];
							returnText[1] = temp[1];
							if(userStories.size() > 0){
								boolean storyAdded = false;
								for(UserStories story :userStories){
									if(!story.getUserStoryNum().isEmpty() && story.getUserStoryNum() != null && story.getUserStoryNum().equalsIgnoreCase(storyNum)){
										returnText[2] = "ADD";
										storyAdded = true;
										break;
									}
								}
								if(!storyAdded){
									returnText[2] = "CREATE";
								}
							}else{
								returnText[2] = "CREATE";
							}
						}
						break;
					}
				}
			}
		}
		return returnText;
		
	}

	/**
	 * For tag <name> -> <en>
	 * @param childNode
	 * @return
	 */
	public static String readName(Node childNode) {
		String returnText = null;
		NodeList nameChildNodesList = childNode.getChildNodes();
		for(int k = 0; k < nameChildNodesList.getLength(); k++){
			Node nameChildNode = nameChildNodesList.item(k);
			if(Node.ELEMENT_NODE == nameChildNode.getNodeType()){
				if("en".equals(nameChildNode.getNodeName())){
					returnText = nameChildNode.getTextContent().trim();
					break;
				}
			}
		}
		return returnText;
	}

	public static String readDataType(Node childNode) {
		return childNode.getTextContent().trim();
	}
}
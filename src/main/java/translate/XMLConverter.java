package translate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

public class XMLConverter {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		File file = null;
		try {
			file = new File(Constants.SOURCE_XML_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			String fileExt = FilenameUtils.getExtension(Constants.SOURCE_XML_FILE);
			if (Constants.XML.equals(fileExt)) {
				XMLReader xmlReader = new XMLReader();
				List<Attribute> attributeList = xmlReader.readXML(file);
				System.out.println(attributeList);
			}
			
			file = new File(Constants.SOURCE_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			fileExt = FilenameUtils.getExtension(Constants.SOURCE_FILE);
			if (Constants.DOCX_EXT.equals(fileExt)) {
				DocxFileConverter converter = new DocxFileConverter();
				converter.docxFileConverter(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

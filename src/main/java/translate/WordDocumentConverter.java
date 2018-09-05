package translate;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class WordDocumentConverter {

	public static void main(String[] args) throws Exception {
		File file = null;
		try {
			file = new File(Constants.SOURCE_FILE);
			if (!file.exists()) {
				throw new Exception("Input file not present at location : " + file.getAbsolutePath());
			}
			String fileExt = FilenameUtils.getExtension(Constants.SOURCE_FILE);
			if (Constants.DOCX_EXT.equals(fileExt)) {
				DocxFileConverter converter = new DocxFileConverter();
				converter.docxFileConverter(file,null,null,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

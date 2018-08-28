package translate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DocFileConverter {

	public void docFileConverter(File file) throws Exception {
		HWPFDocument doc = openDocFile(file);
		if (doc != null) {
			Range r = doc.getOverallRange();
			for (int i = 0; i < r.numSections(); ++i) {
				Section s = r.getSection(i);
				for (int j = 0; j < s.numParagraphs(); j++) {
					Paragraph p = s.getParagraph(j);
					for (int k = 0; k < p.numCharacterRuns(); k++) {
						CharacterRun run = p.getCharacterRun(k);
						String text = run.text();
						if (StringUtils.isNotBlank(text)) {
							run.replaceText(text.toUpperCase(), false);
						}
					}
				}
			}
		}
		saveDocFile(doc, Constants.SOURCE_FILE.replace("input", "output"));
	}

	private HWPFDocument openDocFile(File file) throws Exception {
		HWPFDocument document = null;
		if (file != null && file.exists()) {
			document = new HWPFDocument(new POIFSFileSystem(file));
		}
		return document;
	}

	private void saveDocFile(HWPFDocument doc, String file) {
		try (FileOutputStream out = new FileOutputStream(file)) {
			doc.write(out);
			System.out.println("Successfully created new file " + "at location : " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

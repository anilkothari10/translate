package translate;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class OpenNLPDetect {

	private static OpenNLPDetect nlpDetector = new OpenNLPDetect("models/english/en-sent.bin");

	private SentenceModel model;

	private OpenNLPDetect(String modelPath) {
		try {
			InputStream is = new FileInputStream(modelPath);
			model = new SentenceModel(is);
			is.close();
		} catch (Exception e) {
			throw new RuntimeException("Error initialising OpenNLP module");
		}
	}

	public String[] getSentences(String paragraph) {
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		return sdetector.sentDetect(paragraph);
	}

	public static OpenNLPDetect getNLPDetector() {
		return nlpDetector;
	}

}

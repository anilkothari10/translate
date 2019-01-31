package utilities;

import java.io.File;

public class FileSearch {
	
	private static File returnFile = null;
	
	//Returns null if not found
	public static File searchFileOrDirectory(File fileOrDirectory,String fileOrDirectStr){
		if(returnFile != null){
			returnFile = null;
		}
		return search(fileOrDirectory,fileOrDirectStr);
	}
	
	public static File search(File fileOrDirectory,String fileOrDirectStr){
		if(returnFile != null){
			return returnFile;
		}
		for(File fileOrDir : fileOrDirectory.listFiles()){
			if(fileOrDirectStr.equalsIgnoreCase(fileOrDir.getName())){
				returnFile = fileOrDir;
			}
			else if(fileOrDir.isDirectory()){
				search(fileOrDir,fileOrDirectStr);
			}
		}
		return returnFile;
	}
}

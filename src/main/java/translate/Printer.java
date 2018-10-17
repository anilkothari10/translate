package translate;

public class Printer {

	public static boolean DEBUG = false;
	public static boolean INFO = false;

	/**
	 * Log level : DEBUG
	 * @param text
	 * @return System.out.print(text);
	 */
	public static void print(String text){
		if(DEBUG){
			System.out.print(text);
		}
	}

	/**
	 * Log level : INFO
	 * @param text
	 * @return System.out.println(text)
	 */
	public static void println(String text){
		if(INFO){
			System.out.println(text);
		}
	}
}
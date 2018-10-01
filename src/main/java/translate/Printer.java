package translate;

public class Printer {

	public static boolean DEBUG = false;
	public static boolean INFO = false;

	public Printer(boolean debug) {
		this.DEBUG = debug;
		System.out.println("debug is : " + debug );
	}



	/**
	 * Log level : DEBUG
	 * @param text
	 */
	public static void print(String text){
		if(DEBUG){
			System.out.print(text);
		}
	}

	/**
	 * Log level : INFO
	 * @param text
	 */
	public static void println(String text){
		if(INFO){
			System.out.println(text);
		}
	}

	public void setDebug(boolean debug) {
		this.DEBUG = debug;
	}
}

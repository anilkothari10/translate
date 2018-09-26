package translate;

public class Printer {

	private static boolean debug = true;

	public Printer(boolean debug) {
		this.debug = debug;
		System.out.println("debug is : " + debug );
	}



	public static void print(String text){
		if(debug){
			System.out.print(text);
		}
	}


	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}

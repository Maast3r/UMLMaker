package lab22;

import java.io.Reader;;

public class Util {
	public static void processDataException(Reader in, String message, Exception e) {
		System.err.println(message + e);
		try {
			in.close();
		}
		catch(Exception ex) {
			System.err.println(ex);
		}
	}
	
	public static void transform(char mask, char[] content) {
		for(int i = 0; i < content.length; ++i) {
			char first = (char)(content[i] << mask);
			char second = (char)(content[i] >> mask);
			content[i] = (char)(first ^ second);
		}
	}
}

package testPackage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class PrintThread implements Runnable {

	public void run() {
		String data = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\singletons\n";
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		System.setIn(in);
	}
}

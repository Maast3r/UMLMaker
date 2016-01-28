package testPackage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class PrintThread implements Runnable {

	public void run() {
		String data = "uml C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\singletons\n";
		InputStream stdin = System.in;
		try {
			System.setIn(new ByteArrayInputStream(data.getBytes()));
			Scanner scanner = new Scanner(System.in);
			System.out.println(scanner.nextLine());
		} finally {
			System.setIn(stdin);
		}
	}
}

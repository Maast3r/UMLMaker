package testPackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import src.FirstASM;

public class TestSingletonDetection {

	private String dotCode;
	
	@Before
	public void setup() throws IOException {
		String[] args = {"10"};
//		String data = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\singletons";
		String data = "uml C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\singletons";
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		System.setIn(in);
		FirstASM.main(args);
	}
	
	@Before
	public void readDot() throws IOException {
//		byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\singletons.dot"));
		byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\singletons.dot"));
		this.dotCode = new String(encoded);
	}
	
	@Test
	public void testRuntime() {
		Assert.assertTrue(this.dotCode.contains("Runtime\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testCalendar() {
		Assert.assertTrue(!this.dotCode.contains("Calendar\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testDesktop() {
		Assert.assertTrue(!this.dotCode.contains("Desktop\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testFilterInputStream() {
		Assert.assertTrue(!this.dotCode.contains("FilterInputStream\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testSingletonEager() {
		Assert.assertTrue(this.dotCode.contains("SingletonEager\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testSingletonLazy() {
		Assert.assertTrue(this.dotCode.contains("SingletonLazy\\n\\<\\<Singleton\\>\\>"));
	}
}

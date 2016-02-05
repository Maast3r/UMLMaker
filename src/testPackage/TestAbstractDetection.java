package testPackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import src.FirstASM;

public class TestAbstractDetection {

	private String dotCode;
	
	@Before
	public void setup() throws IOException {
		String[] args = {"10"};
//		String data = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab5one";
		String data = "uml C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\lab5one";
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		System.setIn(in);
		FirstASM.main(args);
	}
	
	@Before
	public void readDot() throws IOException {
//		byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab5one.dot"));
		byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\lab5one.dot"));
		this.dotCode = new String(encoded);
	}
	
	@Test
	public void testDetection() {
		Assert.assertTrue(this.dotCode.contains("IteratorToEnumerationAdapter -> Iterator [arrowhead = vee,label=\"\\<\\<Adapts\\>\\>"));
	}

}

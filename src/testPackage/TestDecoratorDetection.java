package testPackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import src.FirstASM;

public class TestDecoratorDetection {

	private String dotCode;
	
	@Before
	public void setup() throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] args = {"10"};
//		String data = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab2one";
		String data = "uml C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\lab2one";
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		System.setIn(in);
		FirstASM.main(args);
	}
	
	@Before
	public void readDot() throws IOException {
//		byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab2one.dot"));
		byte[] encoded = Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\CSSE374-201620\\UMLMaker\\src\\lab2one.dot"));
		this.dotCode = new String(encoded);
	}
	
	@Test
	public void testDetection() {
		Assert.assertTrue(this.dotCode.contains("EncryptionOutputStream -> IEncryption[arrowhead = vee, style = dotted]"));
		Assert.assertTrue(this.dotCode.contains("EncryptionOutputStream -> IEncryption[arrowhead = vee]"));
		Assert.assertTrue(this.dotCode.contains("DecryptionInputStream -> IDecryption[arrowhead = vee, style = dotted]"));
		Assert.assertTrue(this.dotCode.contains("DecryptionInputStream -> IDecryption[arrowhead = vee]"));
	}

}

package testPackage;
import org.objectweb.asm.ClassVisitor;
import src.ClassFieldVisitor;


public class TestClass extends ClassFieldVisitor{
	private int nathanHatesFields;
	public TestClass(int api, ClassVisitor cv){
		super(api, null);
		//super(api, cv);
	}
}

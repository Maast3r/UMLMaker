package testPackage;
import org.objectweb.asm.ClassVisitor;
import src.ClassFieldVisitor;


public class TestClass extends ClassFieldVisitor{
	public TestClass(int api, ClassVisitor cv){
		super(api, cv);
	}
}

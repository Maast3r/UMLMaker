package testPackage;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import src.ClassDeclarationVisitor;
import src.ClassFieldVisitor;
import src.ClassVisitorBuffered;
import src.NoahsArk;

public class TestClassVisitorBuffered {

	@Test
	public void test() throws IOException {
		NoahsArk ark = new NoahsArk();
		String pkg = "testPackage.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "TestClass");
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf, ark);

		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor, ark.getBoat().get(declVisitor.getName()));
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);

		Assert.assertTrue(buf.toString().contains("-nathanHatesFields"));
		
	}

}

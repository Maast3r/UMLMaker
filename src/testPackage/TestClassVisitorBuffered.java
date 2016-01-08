package testPackage;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import src.ClassDeclarationVisitor;
import src.ClassFieldVisitor;
import src.ClassVisitorBuffered;

public class TestClassVisitorBuffered {

	@Test
	public void test() throws IOException {
		String pkg = "testPackage.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "TestClass");
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf);

		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);

		Assert.assertTrue(buf.toString().contains("-nathanHatesFields"));
		
	}

}

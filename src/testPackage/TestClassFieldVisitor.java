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

public class TestClassFieldVisitor {

	@Test
	public void test() throws IOException {
		NoahsArk ark = new NoahsArk();
		StringBuffer expected = new StringBuffer();
		expected.append("ClassPrototype [ \n"
				+ "    label=\"{ClassPrototype|ClassPrototype [ \n"
				+ "    label=\"{ClassPrototype|+name: String \\l+fields: HashMap \\l+methods: HashMap \\l}\" ]");
		
		String pkg = "src.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "ClassPrototype");

		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf, ark);
		
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);

		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor, ark.getBoat().get(declVisitor.getName()));
		
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\" ]");
		System.out.println(expected.toString());
		System.out.println(buf.toString());
		
		Assert.assertTrue(buf.toString().equals(expected.toString()));
		
	}

}

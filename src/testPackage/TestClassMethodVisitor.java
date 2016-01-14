package testPackage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import src.ClassDeclarationVisitor;
import src.ClassFieldVisitor;
import src.ClassVisitorBuffered;
import src.DotMethodVisitor;
import src.NoahsArk;

public class TestClassMethodVisitor {

	@Test
	public void test() throws IOException {
		NoahsArk ark = new NoahsArk();
		StringBuffer expected = new StringBuffer();
		expected.append("ClassVisitorBuffered [ \n"
				+ "    label=\"{ClassVisitorBuffered|ClassVisitorBuffered [ \n"
				+ "    label=\"{ClassVisitorBuffered|+name: String \\lbuf: StringBuffer \\lClassVisitorBuffered [ \n"
				+ "    label=\"{ClassVisitorBuffered|+name: String \\lbuf: StringBuffer \\l+\\<init\\>(int, ClassVisitor, StringBuffer): void\\l+\\<init\\>(int): void\\l+\\<init\\>(int, StringBuffer): void\\l+\\<init\\>(int, ClassVisitor): void\\l+getAccessModifier(int): String\\l+getName(): String\\l}\" ]");
		
		String pkg = "src.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "ClassVisitorBuffered");

		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf, ark);

		
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor, ark.getBoat().get(declVisitor.getName()));
	
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);

//		buf.append(" | ");

		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
				Opcodes.ASM5, fieldVisitor, buf, ark.getBoat().get(declVisitor.getName()));

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\" ]");
//		System.out.println(expected.toString());
//		System.out.println(buf.toString());
		
		Assert.assertTrue(buf.toString().equals(expected.toString()));
	
	}

}

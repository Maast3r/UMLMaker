package testPackage;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import src.ClassDeclarationVisitor;
import src.ClassFieldVisitor;
import src.ClassVisitorBuffered;

public class TestClassFieldVisitor {

	@Test
	public void test() throws IOException {
		
		StringBuffer expected = new StringBuffer();
		expected.append("AppLauncher [ " + "\n" + "    label=\"{AppLauncher|-watcher: WatchService"
				+ " \\l-dir: Path \\l-stop: boolean \\l-processes: List \\l}\" ]");
		
		String pkg = "target.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "AppLauncher");

		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf);

		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\" ]");
//		System.out.println(expected.toString());
//		System.out.println(buf.toString());
		
		Assert.assertTrue(buf.toString().equals(expected.toString()));
		
	}

}

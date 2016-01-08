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

public class TestDotMethodVisitor {

	@Test
	public void testDotMethodVisitorOne() throws IOException {
		StringBuffer expected = new StringBuffer();
		expected.append("\\<init\\>(Path): void\\l+run(): void\\l#clearEverything(): void\\l+stopGracefully(): void\\l+isRunning(): boolean\\l+getApplicationsCount(): int\\l+handleDirectoryEvent(String, Path): void\\l+main(String[]): void\\l+notifyChanges(FileLauncher, String): void\\l+getProcesses(): List\\l+setProcesses(List): void\\l}\" ]");
		
		String pkg = "target.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "AppLauncher");

		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
				Opcodes.ASM5, buf);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\" ]");
		System.out.println(expected.toString());
		System.out.println(buf.toString());
		
		Assert.assertTrue(buf.toString().equals(expected.toString()));
	
	}

}

package src;


import java.io.IOException;
import java.util.HashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class FirstASM {
	public static String myField = "Hello World!";
	public static void main(String[] args) throws IOException{
		ClassReader reader = new ClassReader("testPackage.TestClass");
		
		HashMap<String, Boolean> classNames = new HashMap<String, Boolean>();
		StringBuffer buf = new StringBuffer();
		
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, buf);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
		
		ClassVisitorBuffered methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		System.out.println(buf.toString());
//		
//		ClassVisitor visitor = new TraceClassVisitor(
//				new PrintWriter(System.out));
//		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
	}
}

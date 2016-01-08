package src;


import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class DesignParser {
	public static void main(String[] args) throws IOException{
		for(String className: args){
			ClassReader reader = new ClassReader(className);
			
			ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
			
			ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
			
			ClassVisitorBuffered methodVisitor = new DotMethodVisitor(Opcodes.ASM5, fieldVisitor);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
	}
}

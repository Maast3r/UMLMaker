package src;

import org.objectweb.asm.MethodVisitor;

public interface IMethodVisitor {
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions);

	
}

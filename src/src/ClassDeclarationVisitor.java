package src;


import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitorBuffered {

	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}
	public ClassDeclarationVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}

	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces){
		buf.append("Class: " + name + " extends "+superName+" implements "+Arrays.toString(interfaces) + "\n");
		super.visit(version, access, name, signature, superName, interfaces);
	}
}

package src;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class MethodBodyVisitor extends MethodVisitor{
	
	public MethodBodyVisitor(int arg0){
		super(arg0);
	}

	public MethodBodyVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visitMethodInsn(int access, String owner, String name, String desc, boolean isIn){
		owner = owner.replaceAll("/", ".");
		System.out.println("OWNER : " + owner);
		System.out.println("access " + access + "  owner " + owner + " name "  + name + " desc " + desc + " isIn " + isIn);
	}

}

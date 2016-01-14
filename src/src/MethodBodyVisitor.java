package src;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class MethodBodyVisitor extends MethodVisitor{
	public String owner;
	public String className;
	public NoahsArk ark;
	
	public MethodBodyVisitor(int arg0){
		super(arg0);
	}

	public MethodBodyVisitor(int arg0, MethodVisitor arg1, String className, NoahsArk ark) {
		super(arg0, arg1);
		this.className = className;
		this.ark = ark;
	}
	
	@Override
	public void visitMethodInsn(int access, String owner, String name, String desc, boolean isIn){
//		owner = owner.replace("/", ".");
		if(owner.contains("/")){
			int len = owner.split("/").length;
			owner = owner.split("/")[len -1 ];
		}
		this.owner = owner;
		System.out.println("-------------------------------- " + this.className + "   " + this.owner);
		this.ark.addPair(this.className, "$" + this.owner);
//		System.out.println("Name: " + name + " OWNER : " + owner);		
//		System.out.println("access " + access + "  owner " + owner + " name "  + name + " desc " + desc + " isIn " + isIn);
	}

}

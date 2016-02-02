package src;

import org.objectweb.asm.MethodVisitor;

public class MethodBodyUsesVisitor extends MethodVisitor{
	public NoahsArk ark;
	public String className;
	public String owner;
	StringBuffer buf;
	
	public MethodBodyUsesVisitor(int asm5, MethodVisitor toDecorate, NoahsArk ark, String className, StringBuffer buf) {
		super(asm5, toDecorate);
		this.ark = ark;
		this.className = className;
		this.buf = buf;
	}

	@Override
	public void visitMethodInsn(int access, String owner, String name, String desc, boolean isIn){
		String newPkg = owner;
		if(owner.contains("/")){
			int len = owner.split("/").length;
			owner = owner.split("/")[len -1 ];
		}
		this.owner = owner;
		this.ark.addPair(this.className, "#" + this.owner);
	}
}

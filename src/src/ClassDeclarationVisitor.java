package src;


import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitorBuffered {
	public NoahsArk ark;
//	public String name;
	
	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}
	
	public ClassDeclarationVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}
	
	public ClassDeclarationVisitor(int arg0, StringBuffer buf, NoahsArk ark) {
		super(arg0);
		this.buf = buf;
		this.ark = ark;
	}

	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces){
		String realname = name;
		if(realname.contains("/")){
			realname = name.split("/")[1];
		}
		this.name = name;
		if(this.name.contains("/")){
			this.name = this.name.split("/")[1];
		}
//		buf.append("Class: " + name + " extends "+superName+" implements "+Arrays.toString(interfaces) + "\n");
		ark.getBoat().put(realname, new ClassPrototype(realname));
		buf.append(realname + " [ \n    label=\"{"+realname+"|");
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public String getName(){
		return this.name;
	}
}

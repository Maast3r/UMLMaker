package src;


import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitorBuffered {
	public NoahsArk ark;
//	public String name;
	
	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}
	
	public ClassDeclarationVisitor(int arg0, NoahsArk ark2) {
		super(arg0);
		this.ark= ark2;
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
		String[] pkgSplit = null;
		if(realname.equals("Object")){
			System.out.println(superName);
		}
		String pkg = "";
		if(superName != null){
			if(superName.contains("/")){
				pkgSplit = superName.split("/");
				for(int i=0; i<pkgSplit.length-1; i++){
					pkg += pkgSplit[i] + ".";
				}
				superName = pkgSplit[pkgSplit.length-1];
				System.out.println("pkg : " + pkg +  "   name " + superName);
				this.ark.getNewList().put(superName, pkg);
			}
		}
		
		this.ark.addClass(realname, new ClassPrototype(realname, superName, interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public String getName(){
		return this.name;
	}
}

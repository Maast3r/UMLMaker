package src;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ClassDeclarationVisitor extends ClassVisitorBuffered {
	public NoahsArk ark;
	
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
		String classPkg = "";
		if(realname.contains("/")){
			realname = name.split("/")[name.split("/").length-1];
			for(int i=0; i<name.split("/").length-1; i++){
				classPkg += name.split("/")[i] + ".";
			}
		}
		this.name = name;
		if(this.name.contains("/")){
			this.name = this.name.split("/")[name.split("/").length-1];
		}
		String[] pkgSplit = null;
		if(realname.equals("Object")){
//			System.out.println(superName);
		}
		String pkg = "";
		if(superName != null){
			if(superName.contains("/")){
				pkgSplit = superName.split("/");
				for(int i=0; i<pkgSplit.length-1; i++){
					pkg += pkgSplit[i] + ".";
				}
				superName = pkgSplit[pkgSplit.length-1];
				if(!this.ark.seenClass.containsKey(superName) && this.ark.seenClass.size() < this.ark.umlNodes){
					if(!pkg.equals("java.lang.")){
						this.ark.getNewList().put(superName, pkg);
						this.ark.seenClass.put(superName, pkg);
					}
				}
			}
		}
		
		if(interfaces != null){
			for(String intfc : interfaces){
				String[] interfSplit = null;
				pkg = "";
				String iface;
				if(intfc.contains("/")){
					interfSplit = intfc.split("/");
					for(int i=0; i<interfSplit.length-1; i++){
						pkg += interfSplit[i] + ".";
					}
					iface = interfSplit[interfSplit.length - 1];
					if(!this.ark.seenClass.containsKey(iface) && this.ark.seenClass.size() < this.ark.umlNodes){
						if(!pkg.equals("java.lang.")) {
							this.ark.getNewList().put(iface, pkg);
							this.ark.seenClass.put(iface, pkg);
						}
					}
				}
			}
		}
		this.ark.seenClass.put(realname, classPkg);
		this.ark.addClass(realname, new ClassPrototype(realname, superName, interfaces));
		this.ark.getBoat().get(realname).isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
		this.ark.getBoat().get(realname).pkg = classPkg;
		this.ark.getBoat().get(realname).isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public String getName(){
		return this.name;
	}
}

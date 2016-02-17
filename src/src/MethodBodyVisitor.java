package src;

import java.io.IOException;
import java.util.HashSet;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MethodBodyVisitor extends MethodVisitor{
	public String owner;
	public String className;
	public NoahsArk ark;
	public String inputMethodName;
	public String inputArgs;
	public String methodName;
	
	public MethodBodyVisitor(int arg0){
		super(arg0);
	}

	public MethodBodyVisitor(int arg0, MethodVisitor arg1, String className, NoahsArk ark, String inputMethodName, String inputArgs, String methodName) {
		super(arg0, arg1);
		this.className = className;
		this.ark = ark;
		this.inputMethodName = inputMethodName;
		this.inputArgs = inputArgs;
		this.methodName = methodName;
	}
	
	public MethodBodyVisitor(int asm5, MethodVisitor toDecorate) {
		super(asm5, toDecorate);
	}

	@Override
	public void visitMethodInsn(int access, String owner, String name, String desc, boolean isIn){
		String newPkg = owner;
		if(owner.contains("/")){
			int len = owner.split("/").length;
			owner = owner.split("/")[len -1 ];
		}
		this.owner = owner;
		if(this.ark.getCmd().equals(("uml")))this.ark.addPair(this.className, "#" + this.owner);
		
		ark.getBoat().get(this.className).methods.get(methodName).typesSeen.add(owner);
		ark.getBoat().get(this.className).methods.get(methodName).calls.add(owner+"."+name);
		
		if(this.ark.getCmd().equals(("sequence"))){
			ark.newNodes.add("/" + className + "#" + owner);
			if(name.equals("<init>")){
				// Create a new node
				String node =  "";
				if(owner.contains("$")) {
					owner = owner.split("\\$")[1];
				}
				node = "/" + className + "#" + owner;
				ark.constructedNodes.add(node);
				ark.sequenceNodes.add(node);
			} else {
				String args = "";
				for(Type t : Type.getArgumentTypes(desc)){
					String temp = t.getClassName();
					if(temp.contains(".")){
						int len = temp.split("\\.").length;
						temp = temp.split("\\.")[len -1];
					}
					args += temp + ",";
				}
				if(args.length() >= 1)args = args.substring(0,args.length()-1);
				ark.sequenceNodes.add(className + ":" + Type.getReturnType(desc).getClassName() + "=" + owner + "." + name + "(" + args + ")");
				if(this.ark.getDepthMax() > 0){
					ark.sequenceNodes.add("GO DEEPER");
					try {
						newPkg = newPkg.replace("/", ".");
						String s = "";
						String[] sa = newPkg.split("\\.");
						for(int i=0; i<sa.length-1; i++){
							s += sa[i] + ".";
						}
						this.ark.setPackage(s);
						repeat(name, args);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ark.sequenceNodes.add("GO UPPER");
				}
			}
		}
	}
	
	
	public void repeat(String targetMethod, String targetArgs) throws IOException{
		this.ark.deeper();
		try{
			ark.getBoat().put(this.owner, new ClassPrototype(this.owner));
			ClassReader reader = new ClassReader(this.ark.getPackage() + this.owner);
			ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
					Opcodes.ASM5, this.ark, this.owner, targetMethod, targetArgs);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		this.ark.goUp();
	}

}

package src;

import java.io.IOException;
import java.util.ArrayList;

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
	
	public MethodBodyVisitor(int arg0){
		super(arg0);
	}

	public MethodBodyVisitor(int arg0, MethodVisitor arg1, String className, NoahsArk ark, String inputMethodName, String inputArgs) {
		super(arg0, arg1);
		this.className = className;
		this.ark = ark;
		this.inputMethodName = inputMethodName;
		this.inputArgs = inputArgs;
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
		
		
		System.out.println("---------------" + this.ark.newNodes);
		
		if(!isIn && !this.owner.equals("Object") && this.ark.getCmd().equals(("sequence"))){
			System.out.println(this.ark.getDepthMax() + " a;slkdfja   " +   " " + owner + " " + name + " " + " " + Type.getReturnType(desc).getClassName() + " " + isIn + " LOOKING FOR: " + this.inputMethodName);
//			System.out.println(this.ark.newNodes + "\n");
//			System.out.println("DEEPER");
			ark.newNodes.add("/" + className + "#" + owner);
			if(name.equals("<init>")){
//				System.out.println(ark.sequenceNodes);
				System.out.println("add new1111111111111111111111111111111111111111111111111");
				
				// Create a new node
//				System.out.println("add new node");
				String node =  "";
				if(owner.contains("$")) {
					owner = owner.split("\\$")[1];
				}
				node = "/" + className + "#" + owner;
				ark.constructedNodes.add(node);
				ark.sequenceNodes.add(node);
	
//				System.out.println(ark.newNodes);
			} else {
				// Add Method call to diagram
//				String test = "/" + className + ":" + owner;
//				String test2 = className + ":" + owner;
////				System.out.println("-------------------    " + test + " " + test2 + !this.ark.newNodes.contains(test) 
////						+ " " + !this.ark.newNodes.contains(test2));
//				if(!this.ark.newNodes.contains(test) 
//						&& !this.ark.newNodes.contains(test2)) this.ark.newNodes.add(test);
				String args = "";
				for(Type t : Type.getArgumentTypes(desc)){
					String temp = t.getClassName();
//					System.out.println("ARGS ARE: " + temp);
					if(temp.contains(".")){
						int len = temp.split("\\.").length;
						temp = temp.split("\\.")[len -1];
					}
					args += temp + ",";
				}
				if(args.length() >= 1)args = args.substring(0,args.length()-1);
				ark.sequenceNodes.add(className + ":" + Type.getReturnType(desc).getClassName() + "=" + owner + "." + name + "(" + args + ")");
//				System.out.println(ark.sequenceNodes);
				// Goes deeper if class in package
				if(this.ark.getDepthMax() > 0){
					ark.sequenceNodes.add("GO DEEPER");
//					System.out.println(ark.sequenceNodes);
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ark.sequenceNodes.add("GO UPPER");
					//Iterate on the next level down
//					System.out.println(ark.sequenceNodes);
//					System.out.println("gooby, deeper pls");
				}
			}
		}
	}
	
	public void repeat(String targetMethod, String targetArgs) throws IOException{
		this.ark.deeper();
//		System.out.println(targetMethod + "  repeat ark package " + this.ark.getPackage() + this.owner);
//		String test = this.ark.getPackage() + this.owner;
//		if(this.owner.equals("AtomicLong")) {
//			this.ark.setPackage("java.util.concurrent.atomic.");
//		} else {
//			this.ark.setPackage("java.util.");
//		}
//		System.out.println("--- " + this.ark.getPackage());
		try{
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

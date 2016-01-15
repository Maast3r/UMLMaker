package src;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.lang.model.util.Types;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
		if(owner.contains("/")){
			int len = owner.split("/").length;
			owner = owner.split("/")[len -1 ];
		}
		this.owner = owner;
		this.ark.addPair(this.className, "#" + this.owner);
		System.out.println("depth " + this.ark.getDepthMax());
		
		
//		Class c;
//		try {
//			System.out.println(this.ark.pkg + owner);
//			c = (Class) Class.forName(this.ark.pkg + owner);
//			Method[] ms = c.getDeclaredMethods();
//			c.get
//			for(Method t : ms){
//				System.out.println(t.getName().toString());
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("a;slkdfja   " +   " " + owner + " " + name + " " + desc + " " + isIn + " " + this.ark.getPackage());

		if(this.ark.getListOfClass().get(this.owner) != null){
			if(name.equals("<init>")){
				System.out.println("add new");
				// Create a new node
				String node =  "t/:" + className;
				ark.sequenceNodes.add(node);
				
				System.out.println(ark.sequenceNodes);
			} else {
				// Add Method call to diagram
				String args = "";
				for(Type t : Type.getArgumentTypes(desc)){
					String temp = t.getClassName();
					if(temp.contains("/")){
						int len = temp.split("/").length;
						temp = temp.split("/")[len -1 ];
					}
					
					args += temp + ", ";
					
				}
				if(args.length() >= 2)args = args.substring(0,args.length()-2);
				ark.sequenceNodes.add(className + ":" + owner + "." + name + "(" + args + ")");
				System.out.println(ark.sequenceNodes);
				// Goes deeper if class in package
				if(this.ark.getDepthMax() > 0){
					ark.sequenceNodes.add("GO DEEPER");
					try {
						repeat();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Iterate on the next level down
					System.out.println("gooby, deeper pls");
				}
			}
		}
	}
	
	public void addToPairs(){
		
	}
	
	public void repeat() throws IOException{
		this.ark.deeper();
		ClassReader reader = new ClassReader(this.ark.getPackage() + this.owner);
		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
				Opcodes.ASM5, this.ark, this.owner);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		this.ark.goUp();
	}

}

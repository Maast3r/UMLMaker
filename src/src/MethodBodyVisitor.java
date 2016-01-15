package src;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
		System.out.println("a;slkdfja   " + access + " " + owner + " " + name + " " + desc + " " + isIn + " " + this.ark.getPackage());
		
		if(this.ark.getListOfClass().get(this.owner) != null){
			if(name.equals("<init>")){
				// add new node
				System.out.println("add new");
			} else {
				if(this.ark.getDepthMax() > 0){
					//go deeper.
					System.out.println("gooby, deeper pls");
				}
			}
		}
	}
	
	public void addToPairs(){
		
	}
	
	public void repeat() throws IOException{
		ClassReader reader = new ClassReader(this.ark.getPackage() + this.owner);
		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
				Opcodes.ASM5, this.ark, this.owner);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	}

}

package src;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class ClassVisitorBuffered extends ClassVisitor{
	public String name;
	StringBuffer buf;
	public ClassVisitorBuffered(int arg0, ClassVisitor arg1, StringBuffer buf) {
		super(arg0, arg1);
		this.buf = buf;
		// TODO Auto-generated constructor stub
	}
	public ClassVisitorBuffered(int arg0){
		super(arg0);
	}
	
	public ClassVisitorBuffered(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}
	
	public ClassVisitorBuffered(int arg0, ClassVisitor cv){
		super(arg0, cv);
	}
	
	public String getAccessModifier(int access){
		String accessModifier = "";
		if((access & Opcodes.ACC_PUBLIC) !=0){
			accessModifier="+";
		}
		if((access & Opcodes.ACC_PRIVATE) !=0){
			accessModifier="-";
		}
		if((access & Opcodes.ACC_PROTECTED) !=0){
			accessModifier="#";
		}
		if((access & Opcodes.ACC_STATIC) !=0){
			accessModifier+="static ";
		}
		return accessModifier;
	}
	
	public String getName(){
		return this.name;
	}
}

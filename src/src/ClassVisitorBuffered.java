package src;

import org.objectweb.asm.ClassVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ClassVisitorBuffered extends ClassVisitor{

	StringBuffer buf;
	public ClassVisitorBuffered(int arg0, ClassVisitor arg1, StringBuffer buf) {
		super(arg0, arg1);
		this.buf = buf;
		// TODO Auto-generated constructor stub
	}
	public ClassVisitorBuffered(int arg0){
		super(arg0);
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
		return accessModifier;
	}
	
}

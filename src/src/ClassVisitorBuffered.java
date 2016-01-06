package src;

import org.objectweb.asm.ClassVisitor;

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
	
}

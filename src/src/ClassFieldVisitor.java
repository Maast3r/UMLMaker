package src;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import com.sun.org.glassfish.gmbal.Description;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ClassFieldVisitor extends ClassVisitorBuffered {

	public ClassFieldVisitor(int arg0, ClassVisitorBuffered arg1) {
		super(arg0, arg1);
		this.buf = arg1.buf;
	}
	
	public FieldVisitor visitField(int access, String name, String desc, String signature, 
			Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		
		String symbol= getAccessModifier(access);
		
		buf.append("    " + symbol + " " + desc +" "+ name + "\n");
		return toDecorate;
	}
}

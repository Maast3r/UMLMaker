package src;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import com.sun.org.glassfish.gmbal.Description;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class DotAssociationVisitor extends ClassVisitorBuffered {

	public DotAssociationVisitor(int arg0, StringBuffer buf){
		super(arg0);
		this.buf = buf;
	}
	public DotAssociationVisitor(int arg0, ClassVisitorBuffered arg1) {
		super(arg0, arg1);
		this.buf = arg1.buf;
	}
	
	public FieldVisitor visitField(int access, String name, String desc, String signature, 
			Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		if(type.contains(".")){
			String[] temparray = type.split("\\.");
			type = temparray[temparray.length - 1];
		}
		String symbol= getAccessModifier(access);
		
		buf.append(type + "\n");
		return toDecorate;
	}
}

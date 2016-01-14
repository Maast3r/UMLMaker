package src;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import com.sun.org.glassfish.gmbal.Description;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ClassFieldVisitor extends ClassVisitorBuffered {
	public NoahsArk ark;
	public String className;
	
	public ClassFieldVisitor(int arg0, ClassVisitorBuffered arg1, NoahsArk ark, String className) {
		super(arg0, arg1);
		this.buf = arg1.buf;
		this.ark = ark;
		this.className = className;
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
		this.ark.getBoat().get(this.className).addField(name, new FieldPrototype(symbol, name, type));
//		this.pro.addField(name, new FieldPrototype(symbol, name, type));
//		buf.append(symbol + name + ": "+ type + " \\l");
		return toDecorate;
	}
}

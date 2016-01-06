package src;


import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ClassMethodVisitor extends ClassVisitorBuffered {

	public ClassMethodVisitor(int arg0, ClassVisitorBuffered arg1) {
		super(arg0, arg1);
		this.buf = arg1.buf;
	}
	public ClassMethodVisitor(int arg0, StringBuffer buf){
		super(arg0);
		this.buf = buf;
	}
	
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
		}
		
		String symbol= getAccessModifier(access);
		
		this.buf.append(symbol + name.replace("<", "\\<").replace(">", "\\>") + "(): " + Type.getReturnType(desc).getClassName() + '\\' + 'l' );
		
		return toDecorate;
	}
}

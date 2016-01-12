package src;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import jdk.internal.org.objectweb.asm.Opcodes;

public class DotUsesVisitor extends ClassVisitorBuffered implements IMethodVisitor{

	public DotUsesVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public DotUsesVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions){
		
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		toDecorate.visitMethodInsn(Opcodes.ASM5 & Opcodes.NEW, toDecorate.getClass().toString(), name, desc, true);
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		String args = "";
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
			String temp = argTypes[i].getClassName();
			
			if(temp.contains(".")){
				String[] temparray = temp.split("\\.");
				temp = temparray[temparray.length - 1];
				
			}
			args += temp + ", ";
		}
		if(args.length()>=2)args = args.substring(0, args.length() -2);
		
		String symbol= getAccessModifier(access);
		String returnName = Type.getReturnType(desc).getClassName();
		if(returnName.contains(".")){
			String[] temparray = returnName.split("\\.");
			returnName = temparray[temparray.length - 1];
		}
		this.buf.append(name.replace("<", "\\<").replace(">", "\\>") + ":"+ args + ", " + returnName + "\n");
		
		
		
		
//		System.out.println(name.replace("<", "\\<").replace(">", "\\>") + ":"+ args +", "+ returnName + "\n");
		
		
		return toDecorate;
	}
}
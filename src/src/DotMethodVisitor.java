package src;


import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.sun.xml.internal.ws.wsdl.writer.document.Types;

import jdk.internal.org.objectweb.asm.commons.InstructionAdapter;

public class DotMethodVisitor extends ClassVisitorBuffered {
	public int arg0;
	public NoahsArk ark;
	public String className;
	public String inputMethodName = "";
	public String inputArgs = "";
	
	public DotMethodVisitor(int arg0, ClassVisitorBuffered arg1, NoahsArk ark, String className) {
		super(arg0, arg1);
		this.arg0 = arg0;
		this.ark = ark;
		this.className = className;
	}
	public DotMethodVisitor(int arg0, StringBuffer buf){
		super(arg0);
		this.buf = buf;
		this.arg0 = arg0;
	}
	
	public DotMethodVisitor(int asm5, NoahsArk ark, String inputClass, String inputMethodName, String inputArgs) {
		super(asm5);
		this.arg0 = asm5;
		this.ark = ark;
		this.className = inputClass;
		this.inputMethodName = inputMethodName;
		this.inputArgs = inputArgs;
	}
	
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
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
			args += temp + ",";
		}
		if(args.length() > 1 )args = args.substring(0, args.length()-1);
		MethodVisitor test = null;
		if((this.ark.getCmd().equals("sequence") && this.inputMethodName.equals(name) && this.inputArgs.equals(args))
				|| this.ark.getCmd().equals("uml")){
			test = new MethodBodyVisitor(Opcodes.ASM5, toDecorate, this.className, this.ark, this.inputMethodName, this.inputArgs);
		}
		
		
		
		if(args.length()>=2)args = args.substring(0, args.length() -2);
		
		String symbol= getAccessModifier(access);
		String returnName = Type.getReturnType(desc).getClassName();
		if(returnName.contains(".")){
			String[] temparray = returnName.split("\\.");
			returnName = temparray[temparray.length - 1];
		}
		if(this.ark.getBoat().get(this.className) != null)this.ark.getBoat().get(this.className).addMethod(name, new MethodPrototype(symbol, name, args, returnName));
		
		return test;
	}
	
}

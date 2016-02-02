package src;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class DotUsesVisitor extends ClassVisitorBuffered{
	public int arg0;
	
	public DotUsesVisitor(int arg0) {
		super(arg0);
	}
	
	public DotUsesVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.arg0 = arg0;
		this.buf = buf;
	}

	@Override
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
//		System.out.println("SUPER " + super.ark + " " + super.className);
		MethodVisitor test = new MethodBodyUsesVisitor(Opcodes.ASM5, toDecorate, super.ark, super.className, buf);
		
		
		
		if(args.length()>=2)args = args.substring(0, args.length() -2);
		
		String symbol= getAccessModifier(access);
		String returnName = Type.getReturnType(desc).getClassName();
		if(returnName.contains(".")){
			String[] temparray = returnName.split("\\.");
			returnName = temparray[temparray.length - 1];
		}
		
		return test;
	}
}

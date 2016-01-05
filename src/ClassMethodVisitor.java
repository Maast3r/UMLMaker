

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ClassMethodVisitor extends ClassVisitor {

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
		}
		String symbol="";
		if((access & Opcodes.ACC_PUBLIC) !=0){
			symbol="+";
		}
		
		System.out.println("   method    "+symbol+name+"  "+Arrays.toString(classNames)+ " "+ Type.getReturnType(desc).getClassName());
		
		return toDecorate;
	}
}

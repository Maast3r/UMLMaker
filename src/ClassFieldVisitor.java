

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import com.sun.org.glassfish.gmbal.Description;

public class ClassFieldVisitor extends ClassVisitor {

	public ClassFieldVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	public FieldVisitor visitField(int access, String name, String desc, String signature, 
			Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		System.out.println("    "+desc+" "+name);
		return toDecorate;
	}
}

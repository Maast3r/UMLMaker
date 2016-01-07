package src;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassInheritanceVisitor extends ClassVisitorBuffered {

	public ClassInheritanceVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public ClassInheritanceVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces){
		buf.append(name + ":"+superName+"#"+Arrays.toString(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}

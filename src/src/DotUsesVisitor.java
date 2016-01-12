package src;

import java.io.IOException;
import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class DotUsesVisitor extends ClassVisitorBuffered{
	public String pkg = "target.";
	public String className;
	public DotUsesVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public DotUsesVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces){
		System.out.println("DOT USES VISITOR");
		String realname = name;
		if(realname.contains("/")){
			realname = name.split("/")[1];
		}

		buf.append(realname + " [ \n    label=\"{"+realname+"|");
		
		try {
			this.className = name;
			System.out.println(pkg + realname);
			ClassReader reader = new ClassReader(pkg + realname);
			ClassVisitorBuffered methodVisitor = new DotUsesMethodVisitor(Opcodes.ASM5, buf, realname);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

}

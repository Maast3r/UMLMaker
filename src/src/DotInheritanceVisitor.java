package src;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class DotInheritanceVisitor extends ClassVisitorBuffered {

	public DotInheritanceVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public DotInheritanceVisitor(int arg0, StringBuffer buf) {
		super(arg0);
		this.buf = buf;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces){
		
//		buf.append(name + ":"+superName+"#"+Arrays.toString(interfaces));
		
		
//		String extendStuff = buf.toString().split(":")[1];
		String extendName = superName;
		String implementStuff = Arrays.toString(interfaces).replace("[", "").replace("]", "");
		String[] implementing = implementStuff.split(",");
		if (name.contains("/")) {
			int len = name.split("/").length;
			name = name.split("/")[len - 1];
		}
		for (String s : implementing) {
			if (s.contains("/")) {
				int len = s.split("/").length;
				s = s.split("/")[len - 1];
			}
			if (ark.seenClass.get(s) != null) {
				if (!s.equals(""))
					super.ark.addPair(name, "@" + s);

			}
		}
		if (extendName != null){
			if (extendName.contains("/")) {
				int len = extendName.split("/").length;
				extendName = extendName.split("/")[len - 1];
			}
//			System.out.println(name + " Should extend " + extendName);
//			if (ark.seenClass.get(extendName) != null) {
				super.ark.addPair(name,"!" + extendName);
//				System.out.println(super.ark.pairs.keySet());
//			}
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
}

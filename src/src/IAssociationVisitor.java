package src;

import java.util.Arrays;

public interface IAssociationVisitor {
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces);

	void visit(int access, String name, String desc, String signature, String[] exceptions);
}

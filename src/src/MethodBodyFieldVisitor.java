package src;

import java.io.IOException;
import java.util.HashSet;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MethodBodyFieldVisitor extends MethodVisitor{
	public String owner;
	public String className;
	public NoahsArk ark;
	public String inputMethodName;
	public String inputArgs;
	public String methodName;
	
	public MethodBodyFieldVisitor(int arg0){
		super(arg0);
	}

	public MethodBodyFieldVisitor(int arg0, MethodVisitor arg1, String className, NoahsArk ark, String inputMethodName, String inputArgs, String methodName) {
		super(arg0, arg1);
		this.className = className;
		this.ark = ark;
		this.inputMethodName = inputMethodName;
		this.inputArgs = inputArgs;
		this.methodName = methodName;
	}
	
	public MethodBodyFieldVisitor(int asm5, MethodVisitor toDecorate) {
		super(asm5, toDecorate);
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc){
		if(opcode == 180){
			//get field
			if(ark.getBoat().get(this.className).methods
					.get(methodName).fieldInsn.containsKey(owner+"."+name)){
				HashSet<String> temp = ark.getBoat().get(this.className)
						.methods.get(methodName).fieldInsn.get(owner+"."+name);
				temp.add("get");
				ark.getBoat().get(this.className).methods.get(methodName)
					.fieldInsn.put(owner+"."+name, temp);
			} else {
				HashSet<String> temp = new HashSet<String>();
				temp.add("get");
				ark.getBoat().get(this.className).methods.get(methodName)
					.fieldInsn.put(owner+"."+name, temp);
			}
		} else if(opcode == 181){
			//set field
			if(ark.getBoat().get(this.className).methods
					.get(methodName).fieldInsn.containsKey(owner+"."+name)){
				HashSet<String> temp = ark.getBoat().get(this.className)
						.methods.get(methodName).fieldInsn.get(owner+"."+name);
				temp.add("set");
				ark.getBoat().get(this.className).methods.get(methodName)
					.fieldInsn.put(owner+"."+name, temp);
			} else {
				HashSet<String> temp = new HashSet<String>();
				temp.add("set");
				ark.getBoat().get(this.className).methods.get(methodName)
					.fieldInsn.put(owner+"."+name, temp);
			}
		}
	}

}

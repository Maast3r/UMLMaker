package src;


import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitorBuffered {
	public NoahsArk ark;
	public String className;
	
	public ClassFieldVisitor(int arg0, ClassVisitorBuffered arg1, NoahsArk ark, String className) {
		super(arg0, arg1);
		this.buf = arg1.buf;
		this.ark = ark;
		this.className = className;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, 
			Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		String pkg = "";
		String sig = "";
		String temp = "";
		
		if(signature != null && !signature.equals("")){
			// find if it is a type of list, and store it
			if(Type.getType(signature).getClassName().contains("<")){
				temp = Type.getType(signature).getClassName().split("<")[0];
			}
			if(Type.getType(signature).getClassName().contains(".")){
				String sign[] = Type.getType(signature).getClassName().split("\\.");
				sig = sign[sign.length-1];
			}
		}
		if(type.contains(".")){
			String[] temparray = type.split("\\.");
			type = temparray[temparray.length - 1];
			for(int i=0; i<temparray.length-1; i++){
				pkg += temparray[i] + ".";
			}
			if(!type.equals("")) {
				String typeClass = type;
				if(type.contains("[")) typeClass = typeClass.replace("[", "");
				if(type.contains("]")) typeClass = typeClass.replace("]", "");
				if(!this.ark.seenClass.containsKey(typeClass) && this.ark.seenClass.size() < this.ark.umlNodes){
					if(!typeClass.contains("$")){
						if(!pkg.equals("java.lang.")){
							this.ark.getNewList().put(typeClass, pkg);
							this.ark.seenClass.put(typeClass, pkg);
						}
					}
				}
			}
		}
		String symbol= getAccessModifier(access);
		
		this.ark.getBoat().get(this.className).addField(name, new FieldPrototype(symbol, name, type, sig, temp));
		
		return toDecorate;
	}
}

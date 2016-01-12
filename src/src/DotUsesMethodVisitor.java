package src;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import jdk.internal.org.objectweb.asm.Opcodes;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class DotUsesMethodVisitor extends ClassVisitorBuffered implements IMethodVisitor{
	public StringBuffer buf;
	public String owner;
	
	public DotUsesMethodVisitor(int arg0, StringBuffer buf, String owner) {
		super(arg0, buf);
		this.owner = owner;
	}
	public DotUsesMethodVisitor(int arg0, StringBuffer buf){
		super(arg0);
		this.buf = buf;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions){
		System.out.println("Visiting inside dot method        " + name);
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		System.out.println("a;sldjf;alsdjflasjdsja : " + exceptions);
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
			args += temp + ", ";
		}
		if(args.length()>=2)args = args.substring(0, args.length() -2);
		
		String symbol= getAccessModifier(access);
		String returnName = Type.getReturnType(desc).getClassName();
		if(returnName.contains(".")){
			String[] temparray = returnName.split("\\.");
			returnName = temparray[temparray.length - 1];
		}
		
		try {
			System.out.println("test:                           target." + owner + "      " + owner.length());
			Class test = Class.forName("target." + owner);
			System.out.println("after");
			try {
				System.out.println(test.getDeclaredMethods());
				Constructor[] c = test.getConstructors();
//				c[0].newInstanc
				Method[] ms = test.getDeclaredMethods();
				System.out.println("teststsetstset");
				for(int i=0; i<ms.length; i++){
					try {
						ms[i].setAccessible(true);
						System.out.println(ms[i].invoke(test.newInstance()).getClass().toString());
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(ms[i].getName().toString());
				}
				Method m = test.getDeclaredMethod(name, List.class);
				try {
					System.out.println(m.invoke(test.newInstance(), argTypes));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.buf.append(name.replace("<", "\\<").replace(">", "\\>") + ":"+ args + ", " + returnName + "\n");
		
		
		
		System.out.println("REPEATTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
//		System.out.println(name.replace("<", "\\<").replace(">", "\\>") + ":"+ args +", "+ returnName + "\n");
		
		
		return toDecorate;
	}
}

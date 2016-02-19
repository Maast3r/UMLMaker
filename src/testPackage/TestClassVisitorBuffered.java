package testPackage;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import src.ClassDeclarationVisitor;
import src.ClassFieldVisitor;
import src.ClassVisitorBuffered;
import src.FirstASM;
import src.NoahsArk;

public class TestClassVisitorBuffered {

	public static HashMap<String, String> listOfClasses; 
	@Test
	public void test() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		String path = "src\\testPackage";
		File packageToUML = new File(path);
		
		this.listOfClasses = FirstASM.listClasses(packageToUML,"");
		NoahsArk ark = new NoahsArk(listOfClasses);
		String pkg = "testPackage.";
		StringBuffer buf = new StringBuffer();
		ark.setCmd("uml");
		FirstASM.getClassDetails(pkg, "TestClassVisitorBuffered", ark);
//		ClassReader reader = new ClassReader(pkg + "TestClassVisitorBuffered");
//		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
//				Opcodes.ASM5, buf, ark);
//
//		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
//		
//		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
//				declVisitor, ark, declVisitor.getName());
//		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		FirstASM.listOfClasses = listOfClasses;
		buf = this.umlHandler(pkg, path, buf, ark);
		System.out.println(buf.toString());
		Assert.assertTrue(buf.toString().contains("TestClassVisitorBuffered"));
		
	}
	

	public StringBuffer umlHandler(String pkg, String path, StringBuffer buf, 
			NoahsArk ark) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		Iterator iter = this.listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
			FirstASM.getClassDetails(pkg, temp, ark);
			
		}
		
		return FirstASM.generateDotUML(pkg, buf, ark);
	}
}

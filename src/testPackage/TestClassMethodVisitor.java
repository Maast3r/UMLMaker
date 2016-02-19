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
import src.DotMethodVisitor;
import src.FirstASM;
import src.NoahsArk;

public class TestClassMethodVisitor {
	
	public static HashMap<String, String> listOfClasses; 	
	
	@Test
	public void test() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		String path = "src\\src";
		File packageToUML = new File(path);
		
		this.listOfClasses = FirstASM.listClasses(packageToUML,"");
		NoahsArk ark = new NoahsArk(listOfClasses);
		String pkg = "src.";
		StringBuffer expected = new StringBuffer();
		expected.append("+visit(int,int,String,String,String,String): void");
		
		StringBuffer buf = new StringBuffer();
		ark.setCmd("uml");
		FirstASM.getClassDetails(pkg, "ClassVisitorBuffered", ark);

		FirstASM.listOfClasses = listOfClasses;
		buf = this.umlHandler(pkg, path, buf, ark);
		System.out.println(buf.toString());
		
		Assert.assertTrue(buf.toString().contains(expected.toString()));
	
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

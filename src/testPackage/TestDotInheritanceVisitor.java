package testPackage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import src.ClassVisitorBuffered;
import src.FirstASM;
import src.NoahsArk;

public class TestDotInheritanceVisitor {
	static String path = "./src/src";
	static File packageToUML = new File(path);
	private static HashMap<String, String> listOfClasses = FirstASM.listClasses(packageToUML,"");
	@Test
	public void test() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		String type = "Inheritance";
		String expected = "";
		
		String path = "./src/src";
		String pkg = "src.";
		File packageToUML = new File(path);
		StringBuffer buf = new StringBuffer();
		HashMap<String, String> listOfClasses = FirstASM.listClasses(packageToUML,"");
		NoahsArk ark = new NoahsArk(listOfClasses);
		ArrayList<String> inheritancePairs = new ArrayList<String>();
		Iterator iter = listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
//			FirstASM.getClassDetails(pkg, temp, buf);
			inheritancePairs.addAll(FirstASM.getAssociation(pkg, temp, type, ark));
		}

		for (int i = 0; i < inheritancePairs.size(); i++) {
			buf.append(FirstASM.pairToViz(inheritancePairs.get(i), ark));
		}
		
//		System.out.println(buf.toString());
//		System.out.println(expected.toString());	
		
		Assert.assertEquals(buf.toString(), expected.toString());
	}

}

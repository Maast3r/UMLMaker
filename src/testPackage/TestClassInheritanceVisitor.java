package testPackage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import src.FirstASM;
import src.NoahsArk;

public class TestClassInheritanceVisitor {

	@Test
	public void test() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		String expected = "";
		
		String path = "./src/composite";
		String pkg = "composite.";
		File packageToUML = new File(path);
		StringBuffer buf = new StringBuffer();
		HashMap<String, String> listOfClasses = FirstASM.listClasses(packageToUML, pkg);
		System.out.println(listOfClasses.toString());
		NoahsArk ark = new NoahsArk(listOfClasses);
		ArrayList<String> inheritancePairs = new ArrayList<String>();
		Iterator iter = listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
//			FirstASM.getClassDetails(pkg, temp, buf);
			ArrayList<String> temptemp = FirstASM.getAssociation(pkg, temp, "inheritance", ark);
			System.out.println(temptemp.toString());
			inheritancePairs.addAll(FirstASM.getAssociation(pkg, temp, "inheritance", ark));
		}

		for (int i = 0; i < inheritancePairs.size(); i++) {
			buf.append(FirstASM.pairToViz(inheritancePairs.get(i), ark));
		}
		
		System.out.println(buf.toString());
//		System.out.println(expected.toString());	
		
		Assert.assertEquals(buf.toString(), expected.toString());
	}

}

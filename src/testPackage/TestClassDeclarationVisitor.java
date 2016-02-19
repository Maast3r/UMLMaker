package testPackage;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

import src.FirstASM;

public class TestClassDeclarationVisitor {

	@Test
	public void testTestPackage() throws IOException {
		String path = "./src/testPackage";
		String pkg = "";
		ArrayList<String> actualClasses = new ArrayList<String>();
		ArrayList<String> expectedClasses = new ArrayList<String>();

		expectedClasses.add("TestClassDeclarationVisitor");
		expectedClasses.add("TestClassFieldVisitor");
		expectedClasses.add("TestClassInheritanceVisitor");
		expectedClasses.add("TestClassMethodVisitor");
		expectedClasses.add("TestClassPrototype");
		expectedClasses.add("TestClassVisitorBuffered");

		File packageToUML = new File(path);
		HashMap<String, String> listOfClasses = FirstASM.listClasses(packageToUML, pkg);
		Iterator iter = listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
			actualClasses.add(temp);
		}

		for (String className : expectedClasses) {
			if (!actualClasses.contains(className)) {
				fail("Missing class name");
			}
		}

	}

}

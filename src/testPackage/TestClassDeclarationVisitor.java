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
	public void testTargetPackage() throws IOException {
		String path = "./src/target";
		ArrayList<String> actualClasses = new ArrayList<String>();
		ArrayList<String> expectedClasses = new ArrayList<String>();

		expectedClasses.add("FileBehavior");
		expectedClasses.add("Observer");
		expectedClasses.add("PyBehavior");
		expectedClasses.add("TxtBehavior");
		expectedClasses.add("AppLauncher");
		expectedClasses.add("HtmlBehavior");

		File packageToUML = new File(path);
		HashMap<String, Boolean> listOfClasses = FirstASM.listClasses(packageToUML);
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

	@Test
	public void testTestPackage() throws IOException {
		String path = "./src/testPackage";
		ArrayList<String> actualClasses = new ArrayList<String>();
		ArrayList<String> expectedClasses = new ArrayList<String>();

		expectedClasses.add("TestClass");
		expectedClasses.add("TestClassDeclarationVisitor");
		expectedClasses.add("TestClassFieldVisitor");
		expectedClasses.add("TestClassInheritanceVisitor");
		expectedClasses.add("TestClassMethodVisitor");
		expectedClasses.add("TestClassPrototype");
		expectedClasses.add("TestClassVisitorBuffered");
		expectedClasses.add("TestDesignParser");

		File packageToUML = new File(path);
		HashMap<String, Boolean> listOfClasses = FirstASM.listClasses(packageToUML);
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

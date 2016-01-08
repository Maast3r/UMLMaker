package testPackage;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;

import org.junit.Assert;
import org.junit.Test;

import src.FirstASM;

public class TestClassInheritanceVisitor {

	@Test
	public void test() throws IOException {
		String expected = "TxtBehavior -> FileBehavior[shape = onormal,style = dotted]\n"
				+ "TxtBehavior -> Observer[shape = onormal,style = dotted]\n"
				+ "TxtBehavior -> Object [shape = onormal]\n"
				+ "Observer -> Object [shape = onormal]\n"
				+ "HtmlBehavior -> FileBehavior[shape = onormal,style = dotted]\n"
				+ "HtmlBehavior -> Observer[shape = onormal,style = dotted]\n"
				+ "HtmlBehavior -> Object [shape = onormal]\n"
				+ "FileBehavior -> Observer[shape = onormal,style = dotted]\n"
				+ "FileBehavior -> Object [shape = onormal]\n"
				+ "AppLauncher -> Thread [shape = onormal]\n"
				+ "PyBehavior -> FileBehavior[shape = onormal,style = dotted]\n"
				+ "PyBehavior -> Observer[shape = onormal,style = dotted]\n"
				+ "PyBehavior -> Object [shape = onormal]\n";
		
		String path = "./src/target";
		String pkg = "target.";
		File packageToUML = new File(path);
		StringBuffer buf = new StringBuffer();
		HashMap<String, Boolean> listOfClasses = FirstASM.listClasses(packageToUML);
		ArrayList<String> inheritancePairs = new ArrayList<String>();
		Iterator iter = listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
//			FirstASM.getClassDetails(pkg, temp, buf);
			inheritancePairs.addAll(FirstASM.getInheritance(pkg, temp));
		}

		for (int i = 0; i < inheritancePairs.size(); i++) {
			buf.append(FirstASM.pairToViz(inheritancePairs.get(i)));
		}
		
//		System.out.println(buf.toString());
//		System.out.println(expected.toString());	
		
		Assert.assertEquals(buf.toString(), expected.toString());
	}

}

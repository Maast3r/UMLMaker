package testPackage;


import java.io.File;
import java.io.IOException;
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

	public static HashMap<String, Boolean> listOfClasses; 
	@Test
	public void test() throws IOException {
		String path = "src\\testPackage";
		File packageToUML = new File(path);
		
		this.listOfClasses = listClasses(packageToUML);
		NoahsArk ark = new NoahsArk(listOfClasses);
		String pkg = "testPackage.";
		StringBuffer buf = new StringBuffer();
		ClassReader reader = new ClassReader(pkg + "TestClass");
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf, ark);

		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor, ark, declVisitor.getName());
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		FirstASM.listOfClasses = listOfClasses;
		buf = this.umlHandler(pkg, path, buf, ark);
		System.out.println(buf.toString());
		Assert.assertTrue(buf.toString().contains("-nathanHatesFields"));
		
	}
	
	public HashMap<String, Boolean> listClasses(final File folder) {
		HashMap<String, Boolean> listOfJavaFiles = new HashMap<String, Boolean>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().contains(".")) {
				String ext = fileEntry.getName().split(Pattern.quote("."))[1];
				if (ext.equals("java")) {
					listOfJavaFiles.put(
							fileEntry.getName().split(Pattern.quote("."))[0],
							true); 
				}
			}
		}
		return listOfJavaFiles;
	}

	public StringBuffer umlHandler(String pkg, String path, StringBuffer buf, 
			NoahsArk ark) throws IOException{
		Iterator iter = this.listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
			FirstASM.getClassDetails(pkg, temp, ark);
			
		}
		
		return FirstASM.generateDotUML(pkg, buf, ark);
	}
}

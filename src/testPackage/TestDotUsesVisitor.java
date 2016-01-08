package testPackage;

import static org.junit.Assert.*;

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

public class TestDotUsesVisitor {
	static String path = "./src/target";
	static File packageToUML = new File(path);
	private static HashMap<String, Boolean> listOfClasses = FirstASM.listClasses(packageToUML);
	@Test
	public void test() throws IOException {
		String[] associationTypes = {"Uses"};
		String expected = "TxtLauncher -> AppLauncher[arrowhead = vee, style = dotted]\n"
				+ "PdfLauncher -> AppLauncher[arrowhead = vee, style = dotted]\n"
				+ "FileLauncher -> AppLauncher[arrowhead = vee, style = dotted]\n"
				+ "AppLauncher -> FileLauncher[arrowhead = vee, style = dotted]\n"
				+ "Subject -> FileLauncher[arrowhead = vee, style = dotted]\n"
				+ "HtmlLauncher -> AppLauncher[arrowhead = vee, style = dotted]\n";
		
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
			for(String type : associationTypes){
				inheritancePairs.addAll(getAssociation(pkg, temp, type));
			}
		}

		for (int i = 0; i < inheritancePairs.size(); i++) {
			buf.append(FirstASM.pairToViz(inheritancePairs.get(i)));
		}
		
//		System.out.println(buf.toString());
//		System.out.println(expected.toString());	
		
		Assert.assertEquals(buf.toString(), expected.toString());
	}
	
	public static ArrayList<String> getAssociation(String pkg, String className, String association)
			throws IOException {
			ClassReader reader = new ClassReader(pkg + className);
			ArrayList<String> result = new ArrayList<String>();
			StringBuffer buf = new StringBuffer();
			association = association.toLowerCase();
	    	try {
	    		Class[] cArg = new Class[2];
	    		cArg[0] = int.class;
	    		cArg[1] = StringBuffer.class;
				Constructor assocVisitor = Class.forName("src.Dot"
						+  association.substring(0, 1).toUpperCase() + association.substring(1) 
						+ "Visitor").getConstructor(cArg);
						
				ClassVisitorBuffered test = (ClassVisitorBuffered) assocVisitor.newInstance(Opcodes.ASM5, buf);
				reader.accept(test, ClassReader.EXPAND_FRAMES);
	    	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Dot visitor association class doesn't exist\n");
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	result = dotUsesHandler(className, buf);
	    	
	    	return result;
		}
	
	public static ArrayList<String> dotUsesHandler(String className, StringBuffer buf){
//		System.out.println("This is getting called");
		ArrayList<String> result = new ArrayList<String>();
		String name = className;
		
		String[] lines = buf.toString().split("\n");
//		System.out.println("Name: " + name);
		for(String line: lines){
			
			String argStuff = line.split(":")[1];
		//		String implementStuff = argStuff.split("#")[1].replace("[","").replace("]","");
			String[] args= argStuff.split(", ");
			if(name.contains("/")){
				int len = name.split("/").length;
				name = name.split("/")[len -1 ];
			}
			// TODO: Implement hash map of class stuff
		//		System.out.println(listOfClasses.toString());
			for(String s : args){
//				System.out.println("Arg:      " + s);
				if(s.equals("")){
					continue;
				}
//				System.out.println("Map result for " + s + "# " + listOfClasses.get(s));
				if(s.contains("/")){
					int len = s.split("/").length;
					s = s.split("/")[len -1 ];
				}
				if(listOfClasses.get(s) != null){
//					System.out.println("YAY " + s );
					if(!s.equals("")) result.add(name + "#" + s);
				}
			}
		}
		
		
		return result;
	}
}

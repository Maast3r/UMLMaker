package src;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class FirstASM {
	public static String myField = "Hello World!";
	public static void main(String[] args) throws IOException{
		ClassReader reader = new ClassReader("testPackage.TestClass");
		
		HashMap<String, Boolean> classNames = new HashMap<String, Boolean>();
		StringBuffer buf = new StringBuffer();
		
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, buf);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
		
		ClassVisitorBuffered methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		System.out.println(buf.toString());

		
//		ClassVisitor visitor = new TraceClassVisitor(
//				new PrintWriter(System.out));
//		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		
		File packageToUML = new File("./src/testPackage");
		HashMap<String, Boolean> listOfClasses = listClaases(packageToUML);
		Iterator iter = listOfClasses.entrySet().iterator();
		while(iter.hasNext()){
			System.out.println("why: " + iter.next().toString());
			iter.remove();
		}
		System.out.println("test " + listOfClasses.toString());
	}
	
	public static HashMap<String, Boolean> listClaases(final File folder){
		HashMap<String, Boolean> listOfJavaFiles = new HashMap<String, Boolean>();
		for (final File fileEntry : folder.listFiles()){
			if(fileEntry.getName().contains(".")){
				String ext = fileEntry.getName().split(Pattern.quote("."))[1];
				if(ext.equals("java")){
					listOfJavaFiles.put(fileEntry.getName().split(Pattern.quote("."))[0], false);
				}
			}
		}
		return listOfJavaFiles;
	}
}

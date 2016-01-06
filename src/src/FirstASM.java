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
		
//		ClassVisitor visitor = new TraceClassVisitor(
//				new PrintWriter(System.out));
//		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		StringBuffer buf = new StringBuffer();
		buf.append("digraph G{\n" +
				"fontname = \"Bitstream Vera Sans\"\n"+
        "fontsize = 8\n" +
        "node [\n"+
        "        fontname = \"Bitstream Vera Sans\"\n"+
        "        fontsize = 8\n"+
        "        shape = \"record\"\n"+
        "]\n"+
        "edge [\n"+
        "        fontname = \"Bitstream Vera Sans\"\n"+
        "        fontsize = 8\n"+
        "]\n");
		File packageToUML = new File("./src/src");
		HashMap<String, Boolean> listOfClasses = listClaases(packageToUML);
		Iterator iter = listOfClasses.entrySet().iterator();
		while(iter.hasNext()){
			getClassDetails(iter.next().toString().split("=")[0], buf);
		}
		buf.append("}");
		System.out.println(buf.toString());
	}
	
	public static void getClassDetails(String className, StringBuffer buf) throws IOException{
		ClassReader reader = new ClassReader("src." + className);
		
		
		
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, buf);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		buf.append(" | ");
		
		ClassVisitorBuffered methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, buf);
		
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\"]");
		buf.append("\n\n");
		
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

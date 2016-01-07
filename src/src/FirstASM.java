package src;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class FirstASM {
	public static String myField = "Hello World!";
	public static void main(String[] args) throws IOException{
		//////////////////////////////////////////////////////////
		// Set these two variables to generate UML for an arbitrary project
		String path = "./src/src";
		String pkg = "src.";
		//////////////////////////////////////////////////////////
	
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
		
		File packageToUML = new File(path);
		HashMap<String, Boolean> listOfClasses = listClasses(packageToUML);
		ArrayList<String> inheritancePairs = new ArrayList<String>();
		Iterator iter = listOfClasses.entrySet().iterator();
		while(iter.hasNext()){
			String temp = iter.next().toString().split("=")[0];
			getClassDetails(pkg, temp, buf);
			getInheritance(pkg, temp);
		}
		
		for(int i = 0; i < inheritancePairs.size(); i++){
			
			
		}
		
		buf.append("}");
		
		// Write the buffer to file
		if(path.contains("/")){
			path = path.split("/")[path.split("/").length - 1];
		}
		path = path+".dot";
		System.out.println(path);
		FileOutputStream base = new FileOutputStream(path);
		System.out.println("blah");
		base.write(buf.toString().getBytes());
		System.out.println("blah");
		base.close();
	}
	
	public static void getClassDetails(String pkg, String className, StringBuffer buf) throws IOException{
		ClassReader reader = new ClassReader(pkg + className);

		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, buf);
		
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		buf.append(" | ");
		
		ClassVisitorBuffered methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, buf);
		
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\"]");
		buf.append("\n\n");
		
	}
	public static ArrayList<String> getInheritance(String pkg, String className) throws IOException{
		ClassReader reader = new ClassReader(pkg + className);
		ArrayList<String> result = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		ClassVisitorBuffered inheritVisitor = new ClassInheritanceVisitor(Opcodes.ASM5, buf);
		reader.accept(inheritVisitor, ClassReader.EXPAND_FRAMES);
		System.out.println(buf.toString());
		buf.toString().split(":");
		
		return null;
	}
	public String pairToViz(String pair){
		String edgeDefinition = 
		if(pair.contains("!")){
			pair.split(arg0)
		}
		
		return pair;
		
	}
	
	public static HashMap<String, Boolean> listClasses(final File folder){
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

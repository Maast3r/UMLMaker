package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class FirstASM {
	private static String font = "\tfontname = \"Comic Sans\"\n"
								+"\tfontsize = 16\n";
	private static String[] associationTypes = {"Inheritance", "Uses", "Association"};//,"Association"};
	private static HashMap<String, Boolean> listOfClasses;
	public static void main(String[] args) throws IOException {
		// ////////////////////////////////////////////////////////
		// Set these two variables to generate UML for an arbitrary project
		String path = "./src/target";
		String pkg = "target.";
		// ////////////////////////////////////////////////////////
		
		StringBuffer buf = new StringBuffer();
		buf.append("digraph G{\n" + font + "\n"
				+ "node [\n" 
				+ font + "\n"
				+ "        shape = \"record\"\n" + "]\n" + "edge [\n"
				+ font + "]\n");

		File packageToUML = new File(path);
		System.out.println(packageToUML.getAbsolutePath());
		listOfClasses = listClasses(packageToUML);
		ArrayList<String> inheritancePairs = new ArrayList<String>();
		Iterator iter = listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
			getClassDetails(pkg, temp, buf);
			for(String type : associationTypes){
				inheritancePairs.addAll(getAssociation(pkg, temp, type));
			}
		}

		for (int i = 0; i < inheritancePairs.size(); i++) {
			buf.append(pairToViz(inheritancePairs.get(i)));
		}

		buf.append("}");

		// Write the buffer to file
		if (path.contains("/")) {
			path = path.split("/")[path.split("/").length - 1];
		}
		path = path + ".dot";
		FileOutputStream base = new FileOutputStream(path);
		base.write(buf.toString().getBytes());
		base.close();
		
		System.out.println("trying to run program");
		visualize(pkg);
	}

	public static void getClassDetails(String pkg, String className,
			StringBuffer buf) throws IOException {
		
		ClassReader reader = new ClassReader(pkg + className);

		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, buf);

		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		buf.append(" | ");

		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
				Opcodes.ASM5, buf);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		buf.append("}\"]");
		buf.append("\n\n");

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
    	
    	if(association.equals("association")){
    		result = dotAssociationHandler(className, buf);
    	} else if(association.equals("inheritance")){
    		result = dotInheritanceHandler(buf);
    	} else if(association.equals("uses")){
    		result = dotUsesHandler(className, buf);
    	}
    	
    	return result;
	}
	public static ArrayList<String> dotInheritanceHandler(StringBuffer buf){
		ArrayList<String> result = new ArrayList<String>();
		String name = buf.toString().split(":")[0];
		String extendStuff = buf.toString().split(":")[1];
		String extendName = extendStuff.split("#")[0];
		String implementStuff = extendStuff.split("#")[1].replace("[","").replace("]","");
		String[] implementing = implementStuff.split(",");
		if(name.contains("/")){
			int len = name.split("/").length;
			name = name.split("/")[len -1 ];
		}
		for(String s : implementing){
			if(s.contains("/")){
				int len = s.split("/").length;
				s = s.split("/")[len -1 ];
			}
			if(listOfClasses.get(s) != null){
				if(!s.equals(""))result.add(name + "@" + s);
		
			}
		}
		if(extendName.contains("/")){
			int len = extendName.split("/").length;
			extendName = extendName.split("/")[len -1 ];
		}
		if(listOfClasses.get(extendName) != null){
			result.add(name + "!" + extendName);
		}
		return result;
		
	}
	
	public static ArrayList<String> dotAssociationHandler(String className, StringBuffer buf){
		ArrayList<String> result = new ArrayList<String>();
		String name = className;
		String[] lines = buf.toString().split("\n");
		
		
		if(name.contains("/")){
			int len = name.split("/").length;
			name = name.split("/")[len -1 ];
		}
		
		for(String s : lines){
//		System.out.println(name + ":" + s);
			if(s.contains("/")){
				int len = s.split("/").length;
				s = s.split("/")[len -1 ];
			}
			if(listOfClasses.get(s) != null){
				if(!s.equals(""))result.add(name + "$" + s);
		
			}
		}
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
	
	public static String pairToViz(String pair){
		//String edgeDefinition = "edge [\n" + font;
		String result = "";
		// extends
		if(pair.equals("")) return result;
		if(pair.contains("!"))result = pair.split("!")[0] + " -> " + pair.split("!")[1] + " [arrowhead = onormal]";
		//implements
		if(pair.contains("@"))result = pair.split("@")[0] + " -> " + pair.split("@")[1] + "[arrowhead = onormal,style = dotted]";
		// Uses
		if(pair.contains("#"))result = pair.split("#")[0] + " -> " + pair.split("#")[1] + "[arrowhead = vee, style = dotted]";
		if(pair.contains("$"))result = pair.split("\\$")[0] + " -> " + pair.split("\\$")[1] + "[arrowhead = vee]";
		result = result + "\n";
		return result;
		
	}

	public static HashMap<String, Boolean> listClasses(final File folder) {
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
	
	public static void visualize(String pkg){
		Runtime rt = Runtime.getRuntime();
		StringBuffer sb = new StringBuffer();
		try {
			pkg = pkg.substring(0,pkg.length()-1);
			System.out.println(pkg);
			Process pr = rt.exec("dot -T png -o "+pkg+".png " + pkg+".dot");
			pr.waitFor();
			BufferedReader reader = 
			         new BufferedReader(new InputStreamReader(pr.getInputStream()));

			    String line = "";			
			    while ((line = reader.readLine())!= null) {
				sb.append(line + "\n");
			    }
			    System.out.println(sb.toString());
		} catch (IOException e) {
			System.out.println("Could not visualize!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			System.out.println("Could not visualize!");
		}
	}
}

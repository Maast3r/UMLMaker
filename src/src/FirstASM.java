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
	private static String font = "\tfontname = \"Times New Roman\"\n"
								+"\tfontsize = 16\n";
	private static String[] associationTypes = {"Inheritance", "Association"};//,"Association"};
	
	private static String methodSeparatorString = " | ";
	private static String classEndString = "}\"]\n";
	private static String pizzaf = "C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\pizzafactory\\factory\\pizzaaf";
	private static String testerino = "C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\pizzaf";
	
	private static HashMap<String, Boolean> listOfClasses;
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		System.out.print("UMLMaker>");
		line = in.readLine();
		if(line == null || line.length() == 0 || !line.contains(" ")) throw new IOException("FORMAT ERROR: Empty command is not supported!");
		String command = line.split(" ")[0];
		String path2 = line.split(" ")[1];
		if(!path2.contains("\\\\"))throw new IOException("FORMAT ERROR: Empty command is not supported!");
		String[] path2Parts = path2.split("\\\\");
		String pkg2 = path2Parts[path2Parts.length-1] + ".";
		path2 = path2.replace("\\\\", "\\");
		// ////////////////////////////////////////////////////////
		// Set these two variables to generate UML for an arbitrary project
//		String path = "./src/target";
//		String pkg = "target.";
		String path = path2;
		String pkg = pkg2;
		// ////////////////////////////////////////////////////////
		
		StringBuffer buf = new StringBuffer();
		File packageToUML = new File(path);
		System.out.println(packageToUML.getAbsolutePath());
		
		
		// Generate the ark
		listOfClasses = listClasses(packageToUML);
		NoahsArk ark = new NoahsArk(listOfClasses);
		Iterator iter = listOfClasses.entrySet().iterator();
		while (iter.hasNext()) {
			String temp = iter.next().toString().split("=")[0];
			getClassDetails(pkg, temp, ark);
			
		}

		
		//Decide which image to generate
		if(command.equals("uml")){
			buf = generateDotUML(pkg, buf, ark);
		} else if(command.equals("sequence")){
			System.out.println("make a sequence diagram");
		} else {
			System.out.println("THIS COMMMANDS IS NOT SUPPORTED");
		}
//		buf = generateDotUML(pkg, buf, ark);
		
		
		// Write the buffer to file
		if (path.contains("/")) {
			path = path.split("/")[path.split("/").length - 1];
		}
		path = path + ".dot";
//		path = path + "\\.\\" + pkg + "dot";
//		path = path.replace("\\\\", "\\");
//		System.out.println("path : " + path);
		FileOutputStream base = new FileOutputStream(path);
		base.write(buf.toString().getBytes());
		base.close();
		
		System.out.println("trying to run program");
		visualize(command, pkg);
	}

	public static void getClassDetails(String pkg, String className,
			NoahsArk ark) throws IOException {
		ClassReader reader = new ClassReader(pkg + className);
		
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(
				Opcodes.ASM5, ark);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				declVisitor, ark, declVisitor.getName());
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);

		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(
				Opcodes.ASM5, fieldVisitor, ark, declVisitor.getName());

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		
		ArrayList<String> inheritancePairs = new ArrayList<String>();
	 
		
		for(String type : associationTypes){
			inheritancePairs.addAll(getAssociation(pkg, className, type));
		}
		for (int i = 0; i < inheritancePairs.size(); i++) {
			if(inheritancePairs.get(i).contains("!"))ark.addPair(inheritancePairs.get(i).split("!")[0],"!" +
				inheritancePairs.get(i).split("!")[1]);
			if(inheritancePairs.get(i).contains("@"))ark.addPair(inheritancePairs.get(i).split("@")[0], "@" +
				inheritancePairs.get(i).split("@")[1]);
			if(inheritancePairs.get(i).contains("#"))ark.addPair(inheritancePairs.get(i).split("#")[0],"#"+
				inheritancePairs.get(i).split("#")[1]);
			if(inheritancePairs.get(i).contains("$"))ark.addPair(inheritancePairs.get(i).split("\\$")[0],"$"+
				inheritancePairs.get(i).split("\\$")[1]);
		}
		
	}
	
	public static StringBuffer generateDotUML(String pkg, StringBuffer buf, NoahsArk ark) throws IOException {
	 	// Generate a dot file, stored in the Stringbuffer buf
		// Setup header of dot file
		buf.append("digraph G{\n" + font + "\n"
				+ "node [\n" 
				+ font + "\n"
				+ "        shape = \"record\"\n" + "]\n" + "edge [\n"
				+ font + "]\n");

		// Do the real work
		String result = "";
		HashMap<String, ClassPrototype> boat = ark.getBoat();
		for(ClassPrototype c : boat.values()){
			String className = c.getName();	
			result += c.prepareUML();
			Iterator fIterator = c.getFields().keySet().iterator();
			while(fIterator.hasNext()){
				result += c.getFields().get(fIterator.next()).prepareUML();
			}
			
			result+= methodSeparatorString;
			Iterator mIterator = c.getMethods().keySet().iterator();
			while(mIterator.hasNext()){
				result += c.getMethods().get(mIterator.next()).prepareUML();
			}
			result+=classEndString;
		}
		buf.append(result);
		for(String origin: ark.pairs.keySet() ){
			for(String target : ark.pairs.get(origin)){
				// Check for inheritance removals
				for(String checkTarget: ark.pairs.get(origin)){
					// compare target and see if target inherits from checkTarget 
					String tempTarget = target.substring(1);
					String tempCheckTarget = target.substring(1);
					if(target.charAt(0) == '$'){
//						if((temp)){
//							
//						}
					}
				}
				
				
				buf.append(pairToViz(origin + target));
			}
		}
		
		// formatting end of document
		buf.append("\n\n");
		buf.append("}");
		return buf;
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
		ArrayList<String> result = new ArrayList<String>();
		String name = className;
		
		String[] lines = buf.toString().split("\n");
		for(String line: lines){
			
			String argStuff = line.split(":")[1];
		//		String implementStuff = argStuff.split("#")[1].replace("[","").replace("]","");
			String[] args= argStuff.split(", ");
			if(name.contains("/")){
				int len = name.split("/").length;
				name = name.split("/")[len -1 ];
			}
			// TODO: Implement hash map of class stuff
			for(String s : args){
				if(s.equals("")){
					continue;
				}
				if(s.contains("/")){
					int len = s.split("/").length;
					s = s.split("/")[len -1 ];
				}
				if(listOfClasses.get(s) != null){
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
	
	public static void visualize(String command, String path){
		System.out.println("visualize " + path + "png");
		Runtime rt = Runtime.getRuntime();
		StringBuffer sb = new StringBuffer();
		try {
			path = path.substring(0,path.length()-1);
			Process pr = null;;
			if(command.equals("uml")){
				pr = rt.exec("dot -T png -o "+path+".png " + path+".dot");
			} else if(command.equals("sequence")){
				System.out.println("sequence");
			} else {
				System.out.println("Not a valid command!");
			}
			pr.waitFor();
			BufferedReader reader = 
			         new BufferedReader(new InputStreamReader(pr.getInputStream()));

			    String line = "";			
			    while ((line = reader.readLine())!= null) {
				sb.append(line + "\n");
			    }
			    System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not visualize!");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Could not visualize!");
		}
	}
}

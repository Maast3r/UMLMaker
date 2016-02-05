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
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class FirstASM {
	private static String font = "\tfontname = \"Bitstream Vera Sans\"\n" + "\tfontsize = 16\n";
	private static String[] associationTypes = { "Inheritance", "Association", "Uses" };

	private static String methodSeparatorString = " | ";
	private static String classEndString1 = "}\"";
	private static String classEndString2 = "]\n";
	private static String ourPK = "uml C:\\Users\\Maast3r\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\src";
	private static String ourPKG = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\src";
	private static String single = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\singletons";
	private static String lab2one = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab2one";
	private static String lab5one = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab5one";
	private static String comp = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\composite";
	private static String testerino = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\pizzaf";
	private static String testerino2 = "sequence C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\lab22 DataLine take char[] 5";
	private static String testerino3 = "sequence java.util Collections shuffle List 5";
	private static String t = "sequence C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\src FirstASM sequenceHandler String,String,String,StringBuffer,NoahsArk,String,String,String,int 2";
	private static boolean isJava = false;
	public static HashMap<String, String> listOfClasses;
	public static ArrayList<String> temps = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		System.out.print("UMLMaker>");
//		line = in.readLine();
		line  = comp;
		if (line == null || line.length() == 0 || !line.contains(" "))
			throw new IOException("FORMAT ERROR: Empty command is not supported!");
		String command = line.split(" ")[0];
		String path = line.split(" ")[1];
		// if(!path.contains("\\\\"))throw new IOException("FORMAT ERROR: Empty
		// command is not supported!");
		String[] pathParts = path.split("\\\\");
		String pkg = pathParts[pathParts.length - 1] + ".";
		path = path.replace("\\\\", "\\");
		String inputClass = "";
		String inputMethod = "";
		String inputArgs = "";
		String maxDepth = "";

		StringBuffer buf = new StringBuffer();
		

		if (command.equals("uml")) {
			if(pkg.contains(".")){
				if(pkg.split("\\.")[0].equals("java") || pkg.split("\\.")[0].equals("org")){
					inputClass = line.split(" ")[2];
					listOfClasses = new HashMap<String, String>();
					listOfClasses.put(inputClass, pkg);
					isJava = true;
				} else {
					File packageToUML = new File(path);
					listOfClasses = listClasses(packageToUML, pkg);
				}
			}
			NoahsArk ark = new NoahsArk(listOfClasses);
			for(String s : temps){
				ark.seenClass.put(s, pkg);
			}
			ark.setPackage(pkg);
			ark.setCmd(command);
			umlHandler(command, pkg, path, buf, ark);
		} else if (command.equals("sequence")) {
			listOfClasses = null;
			NoahsArk ark = new NoahsArk(listOfClasses);
			ark.setPackage(pkg);
			ark.setCmd(command);
			inputClass = line.split(" ")[2];
			inputMethod = line.split(" ")[3];
			inputArgs = line.split(" ")[4];
			maxDepth = line.split(" ")[5];
			sequenceHandler(command, pkg, path, buf, ark, inputClass, inputMethod, inputArgs,
					Integer.parseInt(maxDepth));
		} else {
			System.out.println("THIS COMMMAND IS NOT SUPPORTED");
		}
	}

	public static void umlHandler(String command, String pkg, String path, StringBuffer buf, NoahsArk ark)
			throws IOException {
		while(ark.getListOfClass().size() > 0){
			for(String key : ark.getListOfClass().keySet()){
				getClassDetails(ark.getListOfClass().get(key), key, ark);
			}
			ark.setListOfClasses(ark.getNewList());
			ark.resetNewList();
		}
		getArrows(ark);
		buf = generateDotUML(pkg, buf, ark);

		// Write the buffer to file
		if (path.contains("/")) {
			path = path.split("/")[path.split("/").length - 1];
		}
		if(path.contains(".")) path = path.replace(".", "t");
		path = path + ".dot";
		FileOutputStream output = new FileOutputStream(path);
		output.write(buf.toString().getBytes());
		output.close();

		System.out.println("trying to run program");
		visualize(command, pkg);
	}

	public static void sequenceHandler(String command, String pkg, String path, StringBuffer buf, NoahsArk ark,
			String inputClass, String inputMethod, String inputArgs, int maxDepth) throws IOException {
		ArrayList<String> t = new ArrayList<String>();
		ark.setDepthMax(maxDepth);
		ark.newNodes.add(inputClass + "#" + inputClass);
		ark.setRoot(new CallNode("ROOT", true));
		ark.setActiveNode(ark.graphRoot);
		ark.setDepthMax(maxDepth);
		methodEval(pkg, inputClass, inputMethod, inputArgs, ark);
		buf = generateSequence(pkg, inputClass, inputMethod, inputArgs, buf, ark);

		// Write the buffer to file
		if (path.contains("/")) {
			path = path.split("/")[path.split("/").length - 1];
		}
		path = path + ".sd";
		FileOutputStream output = new FileOutputStream(path);
		output.write(buf.toString().getBytes());
		output.close();

		System.out.println("trying to run program sequence");
		// visualize(command, pkg);
	}

	public static void methodEval(String pkg, String inputClass, String inputMethod, String inputArgs, NoahsArk ark)
			throws IOException {
		ark.getBoat().put(inputClass, new ClassPrototype(inputClass));
		ClassReader reader = new ClassReader(pkg + inputClass);
		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(Opcodes.ASM5, ark, inputClass, inputMethod,
				inputArgs);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	}

	public static void getClassDetails(String pkg, String className, NoahsArk ark) throws IOException {
		ClassReader reader = new ClassReader(pkg + className);

		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, ark);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, ark,
				declVisitor.getName());
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);

		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(Opcodes.ASM5, fieldVisitor, ark,
				declVisitor.getName());

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	}
	
	public static void getArrows(NoahsArk ark) throws IOException{
		for(String key : ark.seenClass.keySet()){
			ArrayList<String> inheritancePairs = new ArrayList<String>();
			for (String type : associationTypes) {
				inheritancePairs.addAll(getAssociation(ark.seenClass.get(key), key, type, ark));
			}
			for (int i = 0; i < inheritancePairs.size(); i++) {
				if (inheritancePairs.get(i).contains("!"))
					ark.addPair(inheritancePairs.get(i).split("!")[0], "!" + inheritancePairs.get(i).split("!")[1]);
				if (inheritancePairs.get(i).contains("@"))
					ark.addPair(inheritancePairs.get(i).split("@")[0], "@" + inheritancePairs.get(i).split("@")[1]);
				if (inheritancePairs.get(i).contains("#"))
					ark.addPair(inheritancePairs.get(i).split("#")[0], "#" + inheritancePairs.get(i).split("#")[1]);
				if (inheritancePairs.get(i).contains("$"))
					ark.addPair(inheritancePairs.get(i).split("\\$")[0], "$" + inheritancePairs.get(i).split("\\$")[1]);
			}
		}
	}

	public static StringBuffer generateDotUML(String pkg, StringBuffer buf, NoahsArk ark) throws IOException {
		buf.append("digraph G{\n" + font + "\n" + "node [\n" + font + "\n" + 
				" shape = \"record\"\n" + "]\n"
				+ "edge [\n" + font + "]\n");

		// Do the real work
		String result = "";
		HashMap<String, ClassPrototype> boat = ark.getBoat();
		
		for (ClassPrototype c : boat.values()) {
			result = "";
			String cName = c.getName();
			Iterator fIterator = c.getFields().keySet().iterator();
			FieldPrototype field;
			while (fIterator.hasNext()) {
				field = c.getFields().get(fIterator.next());
				result += field.prepareUML();
			}

			result += methodSeparatorString;
			Iterator mIterator = c.getMethods().keySet().iterator();
			MethodPrototype method;
			while (mIterator.hasNext()) {
				method = c.getMethods().get(mIterator.next());
				result += method.prepareUML();
			}	
			
			result += classEndString1;

			result += new ColorDecorator(new TypeDetector(cName, ark)).getColor();
			result += new ColorDecorator(new TypeDetector(cName, ark)).getFillColor();
			result = c.prepareUML() + new NameDecorator(new TypeDetector(cName, ark)).getType().toString().replace("]", "")
																										.replace(",","")
																										.replace("[","" )
					+ "|" + result;
			result += classEndString2;
			buf.append(result);
		}

		for (String origin : ark.pairs.keySet()) {
			for (String target : ark.pairs.get(origin)) {
				buf.append(pairToViz(origin + target, ark));
			}
		}

		// formatting end of document
		for (int i = 0; i < 2; i++) {
			buf.append("\n");
		}
		buf.append("}");
		return buf;
	}

	public static StringBuffer generateSequence(String pkg, String inputClass, String inputMethod, String inputArgs,
			StringBuffer buf, NoahsArk ark) {
		String nl = "\n";
		HashSet<String> seen = new HashSet<String>();
		ArrayList<String> checked = new ArrayList<String>();
		int length = ark.newNodes.size();

		for (String node : ark.newNodes) {
			String temp[] = node.split("#");
			if (node.contains("$")) {
				temp[0] = node.replace("$", "#").split("#")[1];
				temp[1] = node.replace("$", "#").split("#")[2];
				node = "/" + temp[0] + "#" + temp[1];
			}

			if (seen.contains(temp[1])) {
			} else {
				if (length == ark.newNodes.size()) {
					seen.add(temp[1]);
					buf.append(temp[1] + ":" + temp[1] + nl);
				} else {
					seen.add(temp[1]);
					if (ark.constructedNodes.contains(node)) {
						buf.append("/" + temp[1] + ":" + temp[1] + nl);
						checked.add(temp[1]);
					} else {

						buf.append(temp[1] + ":" + temp[1] + nl);
					}
				}
				length--;
			}
		}
		buf.append(nl);
		seen = new HashSet<String>();
		// Add nonConstructed nodes to the seen
		for (String node : ark.newNodes) {
			if (!checked.contains(node.split("#")[1])) {
				seen.add(node.split("#")[1]);
			}
		}

		int currentLevel = 0;
		CallNode callNode = ark.getActiveNode();
		while (!ark.sequenceNodes.isEmpty()) {
			String temp = ark.sequenceNodes.get(0);
			ark.sequenceNodes.remove(0);

			if (temp.equals("GO DEEPER")) {
				// callNode = new CallNode(temp.split("#")[1], true);'
				try {
					callNode = callNode.getChildren().get(callNode.getChildren().size() - 1);
				} catch (Exception e) {

				}
				callNode.parent = ark.getActiveNode();
				ark.setActiveNode(callNode);
				currentLevel++;
			} else if (temp.equals("GO UPPER")) {
				callNode = ark.getActiveNode().getParent();
				ark.setActiveNode(callNode);

				currentLevel--;
			} else {
				// Creating a new object
				if (temp.contains("#")) {
					if (seen.contains(temp.split("#")[1])) {
						// init
						temp = (temp.split("#")[0].substring(1) + ":" + temp.split("#")[1] + ".init");
					} else {
						// new
						seen.add(temp.split("#")[1]);
						temp = (temp.split("#")[0].substring(1) + ":" + temp.split("#")[1] + ".new");
					}
					callNode = new CallNode(temp, true);
					callNode.setParent(ark.getActiveNode());
				} else {
					callNode = new CallNode(temp, false);
					callNode.setParent(ark.getActiveNode());
				}
				ark.getActiveNode().addChild(callNode);
				buf.append(temp + nl);
			}
		}
		// ark.graphRoot.printCallTree(0);
		return buf;
	}

	public static ArrayList<String> getAssociation(String pkg, String className, String association, NoahsArk ark)
			throws IOException {
		ClassReader reader = new ClassReader(pkg + className);
		ArrayList<String> result = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		association = association.toLowerCase();
		try {
			Class[] cArg = new Class[2];
			cArg[0] = int.class;
			cArg[1] = StringBuffer.class;
			Constructor assocVisitor = Class.forName(
					"src.Dot" + association.substring(0, 1).toUpperCase() + association.substring(1) + "Visitor")
					.getConstructor(cArg);

			ClassVisitorBuffered test = (ClassVisitorBuffered) assocVisitor.newInstance(Opcodes.ASM5, buf);
			test.ark = ark;
			test.className = className;
			reader.accept(test, ClassReader.EXPAND_FRAMES);
		} catch (ClassNotFoundException e) {
			System.out.println("Dot visitor association class doesn't exist\n");
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		if (association.equals("association")) {
			result = dotAssociationHandler(className, buf, ark);
		} else if (association.equals("inheritance")) {
			result = dotInheritanceHandler(buf, ark);
		} else if (association.equals("uses")) {
			result = dotUsesHandler(className, buf, ark);
		}

		return result;
	}

	public static ArrayList<String> dotInheritanceHandler(StringBuffer buf, NoahsArk ark) {
		ArrayList<String> result = new ArrayList<String>();
		
		return result;

	}

	public static ArrayList<String> dotAssociationHandler(String className, StringBuffer buf, NoahsArk ark) {
		ArrayList<String> result = new ArrayList<String>();
		String name = className;
		String[] lines = buf.toString().split("\n");

		
		if (name.contains("/")) {
			int len = name.split("/").length;
			name = name.split("/")[len - 1];
		}

		for (String s : lines) {
			System.out.println("Checking for association: " + s);
			if (s.contains("/")) {
				int len = s.split("/").length;
				s = s.split("/")[len - 1];
			}
			if (ark.seenClass.get(s) != null) {
				if (!s.equals(""))
					result.add(name + "$" + s);

			}
		}
		return result;

	}

	public static ArrayList<String> dotUsesHandler(String className, StringBuffer buf, NoahsArk ark) {
		ArrayList<String> result = new ArrayList<String>();

		return result;
	}

	public static String pairToViz(String pair, NoahsArk ark) {
		String result = "";
		if (pair.equals(""))
			return result;
		// extends
		if (pair.contains("!"))
			result = pair.split("!")[0] + " -> " + pair.split("!")[1] + " [arrowhead = onormal";
		// implements
		if (pair.contains("@"))
			result = pair.split("@")[0] + " -> " + pair.split("@")[1] + "[arrowhead = onormal,style = dotted";
		// Uses
		if (pair.contains("#"))
			result = pair.split("#")[0] + " -> " + pair.split("#")[1] + "[arrowhead = vee, style = dotted";
		// Association
		if (pair.contains("$")){
			result = pair.split("\\$")[0] + " -> " + pair.split("\\$")[1] + "[arrowhead = vee" ;//+ ark.getBoat().get(pair.split("\\$")[0]).arrowDesc;
			
		}
		
		if(pair.contains(";")){
			result += ",label=\"\\<\\<Adapts\\>\\>\"";
			result = result.replace(";", " ");
		}
		if(pair.contains("+")){
			result += ",label=\"\\<\\<Decorates\\>\\>\"";
			result = result.replace("+", " ");
		}
		
		result += "]\n";
		return result;

	}

	public static HashMap<String, String> listClasses(final File folder, String pkg) {
		HashMap<String, String> listOfJavaFiles = new HashMap<String, String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().contains(".")) {
				String ext = fileEntry.getName().split(Pattern.quote("."))[1];
				if (ext.equals("java")) {
					listOfJavaFiles.put(fileEntry.getName().split(Pattern.quote("."))[0], pkg);
//					temps.add(fileEntry.getName().split(Pattern.quote("."))[0]);
				}
			}
		}
		return listOfJavaFiles;
	}

	public static void visualize(String command, String path) {
		System.out.println("visualize " + path + "png");
		Runtime rt = Runtime.getRuntime();
		StringBuffer sb = new StringBuffer();
		try {
			path = path.substring(0, path.length() - 1);
			Process pr = null;
			if (command.equals("uml")) {
				if(path.contains(".")) path = path.replace(".", "t");
				System.out.println("uml diagram " + path);
				if(!isJava) pr = rt.exec("dot -T png -o src/" + path + ".png src/" + path + ".dot");
				else pr = rt.exec("dot -T png -o " + path + ".png " + path + ".dot");
			} else if (command.equals("sequence")) {
				System.out.println("sequence");
			} else {
				System.out.println("Not a valid command!");
			}
			pr.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
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
		System.out.println("done");
	}
}

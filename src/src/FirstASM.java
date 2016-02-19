package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
/*
 * We can do +
 *	+1. Find classes with high LCOM (where LCOM is one of five cohesion metrics that you aren’t expected to know how to compute).	 
 *	+2. Find classes containing fields that do not match camel case.
 *	+3. Find classes that violate Hollywood principle.
 *	+4. Find cycles in the class diagram and color their arrows.
 *	+5. Find classes with a method that makes more than X method calls.
 *	+6. Find classes with a method that implements recursion.
 *	+7. Find classes that violate the principle of least surprise.
 *	+8. Find all classes that implement a specific interface and color the interface and the implements arrows.
 *	+9. Find all classes that construct a given class or one of its subtypes (e.g. Graphics2D).
 *	+10. Find all classes in the same package as a given class and color them the same.
 *	+11. Find 'SingleGod' classes that have tons of fields and methods.
 *	+12. Find classes with fields that have no corresponding getters and setters.
 *	+13. Find all classes that start with a lower case letter.
 *	+14. Find all classes that have only non-public constructors.
 *	+15. (Related to #3) Find pairs of classes that are directly coupled to each other.
 *	+16. Find all classes that have static initializers.
 *	+17. Find all classes that have public static fields.
 *	+18. Find all classes that are the declared generic types of instances of List, Set, Map, but do not implement hashCode() and equals(). For example, if Foo is used in a list of type List<Foo>, then check whether it implements hashCode() and equals().
 *	+19. Find all Singletons that extends Serializable but do not define the private Object readResolve() method.
 *	+20. Annotate all classes with a simple “HelloWorld” annotation.
 */
public class FirstASM {
	private static String font = "\tfontname = \"Bitstream Vera Sans\"\n" + "\tfontsize = 16\n";
	private static String[] associationTypes = { "Inheritance", "Association", "Uses" };
	private static String methodSeparatorString = " | ";
	private static String classEndString1 = "}\"";
	private static String classEndString2 = "]\n";
	private static String container = "uml java.awt Container";
	private static String ourPK = "uml C:\\Users\\Maast3r\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\src";
	private static String ourPKG = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\src";
	private static String pls = "uml C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\pls";
	private static String pls2 = "uml C:\\Users\\Maast3r\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\pls";
	private static String testerino3 = "sequence java.util Collections shuffle List 5";
	private static String t = "sequence C:\\Users\\Maaster\\Dropbox\\Class\\CSSE374\\UMLMaker\\src\\src FirstASM sequenceHandler String,String,String,StringBuffer,NoahsArk,String,String,String,int 2";
	private static boolean isJava = false;
	public static NoahsArk ark;
	public static HashMap<String, String> listOfClasses;
	public static ArrayList<String> temps = new ArrayList<String>();
	private static FirstASM singleton;
	private static UI ui;
//	public Configuration Config

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ui = new UI();
	}
	public static FirstASM getInstance( ) {
		   if(singleton == null){
			   singleton = new FirstASM();
		   }
	      return singleton;
	   }
	
	public static void oldmain() throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException,
													InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String folder = ConfigurationManager.getInstance().defaultProps.getProperty("Input-Folder");
		String[] classes = ConfigurationManager.getInstance().defaultProps.getProperty("Input-Classes").split(",");
		String command = ConfigurationManager.getInstance().defaultProps.getProperty("Diagram");;
		String pkg = "";
		String inputClass = "";
		String inputMethod = "";
		String inputArgs = "";
		String maxDepth = "";
		StringBuffer buf = new StringBuffer();

		if (command.equals("uml")) {
			pkg = folder.split("\\\\")[folder.split("\\\\").length-1] + ".";
			File pkgToUML = new File(folder);
			listOfClasses = listClasses(pkgToUML, pkg);
			ark = new NoahsArk(listOfClasses);
			for(String s : classes){
				String[] temp = s.split("\\.");
				String tempPkg = "";
				for(int i=0; i<temp.length-1; i++){
					tempPkg += temp[i] + ".";
				}
				String tempClass = temp[temp.length - 1];
				ark.addnewClass(tempPkg, tempClass);
			}
			ark.setPackage(pkg);
			ark.setCmd(command);
			umlHandler(command, pkg, folder, buf, ark);
		} else if (command.equals("sequence")) {
			listOfClasses = null;
			ark = new NoahsArk(listOfClasses);
			ark.setPackage(pkg);
			ark.setCmd(command);
//			inputClass = line.split(" ")[2];
//			inputMethod = line.split(" ")[3];
//			inputArgs = line.split(" ")[4];
//			maxDepth = line.split(" ")[5];
			sequenceHandler(command, pkg, folder, buf, ark, inputClass, inputMethod, inputArgs,
					Integer.parseInt(maxDepth));
		} else {
			System.out.println("THIS COMMMAND IS NOT SUPPORTED");
		}
	}

	public static void umlHandler(String command, String pkg, String path, StringBuffer buf, NoahsArk ark)
			throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException,
					InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
		String bufCleaner = buf.toString();
		bufCleaner = bufCleaner.replace("$", "").replace("@", "");
		output.write(bufCleaner.getBytes());
		output.close();

		System.out.println("trying to run program");
//		visualize(command, pkg);
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

	public static void getClassDetails(String pkg, String className, NoahsArk ark) throws IOException, InstantiationException, IllegalAccessException,
													IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		ClassReader reader = new ClassReader(pkg + className);
//		String[] classVisitorArgs = new String[]{ "ClassMethodFieldVisitor" };
		String[] classVisitorArgs = ConfigurationManager.getInstance().defaultProps.getProperty("Visitors").split(",");
		
		ClassVisitorBuffered declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, ark);
		reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
		ClassVisitorBuffered fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, ark,
				declVisitor.getName());
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);

		ClassVisitorBuffered methodVisitor = new DotMethodVisitor(Opcodes.ASM5, fieldVisitor, ark,
				declVisitor.getName());
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		
		if(classVisitorArgs.length != 0){
			for(String s : classVisitorArgs){
				if(!s.equals("")){
					Class[] cArg = new Class[4];
					cArg[0] = int.class;
					cArg[1] = ClassVisitorBuffered.class;
					cArg[2] = NoahsArk.class;
					cArg[3] = String.class;
					Constructor constr = Class.forName(
							"src." + s)
							.getConstructor(cArg);
		
					ClassVisitorBuffered visitor = (ClassVisitorBuffered) constr.newInstance(Opcodes.ASM5,
									methodVisitor, ark, declVisitor.getName());
					reader.accept(visitor, ClassReader.EXPAND_FRAMES);
				}
			}
		}
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

	public static StringBuffer generateDotUML(String pkg, StringBuffer buf, NoahsArk ark) throws IOException, NoSuchMethodException, SecurityException,
														ClassNotFoundException, InstantiationException, IllegalAccessException, 
														IllegalArgumentException, InvocationTargetException {
		buf.append("digraph G{\n" + font + "\n" + "node [\n" + font + "\n" + 
				" shape = \"record\"\n" + "]\n"
				+ "edge [\n" + font + "]\n");

		// Do the real work
		String result = "";
		HashMap<String, ClassPrototype> boat = ark.getBoat();
		
		for (ClassPrototype c : boat.values()) {
			DotNode tempNode = new DotNode();
			result = "";
			String cName = c.getName();
			tempNode.title = c.getName();
			Iterator fIterator = c.getFields().keySet().iterator();
			FieldPrototype field;
			while (fIterator.hasNext()) {
				field = c.getFields().get(fIterator.next());
				tempNode.fields.add(field.prepareUML());
			}

			Iterator mIterator = c.getMethods().keySet().iterator();
			MethodPrototype method;
			while (mIterator.hasNext()) {
				method = c.getMethods().get(mIterator.next());
				tempNode.methods.add(method.prepareUML());
			}	
			
//			String[] titleArgs = new String[]{ "InterfaceTitleDecorator", "AbstrTitleDecorator" };
			String[] titleArgs = ConfigurationManager.getInstance().defaultProps.getProperty("NodeTitleDecorators").split(",");
			new NodeTitleDecorator(c, titleArgs).decorate();;
			String[] args = ConfigurationManager.getInstance().defaultProps.getProperty("Phases").split(",");

			if(args.length != 0){
				for(String s : args){
					// Check for disabled phase
//					if(){
//						
//					}
					if(!s.equals("")){
						Class[] cArg = new Class[5];
						cArg[0] = String[].class;
						cArg[1] = DotNode.class;
						cArg[2] = String.class;
						cArg[3] = NoahsArk.class;
						cArg[4] = ClassPrototype.class;
						Constructor constr = Class.forName(
								"src." + s +"Phase")
								.getConstructor(cArg);
						AbstractPhase visitor = (AbstractPhase) constr.newInstance(new String[]{s},
								tempNode, cName, ark, c);
						tempNode = visitor.executePhase();
					}
				}
			}
			result = tempNode.dotNodePrepareUML();
			if(ConfigurationManager.getInstance().configuration.get(c.getName()) != null ){				
				if(ConfigurationManager.getInstance().configuration.get(c.getName())){
					buf.append(result);
				}
			}
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
			} else if (temp.equals("GO UPPER")) {
				callNode = ark.getActiveNode().getParent();
				ark.setActiveNode(callNode);

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

	public static String pairToViz(String pair, NoahsArk ark) throws InstantiationException, IllegalAccessException,
							IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		String result = "";
		boolean fuckeverything = false;
		if (pair.equals(""))
			return result;
		
//		System.out.println(pair.split("\\$")[0]);
		// extends
		if(ConfigurationManager.getInstance().configuration.get(pair.split("!")[0]) != null ){
			if (pair.contains("!")){
				if(!ConfigurationManager.getInstance().configuration.get(pair.split("!")[0]) || !ConfigurationManager.getInstance().configuration.get(pair.split("!")[1]))return "";
					result = pair.split("!")[0] + " -> " + pair.split("!")[1] + " [arrowhead = onormal";
			} else{
				return "";
			}
		}
		else if(ConfigurationManager.getInstance().configuration.get(pair.split("@")[0]) != null ){
			// implements
			if (pair.contains("@")){
				if(!ConfigurationManager.getInstance().configuration.get(pair.split("@")[0]) || !ConfigurationManager.getInstance().configuration.get(pair.split("@")[1]))return "";
				result = pair.split("@")[0] + " -> " + pair.split("@")[1] + "[arrowhead = onormal,style = dotted";
			} else{
				return "";
			}
		}
			// Uses
		else if(ConfigurationManager.getInstance().configuration.get(pair.split("#")[0]) != null ){
			if (pair.contains("#")){
				if(!ConfigurationManager.getInstance().configuration.get(pair.split("#")[0]) || !ConfigurationManager.getInstance().configuration.get(pair.split("#")[1]))return "";
				result = pair.split("#")[0] + " -> " + pair.split("#")[1] + "[arrowhead = vee, style = dotted";
			}else{
				return "";
			}
		}
			// Association
		else if(ConfigurationManager.getInstance().configuration.get(pair.split("\\$")[0]) != null && ConfigurationManager.getInstance().configuration.get(pair.split("\\$")[1].replace(";","").replace("+", "").replace("?", "")) != null){
			if (pair.contains("$")){
				if(!ConfigurationManager.getInstance().configuration.get(pair.split("\\$")[0]) || !ConfigurationManager.getInstance().configuration.get(pair.split("\\$")[1].replace(";","").replace("+", "").replace("?", "")))return "";
				result = pair.split("\\$")[0] + " -> " + pair.split("\\$")[1] + "[arrowhead = vee" ;
			}else{
				return "";
			}
		} else{
			return "";
		}
//		String args[]= new String[] { "PairLabelDecorator" };\
		String args[] = ConfigurationManager.getInstance().defaultProps.getProperty("ArrowDecorators").split(",");
		result = new PairDecorator(result, args).toDecorate();
//		if(!fuckeverything)return "";
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
			} else if(fileEntry.isDirectory()){
				String pkgName = pkg+"."+fileEntry.getName();
				listOfJavaFiles.putAll(listClasses(fileEntry, pkgName));
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
			if (command.equals("uml") || command.equals("2")) {
				if(path.contains(".")) path = path.replace(".", "t");
				System.out.println("uml diagram " + path);
				if(!isJava) pr = rt.exec("dot -T png -o " + path + ".png " + path + ".dot");
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

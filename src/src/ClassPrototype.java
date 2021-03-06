package src;
import java.util.HashMap;
import java.util.HashSet;

public class ClassPrototype {
	public String preName = "";
	public String name;
	public HashMap<String, FieldPrototype> fields;
	public HashMap<String, MethodPrototype> methods;
	public String superName;
	public String[] interfaces;
	public String arrowDesc;
	public HashSet<String> type = new HashSet<String>();
	public HashSet<String> phases = new HashSet<String>();
	public boolean isAbstract = false;
	public boolean isInterface = false;
	public String pkg = "";
	
	public ClassPrototype(String name){
		this.name = name;
		this.fields = new HashMap<String, FieldPrototype>();
		this.methods = new HashMap<String, MethodPrototype>();
		this.arrowDesc = "";
	}
	
	public ClassPrototype(String name, String superName, String[] interfaces){
		this.name = name;
		this.fields = new HashMap<String, FieldPrototype>();
		this.methods = new HashMap<String, MethodPrototype>();
		this.superName = superName;
		this.interfaces = interfaces;
	}
	
	public String getPreName(){
		return this.preName;
	}
	
	public String getName(){
		return this.name;
	}
	
	public HashMap<String, FieldPrototype> getFields(){
		return this.fields;
	}

	public HashMap<String, MethodPrototype> getMethods(){
		return this.methods;
	}
	
	public void addField(String key, FieldPrototype value){
		this.fields.put(key, value);
	}
	
	public void addMethod(String key, MethodPrototype value){
		this.methods.put(key, value);
	}
	
	
	public String getSuperName(){
		return this.superName;
	}
	
	public String prepareUML(){
		return this.name + " [ \ncenter=true\n    label=\"{" + this.preName + "\n" + this.name;
	}
	
	public String[] getInterfaces(){
		return this.interfaces;
	}
	
	public String getArrowDesc(){
		return this.arrowDesc;
	}
	
	public HashSet<String> getTypes(){
		return this.type;
	}
	
	public String getPkg(){
		return this.pkg;
	}
}
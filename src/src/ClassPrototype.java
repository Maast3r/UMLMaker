package src;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassPrototype {
	public String name;
	public HashMap<String, FieldPrototype> fields;
	public HashMap<String, MethodPrototype> methods;

	
	public ClassPrototype(String name){
		this.name = name;
		this.fields = new HashMap<String, FieldPrototype>();
		this.methods = new HashMap<String, MethodPrototype>();
		
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
	
	public String getName(){
		return this.name;
	}
	
	public String prepareUML(){
		return this.name + " [ \n    label=\"{" + this.name +"|";
	}
}
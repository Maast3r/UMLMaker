package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MethodPrototype {
	public String access;
	public String name;
	public String args;
	public String returnType;
	public HashSet<String> typesSeen;
	public ArrayList<String> calls;
	public HashMap<String, HashSet<String>> fieldInsn;
	
	public MethodPrototype(String access, String name, String args,
			String returnType){
		this.access = access;
		this.name = name;
		this.args = args;
		this.returnType = returnType;
		this.typesSeen = new HashSet<String>();
		this.calls = new ArrayList<String>();
		this.fieldInsn = new HashMap<String, HashSet<String>>();
	}
	
	public String prepareUML(){
		return this.access + this.name.replace("<", "\\<").replace(">", "\\>") + "("+ this.args + "): " + this.returnType + '\\' + 'l' ;
	}
	
	public boolean getIsStaticAndSame(String whateverYouWant){
		return this.access.contains("static") && this.returnType.equals(whateverYouWant);
	}
	
	public String getAccess(){
		return this.access;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getArgs(){
		return this.args;
	}
	
	public String returnType(){
		return this.returnType;
	}
	
	public HashSet<String> getTypesSeen(){
		return this.typesSeen;
	}
	
	public ArrayList<String> getCalls(){
		return this.calls;
	}
	
	public HashMap<String, HashSet<String>> getFieldInsn(){
		return this.fieldInsn;
	}
}

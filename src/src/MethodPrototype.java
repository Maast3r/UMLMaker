package src;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodPrototype {
	public String access;
	public String name;
	public String args;
	public String returnType;
	
	public MethodPrototype(String access, String name, String args,
			String returnType){
		this.access = access;
		this.name = name;
		this.args = args;
		this.returnType = returnType;
	}
	
	public String prepareUML(){
		return this.access + this.name.replace("<", "\\<").replace(">", "\\>") + "("+ this.args + "): " + this.returnType + '\\' + 'l' ;
	}
	
	public boolean getIsStaticAndSame(String whateverYouWant){
		return this.access.contains("static") && this.returnType.equals(whateverYouWant);
	}
	
}

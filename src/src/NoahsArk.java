package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NoahsArk {
	public HashMap<String, ClassPrototype> boat;
	public HashMap<String,HashSet<String>> pairs;
	private static HashMap<String, Boolean> listOfClasses;
	public int depth;
	public String pkg;
	
	public NoahsArk(HashMap<String, Boolean> listOfClasses) {
		this.boat = new HashMap<String, ClassPrototype>();
		this.pairs = new HashMap<String,HashSet<String>>();
		this.listOfClasses = listOfClasses;
	}
	
	

	public HashMap<String, ClassPrototype> getBoat() {
		return this.boat;
	}

	public void addClass(String name, ClassPrototype toAdd) {
		this.boat.put(name, toAdd);
	}

	public void addPair(String origin, String target) {
		if(listOfClasses.get(origin) != null && listOfClasses.get(target.substring(1)) != null){
			if(this.pairs.get(origin) == null){
				this.pairs.put(origin, new HashSet<String>());
			}
			this.pairs.get(origin).add(target);
		}
	}
	
	public void setDepthMax(int depth){
		this.depth = depth;
	}
	
	public int getDepthMax(){
		return this.depth;
	}
	
	public HashMap<String, Boolean> getListOfClass(){
		return this.listOfClasses;
	}
	
	public String getPackage(){
		return this.pkg;
	}
	
	public void setPackage(String pkg){
		this.pkg = pkg;
	}

}
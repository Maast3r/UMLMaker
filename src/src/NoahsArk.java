package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NoahsArk {
	public HashMap<String, ClassPrototype> boat;
	public HashMap<String,HashSet<String>> pairs;
	
	public NoahsArk() {
		this.boat = new HashMap<String, ClassPrototype>();
		this.pairs = new HashMap<String,HashSet<String>>();
	}
	
	

	public HashMap<String, ClassPrototype> getBoat() {
		return this.boat;
	}

	public void addClass(String name, ClassPrototype toAdd) {
		this.boat.put(name, toAdd);
	}

	public void addPair(String origin, String target) {
		this.pairs.get(origin).add(target);
	}

}
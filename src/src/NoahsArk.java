package src;

import java.util.HashMap;

public class NoahsArk {
	public HashMap<String, ClassPrototype> boat;

	public NoahsArk() {
		this.boat = new HashMap<String, ClassPrototype>();
	}

	public HashMap<String, ClassPrototype> getBoat() {
		return this.boat;
	}

	public void addClass(String name, ClassPrototype toAdd) {
		this.boat.put(name, toAdd);
	}

}
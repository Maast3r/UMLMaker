package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NoahsArk {
	public HashMap<String, ClassPrototype> boat;
	public HashMap<String, HashSet<String>> pairs;
	private static HashMap<String, String> listOfClasses;
	private static HashMap<String, String> newList;
	public int depth;
	public String pkg;
	public String cmd;
	public ArrayList<String> newNodes;
	public ArrayList<String> constructedNodes;
	public ArrayList<String> sequenceNodes;
	public CallNode graphRoot;
	public String mainNode;
	public CallNode noe;
	public HashMap<String, String> seenClass;
	public int umlNodes = 0;

	public NoahsArk(HashMap<String, String> listOfClasses) {
		this.boat = new HashMap<String, ClassPrototype>();
		this.pairs = new HashMap<String, HashSet<String>>();
		this.listOfClasses = listOfClasses;
		this.newList = new HashMap<String, String>();
		newNodes = new ArrayList<String>();
		constructedNodes = new ArrayList<String>();
		sequenceNodes = new ArrayList<String>();
		this.graphRoot = null;
		this.seenClass = new HashMap<String, String>();
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public HashMap<String, ClassPrototype> getBoat() {
		return this.boat;
	}

	public void addClass(String name, ClassPrototype toAdd) {
		this.boat.put(name, toAdd);
	}

	public void setRoot(CallNode root) {
		this.graphRoot = root;
	}

	public void setActiveNode(CallNode node) {
		this.noe = node;
	}

	public CallNode getActiveNode() {
		return this.noe;
	}

	public void addPair(String origin, String target) {
		if (seenClass.get(origin) != null && seenClass.get(target.substring(1)) != null) {
			if (this.pairs.get(origin) == null) {
				this.pairs.put(origin, new HashSet<String>());
			}
			this.pairs.get(origin).add(target);
		}
	}

	public void setDepthMax(int depth) {
		this.depth = depth;
	}

	public int getDepthMax() {
		return this.depth;
	}

	public HashMap<String, String> getListOfClass() {
		return this.listOfClasses;
	}
	
	public HashMap<String, String> getNewList() {
		return this.newList;
	}
	
	public void setListOfClasses(HashMap<String, String> h){
		this.listOfClasses = h;
	}
	
	public void resetNewList(){
		this.newList = new HashMap<String, String>();
	}

	public String getPackage() {
		return this.pkg;
	}

	public void setPackage(String pkg) {
		this.pkg = pkg;
	}

	public void goUp() {
		this.depth = this.depth + 1;
	}

	public void deeper() {
		this.depth = this.depth - 1;
	}

}
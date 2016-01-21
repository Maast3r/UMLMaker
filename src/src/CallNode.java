package src;

import java.util.ArrayList;

public class CallNode {
	public String className = "";
	public String methodName = "";
	public ArrayList<CallNode> children;
	public CallNode parent;
	
	public CallNode(String name, boolean isClass){
		this.children = new ArrayList<CallNode>();
		if(isClass){
			this.className = name;
		} else{
			this.methodName = name;
		}
	}
	
	public void addChild(CallNode child){
		this.children.add(child);
	}
	
	public ArrayList<CallNode> getChildren(){
		return this.children;
	}
	
	public boolean isMethod(){
		return this.methodName.length()!=0;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setChildren(ArrayList<CallNode> children) {
		this.children = children;
	}

	public CallNode getParent(){
		return this.parent;
	}
	
	public void setParent(CallNode node){
		this.parent = node;
	}
	
	public void printCallTree(int counter){
		System.out.print(counter + " " + this.methodName + this.className);
		for(CallNode node : this.children){
			node.printCallTree(counter + 1);
		}
		System.out.print("\n");
	}
}

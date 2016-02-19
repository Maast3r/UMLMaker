package src;

import java.util.ArrayList;

public class DotNode {
	private static String methodSeparatorString = " | ";
	private static String classEndString1 = "}\"\n";
	private static String classEndString2 = "]\n";
	
	public String title = "";
	public String color = "black\n";
	public String fillColor = "white\n";
	public ArrayList<String> fields = new ArrayList<String>();
	public ArrayList<String> methods = new ArrayList<String>();
	
	public DotNode(){ 
	}
	
	public String dotNodePrepareUML(){
		String result = "";
		// Begin node
		result += this.title;
		
		// Add fields
		for(String s : fields){
			result += s;
		}
		
		result += methodSeparatorString;
		// Add Methods
		for(String s : methods){
			result += s;
		}
		result+= classEndString1;
		result += "color = " +  this.color;
		result += "style = filled \n fillcolor = " + this.fillColor;
		result += classEndString2;
		
		return result;
	}
	
}

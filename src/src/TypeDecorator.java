package src;

import java.util.HashMap;

public class TypeDecorator extends TypeDetector{
	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	private HashMap<String, String> typeToFill = new HashMap<String, String>();
	private HashMap<String, String> typeToName = new HashMap<String, String>();
	private TypeDetector typeDetector;
	
	public TypeDecorator(TypeDetector t){
		super(t.cName, t.ark);
		this.typeDetector = t;
		this.typeToColor.put("", "black");
		this.typeToColor.put("singleton", "blue");
		
		this.typeToFill.put("", "white");
		this.typeToFill.put("singleton", "white");
		this.typeToFill.put("decorator", "chartreuse2");
		this.typeToFill.put("component", "chartreuse2");
		this.typeToFill.put("adapter", "firebrick");
		this.typeToFill.put("adaptee", "firebrick");
		this.typeToFill.put("target", "firebrick");
		
		this.typeToName.put("", "");
		this.typeToName.put("singleton", "\\n\\<\\<Singleton\\>\\>");
		this.typeToName.put("decorator", "\\n\\<\\<decorator\\>\\>");
		this.typeToName.put("component", "\\n\\<\\component\\>\\>");
		this.typeToName.put("adapter", "\\n\\<\\<adapter\\>\\>");
		this.typeToName.put("adaptee", "\\n\\<\\<adaptee\\>\\>");
		this.typeToName.put("target", "\\n\\<\\<target\\>\\>");
	}
	
	public String getColor(){
		return "color = " + this.typeToColor.get(this.typeDetector.getType()) + "\n";
	}
	
	public String getFillColor(){
		return "style = filled \n fillcolor = " + this.typeToFill.get(this.typeDetector.getType()) + "\n";
	}
	
	public String getType(){
		return this.typeToName.get(this.typeDetector.getType());
	}
}

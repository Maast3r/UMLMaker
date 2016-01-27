package src;

import java.util.HashMap;

public class TypeDecorator {
	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	private HashMap<String, String> typeToName = new HashMap<String, String>();
	private TypeDetector typeDetector;
	
	public TypeDecorator(TypeDetector t){
		this.typeDetector = t;
		this.typeToColor.put("none", "black");
		this.typeToColor.put("singleton", "blue");
		this.typeToName.put("", "");
		this.typeToName.put("singleton", "\\n\\<\\<Singleton\\>\\>");
	}
	
	public String getColor(){
		return "color = " + this.typeToColor.get(this.typeDetector.getType()) + "\n";
	}
	
	public String getType(){
		return this.typeToName.get(this.typeDetector.getType());
	}
}

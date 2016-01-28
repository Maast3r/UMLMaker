package src;

import java.util.HashMap;

public class TypeDecorator extends TypeDetector{
	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	private HashMap<String, String> typeToFill = new HashMap<String, String>();
	private HashMap<String, String> typeToName = new HashMap<String, String>();
	private TypeDetector typeDetector;
	
	public TypeDecorator(TypeDetector t){
		super(t.flags);
		this.typeDetector = t;
		this.typeToColor.put("a", "black");
		this.typeToColor.put("singleton", "blue");
		this.typeToFill.put("a", "white");
		this.typeToFill.put("singleton", "white");
		this.typeToFill.put("decorator", "chartreuse2");
		this.typeToName.put("a", "");
		this.typeToName.put("singleton", "\\n\\<\\<Singleton\\>\\>");
		this.typeToName.put("decorator", "\\n\\<\\<decorator\\>\\>");
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

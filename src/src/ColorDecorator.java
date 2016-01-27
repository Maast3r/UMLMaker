package src;

import java.util.HashMap;

public class ColorDecorator extends TypeDetector{
	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	private String type;
	private TypeDetector typeDec;
	
	public ColorDecorator(String type, boolean[] flags){
		super(flags);
		this.type = type;
		this.typeToColor.put("none", "black");
		this.typeToColor.put("singleton", "blue");
	}
	
	public ColorDecorator(TypeDetector t, boolean[] flags){
		super(flags);
		this.typeDec = t;
		this.typeToColor.put("none", "black");
		this.typeToColor.put("singleton", "blue");
	}
	
	public String getColor(){
		return "color = " + this.typeToColor.get(this.typeDec.getType()) + "\n";
	}
}

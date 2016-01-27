package src;

import java.util.HashMap;

public class ColorDecorator extends TypeDetector{
	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	
	public ColorDecorator(String type, boolean[] flags){
		super(flags);
		this.typeToColor.put("none", "black");
		this.typeToColor.put("singleton", "blue");
	}
	
	public ColorDecorator(boolean[] flags){
		super(flags);
		this.typeToColor.put("none", "black");
		this.typeToColor.put("singleton", "blue");
	}
	
	public String getColor(){
		return "color = " + this.typeToColor.get(super.getType()) + "\n";
	}
}

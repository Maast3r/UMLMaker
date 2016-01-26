package src;

import java.util.HashMap;

public class ColorDecorator {
	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	private String type;
	
	public ColorDecorator(String type){
		this.type = type;
		this.typeToColor.put("none", "black");
		this.typeToColor.put("singleton", "blue");
	}
	
	public String getColor(){
		return "color = " + this.typeToColor.get(this.type) + "\n";
	}
}

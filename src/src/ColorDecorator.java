package src;

import java.util.HashMap;

public class ColorDecorator extends TypeDecorator{
//	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	
	public ColorDecorator(TypeDetector t){
		super(t);
//		this.typeToColor.put("none", "black");
//		this.typeToColor.put("singleton", "blue");
	}
	
	public String getColor(){
		return super.getColor();
	}
}

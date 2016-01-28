package src;

import java.util.HashMap;

public class ColorDecorator extends TypeDecorator{
//	private HashMap<String, String> typeToColor = new HashMap<String, String>();
	
	public ColorDecorator(TypeDetector t){
		super(t);
	}
	
	public String getColor(){
		return super.getColor();
	}
	
	public String getFillColor(){
		return super.getFillColor();
	}
}

package src;

import java.util.HashMap;

public class NameDecorator extends TypeDecorator{
	
	public NameDecorator(TypeDetector t){
		super(t);
	}
	
	public String getType(){
		return super.getType();
	}
}

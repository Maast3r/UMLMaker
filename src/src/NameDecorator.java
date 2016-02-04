package src;

import java.util.HashSet;

public class NameDecorator extends TypeDecorator{
	
	public NameDecorator(TypeDetector t){
		super(t);
	}
	
	public HashSet<String> getType(){
		return super.getType();
	}
}

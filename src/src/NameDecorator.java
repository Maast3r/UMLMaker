package src;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class NameDecorator extends TypeDecorator{
	
	public NameDecorator(TypeDetector t){
		super(t);
	}
	
	@Override
	public HashSet<String> getType() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
											InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return super.getType();
	}
}

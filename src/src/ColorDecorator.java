package src;

import java.lang.reflect.InvocationTargetException;


public class ColorDecorator extends TypeDecorator{
	
	public ColorDecorator(TypeDetector t){
		super(t);
	}
	
	@Override
	public String getColor() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
							InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return super.getColor();
	}
	
	@Override
	public String getFillColor() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return super.getFillColor();
	}
}

package src;

import java.lang.reflect.InvocationTargetException;

public class InterfaceDecorator extends TypeDecorator{

	public InterfaceDecorator(TypeDetector t) {
		super(t);
		super.addFill("interface", "purple");
		super.addTypeToName("interface", "interfaceuhhh");
	}
	
	@Override
	public String getFillColor() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return super.getTypeToFill().get("interface");
	}
}

package src;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

public class TypeDetector {
	public String cName;
	public NoahsArk ark;
	public HashSet<AbstractDetector> detectors = new HashSet<AbstractDetector>();
	public HashMap<String, AbstractDetector> totalDetectors = new HashMap<String, AbstractDetector>();
	public String[] args;
	
	public TypeDetector(String cName, NoahsArk ark, String[] args){
		this.cName = cName;
		this.ark = ark;
		this.args = args;
		
		this.totalDetectors.put("SingletonDetector", new SingletonDetector(this.ark));
		this.totalDetectors.put("DecoratorDetector", new DecoratorDetector(this.ark));
		this.totalDetectors.put("AdapterDetector", new AdapterDetector(this.ark));
		this.totalDetectors.put("CompositeDetector", new AdapterDetector(this.ark));
	}
	
	public HashSet<String> getType() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
									InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		populateDetectors();
		HashSet<String> result = new HashSet<String>();
		for(AbstractDetector ad : this.detectors){
			result.addAll(ad.getType(this.cName));
		}
		return result;
	}
	
	public void populateDetectors() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
									InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		this.detectors = new HashSet<AbstractDetector>();
		for(String s : this.args){
			if(!this.totalDetectors.containsKey(s)){
				this.detectors.add(addNewTypeDetector(s));
			} else {
				this.detectors.add(this.totalDetectors.get(s));
			}
		}
	}
	
	public AbstractDetector addNewTypeDetector(String cName) throws NoSuchMethodException, SecurityException, ClassNotFoundException,
												InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class[] cArg = new Class[1];
		cArg[0] = NoahsArk.class;
		Constructor constr = Class.forName(
				"src." + cName)
				.getConstructor(cArg);

		return (AbstractDetector) constr.newInstance(this.ark);
	}
	
	
}

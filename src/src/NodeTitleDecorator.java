package src;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

public class NodeTitleDecorator {
	public ClassPrototype c;
	public String[] args;
	public HashMap<String, AbstractTitleDecorator> decorators = new HashMap<String, AbstractTitleDecorator>();
	
	public NodeTitleDecorator(ClassPrototype c, String[] args){
		this.c = c;
		this.args = args;
		
		this.decorators.put("AbstrTitleDecorator", new AbstrTitleDecorator(c));
		this.decorators.put("InterfaceTitleDecorator", new InterfaceTitleDecorator(c));
	}
	
	public void decorate() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
							InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashSet<AbstractTitleDecorator> temp = populate();
		for(AbstractTitleDecorator atd : temp){
			atd.decorateTitle();
		}
	}
	
	public HashSet<AbstractTitleDecorator> populate() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
								InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashSet<AbstractTitleDecorator> temp = new HashSet<AbstractTitleDecorator>();
		for(String s : this.args){
			if(this.decorators.containsKey(s)){
				temp.add(this.decorators.get(s));
			} else {
				temp.add(addNewTitleDecorator(s));
			}
		}
		return temp;
	}
	
	public AbstractTitleDecorator addNewTitleDecorator(String cName) throws NoSuchMethodException, SecurityException, ClassNotFoundException,
									InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class[] cArg = new Class[1];
		cArg[0] = ClassPrototype.class;
		Constructor constr = Class.forName(
				"src." + cName)
				.getConstructor(cArg);

		return (AbstractTitleDecorator) constr.newInstance(this.c);
	}
	
}

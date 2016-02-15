package src;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

public class PairDecorator {
	public String pair;
	public String[] args;
	public HashMap<String, AbstractPairDecorator> decorators = new HashMap<String, AbstractPairDecorator>();
	
	public PairDecorator(String pair, String[] args){
		this.pair = pair;
		this.args = args;
		
//		this.decorators.put("PairLabelDecorator", new PairLabelDecorator(this.pair));
	}
	
	public String toDecorate() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
							InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		HashSet<AbstractPairDecorator> temp = populate();
		for(AbstractPairDecorator atd : temp){
			String loopTemp = "";
			loopTemp = atd.decorate(this.pair);
			this.pair = loopTemp;
		}
		return this.pair;
	}
	
	public HashSet<AbstractPairDecorator> populate() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
								InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		HashSet<AbstractPairDecorator> temp = new HashSet<AbstractPairDecorator>();
		for(String s : this.args){
			if(this.decorators.containsKey(s)){
				temp.add(this.decorators.get(s));
			} else {
				temp.add(addNewDecorator(s));
			}
		}
		return temp;
	}
	
	public AbstractPairDecorator addNewDecorator(String cName) throws InstantiationException, IllegalAccessException,
								IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		Class[] cArg = new Class[1];
		cArg[0] = String .class;
		Constructor constr = Class.forName(
				"src." + cName)
				.getConstructor(cArg);

		return (AbstractPairDecorator) constr.newInstance(this.pair);
	}
}

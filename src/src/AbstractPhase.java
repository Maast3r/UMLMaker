package src;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPhase {
	public String[] args;
	public DotNode node;
	public String cName;
	public NoahsArk ark;
	public ClassPrototype c;
	
	public AbstractPhase(String[] args, DotNode node,String classname,
			NoahsArk ark, ClassPrototype c){
		this.args = args;
		this.node = node;
		this.cName = classname;
		this.ark = ark;
		this.c = c;
	}
	
	public abstract void execute();
	
	public DotNode executePhase() throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		node.color = new ColorDecorator(new TypeDetector(cName, ark, args)).getColor();
		node.fillColor = new ColorDecorator(new TypeDetector(cName, ark, args)).getFillColor();

		
		String[] nodeDecorators = ConfigurationManager.getInstance().defaultProps.getProperty("NodeDecorators").split(",");
		if(nodeDecorators.length != 0){
			for(String s : nodeDecorators){
				if(!s.equals("")){
					Class[] cArg = new Class[1];
					cArg[0] = TypeDetector.class;
					Constructor constr = Class.forName(
							"src." + s)
							.getConstructor(cArg);
					TypeDecorator decorator = (TypeDecorator) constr.newInstance(new TypeDetector(cName, ark, args));
					node.fillColor = decorator.getFillColor();
				}
			}
		}
		node.title = c.prepareUML() + new NameDecorator(new TypeDetector(cName, ark, args)).getType().toString().replace("]", "")
				.replace(",","")
				.replace("[","" )+ "|";
		return node;
	};
}

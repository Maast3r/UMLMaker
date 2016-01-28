package src;

import java.util.Iterator;

public class SingletonDetector extends AbstractDetector{
	public NoahsArk ark;
	
	public SingletonDetector(NoahsArk ark){
		super(ark);
		this.ark = ark;
	}

	@Override
	public String getType(String cName) {
		boolean flag[] = { false, false };
		if(this.ark.getListOfClass().containsKey(cName)){
			ClassPrototype c = this.ark.getBoat().get(cName);
			String className = c.getName();
			String superName = c.getSuperName();
			String[] interfaces= c.getInterfaces();
	
			Iterator fIterator = c.getFields().keySet().iterator();
			FieldPrototype field;
			while (fIterator.hasNext()) {
				field = c.getFields().get(fIterator.next());
				if (field.getSingleton(className, superName))
					flag[0] = true;
			}
	
			Iterator mIterator = c.getMethods().keySet().iterator();
			MethodPrototype method;
			while (mIterator.hasNext()) {
				method = c.getMethods().get(mIterator.next());
				if (method.getIsStaticAndSame(className))
					flag[1] = true;
					
				}	
		}
		if(flag[0] && flag[1]) return "singleton";
		return "";
	}
}
